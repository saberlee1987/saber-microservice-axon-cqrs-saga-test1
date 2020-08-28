package com.saber.microservice.command.rest.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ecom_order_view")
@Data
@EqualsAndHashCode
@ToString
public class OrderDtoEntity {
    @Id
    private Integer id;
    @Column(name = "price",nullable = false)
    private Double price;
    @Column(name = "number",nullable = false)
    private Integer number;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "status",nullable = false,length = 50)
    private String status;
}
