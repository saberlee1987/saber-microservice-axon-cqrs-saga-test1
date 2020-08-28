package com.saber.ecom.order.api.event;

import java.io.Serializable;
import java.util.Objects;

public class OrderCreatedEvent implements Serializable {

    private Integer orderId;
    private Double price;
    private Integer number;
    private String productDescription;
    private String orderStatus;
    private Integer productId;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Integer orderId, Double price, Integer number, String productDescription, String orderStatus, Integer productId) {
        this.orderId = orderId;
        this.price = price;
        this.number = number;
        this.productDescription = productDescription;
        this.orderStatus = orderStatus;
        this.productId = productId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
        OrderCreatedEvent that = (OrderCreatedEvent) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(price, that.price) &&
                Objects.equals(number, that.number) &&
                Objects.equals(productDescription, that.productDescription) &&
                Objects.equals(orderStatus, that.orderStatus) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, price, number, productDescription, orderStatus, productId);
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId=" + orderId +
                ", price=" + price +
                ", number=" + number +
                ", productDescription='" + productDescription + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", productId=" + productId +
                '}';
    }
}
