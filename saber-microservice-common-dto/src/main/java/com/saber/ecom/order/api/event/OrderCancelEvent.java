package com.saber.ecom.order.api.event;

import java.io.Serializable;
import java.util.Objects;

public class OrderCancelEvent  implements Serializable {
    private Integer orderId;

    public OrderCancelEvent() {
    }

    public OrderCancelEvent(Integer orderId) {
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
        OrderCancelEvent that = (OrderCancelEvent) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "OrderCancelEvent{" +
                "orderId=" + orderId +
                '}';
    }
}
