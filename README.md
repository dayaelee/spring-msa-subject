

#📌 Spring MSA Exam Project

##📖 프로젝트 소개

spring-msa-subject는 Spring Boot와 Spring Cloud를 이용한 마이크로서비스 아키텍처(MSA) 예제 프로젝트입니다.
서비스를 인증, 게이트웨이, 주문, 상품 등으로 분리하여 독립적으로 개발·배포 가능하도록 구성하였으며,
서비스 간 통신은 Spring Cloud OpenFeign과 Gateway를 통해 이루어집니다.

##🏗 아키텍처 구성

- Auth Service (com.sparta.msa_exam.auth)

    -  사용자 인증/인가 처리

    -  토큰 발급 및 검증

- Gateway Service (com.sparta.msa_exam.gateway)

    -  API 라우팅 및 인증 필터 적용

- Order Service (com.sparta.msa_exam.order)

    -  주문 생성, 조회, 관리 기능

    -  상품 서비스와 통신하여 재고 확인

- Product Service (com.sparta.msa_exam.product)

    -  상품 등록, 조회, 재고 관리

- Common Module (com.sparta.msa_exam)

    -  공통 설정, 예외 처리, 유틸리티

##⚙ 기술 스택
-  Java 17

-  Spring Boot 3.x

-  Spring Cloud Gateway

-  Spring Cloud OpenFeign

-  Spring Security

-  Spring Data JPA

-  MySQL

-  Gradle

##📂 디렉토리 구조

spring-msa-subject

├── com.sparta.msa_exam.auth      # 인증 서비스

├── com.sparta.msa_exam.gateway   # 게이트웨이 서비스

├── com.sparta.msa_exam.order     # 주문 서비스

├── com.sparta.msa_exam.product   # 상품 서비스

├── com.sparta.msa_exam           # 공통 설정

└── README.md

##🚀 실행 방법

1. 환경 준비
-  Java 17 이상

-  MySQL 설치

-  Gradle 설치 (또는 Wrapper 사용)

2. 데이터베이스 생성
3. 설정 파일 수정

   각 서비스의 application.yml에서 DB, 포트, JWT 시크릿 키 등을 환경에 맞게 수정

4. 서비스 실행 순서
    1. Server
    1. Gateway Service
    1. Auth Service
    1. Product Service
    4. Order Service


##📡 주요 기능

JWT 기반 인증/인가

API Gateway 통한 라우팅

OpenFeign으로 서비스 간 통신

주문-상품 연동

모듈 간 의존성 최소화

##📌 참고 사항

서비스 간 호출은 FeignClient를 사용합니다.

Gateway에서 인증 필터를 적용하여 API 접근을 제어합니다.

상품/주문 서비스는 MySQL을 사용하며, 실행 전 DB 연결이 필요합니다.

