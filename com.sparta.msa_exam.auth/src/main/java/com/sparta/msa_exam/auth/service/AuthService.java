package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.dto.LoginResponseDto;
import com.sparta.msa_exam.auth.dto.SignUpResponseDto;
import com.sparta.msa_exam.auth.entity.Member;
import com.sparta.msa_exam.auth.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final MemberRepository memberRepository;
    private final SecretKey secretKey;
    private final String issuer;
    private final Long accessExpiration;

    @Autowired
    public AuthService(
            MemberRepository memberRepository,
            @Value("${service.jwt.secret-key}") String secretKey,
            @Value("${spring.application.name}") String issuer,
            @Value("${service.jwt.access-expiration}") Long accessExpiration
    ) {
        this.memberRepository = memberRepository;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        this.issuer = issuer;
        this.accessExpiration = accessExpiration;
    }

    public String createAccessToken(String user_id){
        return Jwts.builder()
                .claim("user_id", user_id)
                .claim("role", "ADMIN")
                .issuer(issuer)
                .issuedAt(new Date((System.currentTimeMillis())))
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String userId) {
        long refreshExpiration = 1000L * 60 * 60 * 24 * 7; // 7일
        return Jwts.builder()
                .claim("user_id", userId)
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
    }

    public LoginResponseDto login(String username, String password){

        Member member = memberRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다."));

        // JWT Access Token 생성
        String accessToken = createAccessToken(member.getId().toString());
        String refreshToken = createRefreshToken(member.getId().toString());

        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

        return new LoginResponseDto(member.getId(), member.getUsername(), accessToken, refreshToken);
    }

    public SignUpResponseDto signUp(String username, String password){
        Member member = new Member(username, password);
        Member savedMember = memberRepository.save(member);

        String refreshToken = createRefreshToken(savedMember.getId().toString());
        savedMember.setRefreshToken(refreshToken);
        memberRepository.save(savedMember);

        String accessToken = createAccessToken(savedMember.getId().toString());

        return new SignUpResponseDto(savedMember.getId(), savedMember.getUsername(), savedMember.getPassword(), accessToken, refreshToken);
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String reissueAccessToken(String refreshToken) {
        // refreshToken에서 user_id 추출
        String userId = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .get("user_id", String.class);

        // 새로운 Access Token 발급
        return createAccessToken(userId);
    }


}
