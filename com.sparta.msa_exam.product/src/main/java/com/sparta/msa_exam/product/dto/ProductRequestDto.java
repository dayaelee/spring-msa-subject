package com.sparta.msa_exam.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Getter
public class ProductRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private Integer supply_price;

}
