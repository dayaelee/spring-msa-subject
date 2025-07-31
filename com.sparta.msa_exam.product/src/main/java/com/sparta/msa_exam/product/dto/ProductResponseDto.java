package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private Long product_id;
    private String name;
    private Integer supply_price;

    public ProductResponseDto(Long product_id, String name, Integer supply_price) {
        this.product_id = product_id;
        this.name = name;
        this.supply_price = supply_price;
    }

    public static ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(product.getProduct_id(), product.getName(), product.getSupply_price());
    }
}
