package com.sparta.msa_exam.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class SignUpResponseDto {
    private Long id;
    private String username;
    private String password;
    private String accessToken;
    private String refreshToken;


    public SignUpResponseDto(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
