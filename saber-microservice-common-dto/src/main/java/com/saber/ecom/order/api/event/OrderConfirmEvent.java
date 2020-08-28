package com.saber.ecom.order.api.event;

import java.io.Serializable;
import java.util.Objects;

public class OrderConfirmEvent implements Serializable {

    private Integer orderId;

    public OrderConfirmEvent() {
    }

    public OrderConfirmEvent(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderConfirmEvent that = (OrderConfirmEvent) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "OrderConfirmEvent{" +
                "orderId=" + orderId +
                '}';
    }
}
