package com.sparta.msa_exam.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    // Product 서비스에서 관리하는 상품 ID
    private List<OrderProductRequestDto> products;

}
