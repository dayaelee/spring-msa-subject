package com.sparta.msa_exam.order.repository;


import com.sparta.msa_exam.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
