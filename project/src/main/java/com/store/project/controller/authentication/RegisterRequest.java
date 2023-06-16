package com.store.project.controller.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String companyName;
    private String email;
    private  String password;
    private String username;
    private LocalDate dateOfJoin;

    private String address;
}
