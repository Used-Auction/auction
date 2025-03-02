package com.example.auction.Product;

import com.example.auction.Global.BaseEntity;
import com.example.auction.Product.Dto.ProductRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false , columnDefinition = "varchar(50)" )
    private String name;

    @Column(nullable = false , columnDefinition = "varchar(500)" )
    private String content;

    @Column(columnDefinition = "varchar(320)" )
    private String image;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus productStatus = ProductStatus.ACTIVE;

    public Product(){}

    public Product(Long userId , ProductRequestDto requestDto){
        this.userId = userId;
        this.name = requestDto.getName();
        this.content = requestDto.getContent();
        this.image = requestDto.getImage();
    }

    public void updateProduct(ProductRequestDto requestDto){
        this.name = requestDto.getName();
        this.content = requestDto.getContent();
        this.image = requestDto.getImage();
    }

}
