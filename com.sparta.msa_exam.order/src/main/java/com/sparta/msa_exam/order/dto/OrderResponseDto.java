package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.OrderProduct;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponseDto {
    private Long order_id;
    private List<OrderProductDto> product_ids;

    public OrderResponseDto(Long order_id, List<OrderProductDto> product_ids){
        this.order_id = order_id;
        this.product_ids = product_ids;
    }
}
