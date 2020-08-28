package com.saber.ecom.order.dto;

import java.io.Serializable;
import java.util.Objects;

public class OrderCreatedDto implements Serializable, Cloneable {

    private Integer productId;
    private Double price;
    private Integer number;

    public OrderCreatedDto() {
    }

    public OrderCreatedDto(Double price, Integer number,Integer productId) {
        this.price = price;
        this.number = number;
        this.productId=productId;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderCreatedDto that = (OrderCreatedDto) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(price, that.price) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, price, number);
    }

    @Override
    public String toString() {
        return "OrderCreatedDto{" +
                "productId=" + productId +
                ", price=" + price +
                ", number=" + number +
                '}';
    }
}
