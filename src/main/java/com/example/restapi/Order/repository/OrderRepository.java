package com.example.restapi.Order.repository;

import com.example.restapi.Order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustom {
}
