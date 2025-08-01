package com.sparta.msa_exam.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
@Table(name = "orders")
@NoArgsConstructor
public class Orders extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @OneToMany(targetEntity = OrderProduct.class)
    private Long order_id;

    // Order 하나에 여러 Product가 매핑됨 (1:N)
    @OneToMany(mappedBy = "orders")
    private List<OrderProduct> product_ids = new ArrayList<>();

}
