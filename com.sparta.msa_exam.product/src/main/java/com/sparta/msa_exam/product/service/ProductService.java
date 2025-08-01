package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto save(String name, Integer price){

        Product product = new Product(name, price);
        productRepository.save(product);

        return new ProductResponseDto(product.getProduct_id(), product.getName(), product.getSupply_price());
    }

    public List<ProductResponseDto> findAll(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::toDto)
                .toList();
    }

}
