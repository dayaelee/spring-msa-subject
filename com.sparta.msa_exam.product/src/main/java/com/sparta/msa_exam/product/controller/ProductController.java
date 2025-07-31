package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 추가 API
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> addProduct(@Validated @ModelAttribute ProductRequestDto product) {
        // 상품 추가 쿼리 호출

        ProductResponseDto productResponseDto =
                productService.save(
                        product.getName(),
                        product.getSupply_price()
                );

        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }
    // 상품 목록 조회 API
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts(){

        // 상품 리스트 받아옴
        List<ProductResponseDto> productResponseDtoList = productService.findAll();

        return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
    }




}
