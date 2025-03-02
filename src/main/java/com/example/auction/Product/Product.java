package com.example.auction.Product;

import com.example.auction.Global.BaseEntity;
import com.example.auction.User.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false , columnDefinition = "varchar(50)" )
    private String name;

    @Column(nullable = false , columnDefinition = "varchar(500)" )
    private String content;

    @Column(columnDefinition = "varchar(320)" )
    private String image;

    public Product(){}

}
