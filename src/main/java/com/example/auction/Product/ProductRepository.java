package com.example.auction.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new IllegalArgumentException(""));
    }

}
