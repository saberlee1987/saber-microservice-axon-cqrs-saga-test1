package com.saber.ecom.order.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class OrderAuditDto implements Serializable {
    private Integer id;
    private String orderStatus;
    private Date date;

    public OrderAuditDto() {
    }

    public OrderAuditDto(Integer id, String orderStatus, Date date) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderAuditDto that = (OrderAuditDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orderStatus, that.orderStatus) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderStatus, date);
    }

    @Override
    public String toString() {
        return "OrderAuditDto{" +
                "id=" + id +
                ", orderStatus='" + orderStatus + '\'' +
                ", date=" + date +
                '}';
    }
}
