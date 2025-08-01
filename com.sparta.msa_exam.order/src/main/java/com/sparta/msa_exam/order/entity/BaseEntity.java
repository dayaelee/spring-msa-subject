package com.sparta.msa_exam.order.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
// JPA 상속 매핑용 어노테이션
// 해당 클래스를 엔티티가 아닌 부모 클래스로 사용하게 함
// 이 클래스를 상속받는 실제 엔티티 클래스에 이 클래스의 필드들이 컬럼으로 매핑됨
// 하지만 BaseEntity 자체는 테이블이 생성되지 않음
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

}
