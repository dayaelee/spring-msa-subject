package com.sparta.msa_exam.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;

//@Component
@Slf4j
public class LocalJwtAuthenticationFilter implements GlobalFilter {
    @Value("${service.jwt.secret-key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (isWhiteListPath(path)){
            return chain.filter(exchange);
        }

        String token = extractToken(exchange);

        if(token == null) {
            return unauthorized(exchange);
        }

        Claims claims;
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // 만료 체크
            if (claims.getExpiration() != null && claims.getExpiration().before(new Date())) {
                return unauthorized(exchange);
            }

        } catch (Exception e) {
            return unauthorized(exchange);
        }

        // 4. 사용자 정보 헤더에 추가 (상품/주문 서비스로 전달)
        exchange = exchange.mutate().request(
                exchange.getRequest().mutate()
                        .header("X-User-Id", claims.get("user_id", String.class))
                        .header("X-User-Role", claims.get("role", String.class))
                        .build()
        ).build();

        // 5. 다음 필터로 요청 전달
        return chain.filter(exchange);
    }

    // 화이트리스트 경로
    private boolean isWhiteListPath(String path) {
        return path.startsWith("/auth/sign-in") ||
                path.startsWith("/auth/sign-up") ||
                path.startsWith("/auth/refresh-token");
    }


    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        // 2. accessToken 쿠키에서 추출
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst("accessToken");
        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token);

            Claims claims = claimsJws.getPayload();
            log.info("#####payload :: " + claimsJws.getPayload().toString());

            // 추가적인 검증 로직 (예: 토큰 만료 여부 확인 등)을 여기에 추가할 수 있습니다.

            // 만료 여부 체크
            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                log.warn("JWT 토큰이 만료되었습니다.");
                return false;
            }


            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
