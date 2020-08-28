package com.saber.ecom.product.api.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.io.Serializable;
import java.util.Objects;

public class ProductStockRevertCommand implements Serializable {
    @TargetAggregateIdentifier
    private Integer productId;
    private Integer  count;

    public ProductStockRevertCommand() {
    }

    public ProductStockRevertCommand(Integer productId, Integer count) {
        this.productId = productId;
        this.count = count;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductStockRevertCommand that = (ProductStockRevertCommand) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, count);
    }

    @Override
    public String toString() {
        return "ProductStockRevertCommand{" +
                "productId=" + productId +
                ", count=" + count +
                '}';
    }
}
