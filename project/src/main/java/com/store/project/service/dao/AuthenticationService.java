package com.store.project.service.dao;

import com.store.project.controller.authentication.AuthenticationRequest;
import com.store.project.controller.authentication.AuthenticationResponse;
import com.store.project.controller.authentication.RegisterRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

     AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void logout(HttpServletRequest request, HttpServletResponse response);
}
