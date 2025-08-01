package com.sparta.msa_exam.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderProductResponseDto {
    private  Long order_id;
    private List<Long> product_ids;

    public OrderProductResponseDto(Long order_id, List<Long> product_ids) {
        this.order_id = order_id;
        this.product_ids = product_ids;
    }
}
