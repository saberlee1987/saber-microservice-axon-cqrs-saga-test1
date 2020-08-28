package com.saber.ecom.order.api.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.io.Serializable;
import java.util.Objects;

public class OrderStatusUpdateCommand implements Serializable {
    @TargetAggregateIdentifier
    private Integer orderId;
    private String orderStatus;

    public OrderStatusUpdateCommand() {
    }

    public OrderStatusUpdateCommand(Integer orderId, String orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatusUpdateCommand that = (OrderStatusUpdateCommand) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(orderStatus, that.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderStatus);
    }

    @Override
    public String toString() {
        return "OrderStatusUpdateCommand{" +
                "orderId=" + orderId +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
