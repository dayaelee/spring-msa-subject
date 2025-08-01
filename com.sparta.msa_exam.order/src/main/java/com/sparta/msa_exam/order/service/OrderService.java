package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.dto.OrderProductDto;
import com.sparta.msa_exam.order.dto.OrderProductRequestDto;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.entity.Orders;
import com.sparta.msa_exam.order.repository.OrderProductRepository;
import com.sparta.msa_exam.order.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderResponseDto save(OrderRequestDto request, boolean fail){
        // 1. 주문 엔티티 생성 및 저장
        Orders orders = new Orders();
        ordersRepository.save(orders); // order_id

        List<OrderProductDto> orderProductDtos = new ArrayList<>();

        // 2. 주문상품 엔티티 생성 및 저장
        for (OrderProductRequestDto productRequest : request.getProducts()){
            // 실패 시뮬레이션
            if (fail) {
                throw new RuntimeException("잠시 후에 주문 추가를 요청 해주세요.");
            }
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrders(orders);
            orderProduct.setProduct_id(productRequest.getProduct_id());
            orderProductRepository.save(orderProduct);

            orderProductDtos.add(new OrderProductDto(orders.getOrder_id(), productRequest.getProduct_id()));
        }

        return new OrderResponseDto(orders.getOrder_id(), orderProductDtos);
//        return new OrderResponseDto(orders.getOrder_id(), orders.getProduct_ids());
    }

    public OrderResponseDto add(Long order_id, Long product_id){
        // 주문 상품 추가
        OrderProduct orderProduct = new OrderProduct();
        Orders orders = ordersRepository.getReferenceById(order_id);

        orderProduct.setOrders(orders);
        orderProduct.setProduct_id(product_id);
        orderProductRepository.save(orderProduct);

        List<OrderProductDto> orderProductDtos = new ArrayList<>();

        for(OrderProduct product: orders.getProduct_ids()) {
            orderProductDtos.add(new OrderProductDto(order_id, product.getProduct_id()));
        }

        return new OrderResponseDto(orders.getOrder_id(), orderProductDtos);
    }

    public OrderResponseDto get(Long order_id){
        Orders orders = ordersRepository.getReferenceById(order_id);
//        OrderProduct orderProduct = orderProductRepository.getReferenceById(order_id);
//        System.out.println(orderProduct.toString()+" 안되나요?");
        // List<OrderProduct> orderProducts = findOrder.getOrderProduct();

        List<OrderProductDto> orderProductDtos = new ArrayList<>();

        for(OrderProduct product: orders.getProduct_ids()) {
            orderProductDtos.add(new OrderProductDto(product.getOrders().getOrder_id(), product.getProduct_id()));
        }

//        return new OrderResponseDto(orders.getOrder_id(), orders.getProduct_ids());
        return new OrderResponseDto(orders.getOrder_id(), orderProductDtos);
    }
}
