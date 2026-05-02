package com.ets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
}