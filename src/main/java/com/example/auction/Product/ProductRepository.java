package com.example.auction.Product;

import com.example.auction.Global.error.errorcode.ErrorCode;
import com.example.auction.Global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

}
