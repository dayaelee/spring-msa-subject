package com.sparta.msa_exam.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private final Long id;
    private String username;
    private String accessToken;
    private String refreshToken;

    public LoginResponseDto(Long id, String username, String accessToken) {
        this.id = id;
        this.username = username;
        this.accessToken = accessToken;
    }

    public LoginResponseDto(Long id){
        this.id = id;
    }

    public LoginResponseDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
