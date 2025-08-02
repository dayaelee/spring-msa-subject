package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.dto.LoginRequestDto;
import com.sparta.msa_exam.auth.dto.LoginResponseDto;
import com.sparta.msa_exam.auth.dto.SignUpRequestDto;
import com.sparta.msa_exam.auth.dto.SignUpResponseDto;
import com.sparta.msa_exam.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @Value("${server.port}")
    private String port;


    private final AuthService authService;

    // login api
    @PostMapping("/auth/sign-in")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto){
        try {
            LoginResponseDto responseDto = authService.login(requestDto.getUsername(), requestDto.getPassword());

            ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", responseDto.getAccessToken())
                    .httpOnly(true)      // 자바스크립트 접근 불가
                    .secure(false)       // HTTPS에서만 true (개발 환경이면 false)
                    .path("/")           // 모든 경로에서 사용 가능
                    .maxAge(60 * 30)     // 30분 (Access Token 유효기간과 맞춤)
                    .sameSite("Strict")  // CSRF 방지 (Strict/Lax/None)
                    .build();

            return ResponseEntity.ok()
                    .header("Set-Cookie", accessTokenCookie.toString())
                    .body(responseDto); // body에는 사용자 정보나 Refresh Token만 내려줄 수 있음
        } catch (RuntimeException e){
            // 로그인 실패 예외처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // sign-in api
    @PostMapping("/auth/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto){
        // 회원가입 처리
        SignUpResponseDto signUpResponseDto =
                authService.signUp(
                        requestDto.getUsername(),
                        requestDto.getPassword()
                );
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    // Refresh Token으로 Access Token 재발급 API 추가
    @PostMapping("/auth/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (authService.validateRefreshToken(refreshToken)) {
            String newAccessToken = authService.reissueAccessToken(refreshToken);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }
    }


}
