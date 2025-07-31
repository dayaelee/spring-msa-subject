package com.sparta.msa_exam.product.entity;


import com.sparta.msa_exam.product.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 데이어베이스 테이블의 기본키 값을 자동으로 생성하도록 지정, AUTO_INCREMENT
    private Long product_id;

    @Column(nullable = false)
    // null 값을 허용하지 않음
    private String name;

    @Column(nullable = false)
    private Integer supply_price;

    public Product(String name, Integer supply_price) {
        this.name = name;
        this.supply_price = supply_price;
    }
}
