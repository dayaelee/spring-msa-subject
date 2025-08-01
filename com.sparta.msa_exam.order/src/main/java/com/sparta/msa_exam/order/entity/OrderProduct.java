package com.sparta.msa_exam.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_product")
@NoArgsConstructor
public class OrderProduct extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 다대일 관계: 여러 OrderProduct가 하나의 Order에 속함
    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    // MSA라서 Product 엔티티를 직접 참조 하지 않음
    // 대신 productId만 Long 타입으로 저장
    @Column(nullable = false)
    private Long product_id;

}
