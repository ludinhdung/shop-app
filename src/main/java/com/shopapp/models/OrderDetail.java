package com.shopapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "price")
    private Float price;

    @Column(name = "number_of_products")
    private Integer numberOfProducts;

    @Column(name = "total_money")
    private Float totalMoney;

    @Size(max = 20)
    @Column(name = "color", length = 20)
    private String color;

}