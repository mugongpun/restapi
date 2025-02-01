package com.example.restapi.repository;

import com.example.restapi.entity.Order;
import com.example.restapi.repository.custom.OrderCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustom {
}
