package com.saber.ecom.product.api.event;

import java.io.Serializable;
import java.util.Objects;

public class StockUpdateEvent implements Serializable {
    private Integer id;
    private Integer stock;

    public StockUpdateEvent() {
    }

    public StockUpdateEvent(Integer id, Integer stock) {
        this.id = id;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockUpdateEvent that = (StockUpdateEvent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock);
    }

    @Override
    public String toString() {
        return "StockUpdateEvent{" +
                "id=" + id +
                ", stock=" + stock +
                '}';
    }
}
