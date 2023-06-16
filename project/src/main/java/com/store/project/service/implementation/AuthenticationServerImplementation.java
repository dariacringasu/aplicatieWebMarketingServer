package com.store.project.service.implementation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.project.controller.authentication.AuthenticationRequest;
import com.store.project.controller.authentication.AuthenticationResponse;
import com.store.project.controller.authentication.RegisterRequest;
import com.store.project.controller.security.JwtService;
import com.store.project.controller.token.Token;
import com.store.project.controller.token.TokenType;
import com.store.project.domain.User;
import com.store.project.domain.Role;
import com.store.project.exceptions.EmailAlreadyExistsException;
import com.store.project.exceptions.EmailAlreadyExistsException;
import com.store.project.exceptions.UserNotEnabledException;
import com.store.project.repository.TokenRepository;
import com.store.project.repository.UserRepository;
import com.store.project.service.dao.AuthenticationService;
import com.store.project.service.dao.EmailService;
import com.store.project.service.dao.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableJpaAuditing
public class AuthenticationServerImplementation implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;
    private final UserService userService;

    private final TokenRepository tokenRepository;

        public AuthenticationResponse register(RegisterRequest request){
//           if(!userService.emailExists(request.getEmail())) {
               var client = User.builder()
                       .companyName(request.getCompanyName())
                       .email(request.getEmail())
                       .password(passwordEncoder.encode(request.getPassword()))
                       .username(request.getUsername())
                       .address((request.getAddress()))
                       .enabled(false)
                       .role(Role.USER)
                       .dateOfJoin(request.getDateOfJoin())
                       .build();
               repository.save(client);
               var jwtToken = jwtService.generateToken(client);
               saveUserToken(client, jwtToken);
               SimpleMailMessage mailMessage = new SimpleMailMessage();
               System.out.println(client.getEmail());
               mailMessage.setTo(client.getEmail());
               mailMessage.setSubject("Confirm email address");
               mailMessage.setFrom("dariacringasu05@gmail.com");
               mailMessage.setText("To confirm your account, please click here: " + "http://localhost:5173/confirmAccount?email="+client.getEmail());
               emailService.sendEmail(mailMessage);
               return AuthenticationResponse.builder()
                       .token(jwtToken).build();
//           }
//           else {
//               throw new EmailAlreadyExistsException(request.getEmail());
//           }
        }



        public AuthenticationResponse authenticate(AuthenticationRequest request){
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        //username si password corecte
            var client = repository.findByEmail(request.getEmail())
                    .orElseThrow();
            System.out.println(client.getEnabled());
            if(client.getEnabled().equals(true)) {
                var jwtToken = jwtService.generateToken(client);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .companyName(client.getCompanyName())
                        .email(client.getEmail())
                        .id(client.getId())
                        .role(client.getRole())
                        .dateOfJoin(client.getDateOfJoin())
                        .address(client.getAddress())
                        .build();
            }
            else {
                throw new UserNotEnabledException(client.getEmail());
            }

        }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);

            }
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response){
        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
    }
}
