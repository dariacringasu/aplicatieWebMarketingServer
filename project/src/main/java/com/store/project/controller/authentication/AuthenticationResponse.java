package com.store.project.controller.authentication;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.project.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("token")
    private String token;
    @JsonProperty("refresh_token")
    private String refreshToken;

    private String companyName;

    private Long id;

    private String email;

    private String address;

    private LocalDate dateOfJoin;

    private Role role;
}
