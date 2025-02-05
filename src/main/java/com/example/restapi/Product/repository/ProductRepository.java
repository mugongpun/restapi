package com.example.restapi.Product.repository;

import com.example.restapi.Product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    boolean existsByName(String name);

    @Query("select p from Product p")
    List<Product> findAllProduct();

}
