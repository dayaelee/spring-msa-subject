package com.sparta.msa_exam.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
// Spring Cloud OpenFeign에서
// Feign 클라이언트를 스프링 빈으로 등록해주는 스위치임
@EnableJpaAuditing
// Spring Data JPA에서 엔티티의 생성일/수정일을 자동으로
// 기록할 수 있게 해주는 기능을 켜는 어노테이션임
// Auditing(감사)정보를 자동으로 넣어주는 스위치
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
