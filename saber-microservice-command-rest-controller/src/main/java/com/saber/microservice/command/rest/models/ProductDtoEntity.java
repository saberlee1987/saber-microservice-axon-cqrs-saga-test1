package com.saber.microservice.command.rest.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ecom_product_view")
@Data
@EqualsAndHashCode
@ToString
public class ProductDtoEntity {
    @Id
    private Integer id;
    @Column(name = "price",nullable = false)
    private Double price;
    @Column(name = "stock",nullable = false)
    private Integer stock;
    @Column(name = "description",nullable = false)
    private String description;
}
