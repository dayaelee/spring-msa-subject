package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.dto.OrderProductRequestDto;
import com.sparta.msa_exam.order.dto.OrderProductResponseDto;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// 이 클래스는 REST API 엔드포인트다.
// 메서드가 리턴하는 값은 바로 HTTP 응답 본문(Body)에 넣겠다.
@RequiredArgsConstructor
// Lombok에서 제공하는 어노테이션
// final이 붙은 필드나 @NonNull이 붙은 필드만 골라서 자동으로
// 생성자를 만들어줌
public class OrderController {
    @Value("${server.port}")
    private String port;

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> addOrder(
            @RequestBody OrderRequestDto order,
            @RequestParam(value = "fail", required = false) boolean fail) {
        // @ModelAttribute 형태는 지금 상황에서 부적절함
        //    {
        //        "products": [
        //        { "product_id": 99 },
        //        { "product_id": 88 }
        //        ]
        //    }
        // 이런 식으로 하나의 주문에 (상품 여러개) 받을 것이기 때문임 => JSON 요청

        // 주문 추가 쿼리 호출
        OrderResponseDto orderResponseDto = orderService.save(order, fail);

        return ResponseEntity.ok(orderResponseDto);
    }

    // 주문에 상품을 추가하는 API
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> addProduct(@PathVariable("orderId") Long order_id, @RequestBody OrderProductRequestDto product){

        // 주문에 상품을 추가하는 API를 조건에 맞게 구성하기

        boolean exists = orderService.checkProductExists(product.getProduct_id());

        if (!exists) {
            return ResponseEntity.badRequest().body(null);
        }


        OrderResponseDto orderResponseDto = orderService.add(order_id, product.getProduct_id());

        return ResponseEntity.ok(orderResponseDto);
    }

    // 주문 단 건 조회 API
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable("orderId") Long order_id){
        OrderResponseDto orderResponseDto = orderService.get(order_id);
        return ResponseEntity.ok(orderResponseDto);
    }







}
