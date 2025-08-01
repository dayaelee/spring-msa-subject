package com.sparta.msa_exam.order.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderProductDto {
    private Long order_id;
    private Long product_id;

}
