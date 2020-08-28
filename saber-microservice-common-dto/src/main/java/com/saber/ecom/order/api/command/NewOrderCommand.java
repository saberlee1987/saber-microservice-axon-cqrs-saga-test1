package com.saber.ecom.order.api.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.io.Serializable;
import java.util.Objects;

public class NewOrderCommand implements Serializable {

    @TargetAggregateIdentifier
    private Integer productId;
    private Double price;
    private Integer number;

    public NewOrderCommand() {
    }

    public NewOrderCommand(Integer productId, Double price, Integer number) {
        this.productId = productId;
        this.price = price;
        this.number = number;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewOrderCommand that = (NewOrderCommand) o;
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
        return "NewOrderCommand{" +
                "productId=" + productId +
                ", price=" + price +
                ", number=" + number +
                '}';
    }
}
