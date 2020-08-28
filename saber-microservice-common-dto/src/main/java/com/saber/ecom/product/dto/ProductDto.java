package com.saber.ecom.product.dto;

import java.io.Serializable;
import java.util.Objects;

public class ProductDto implements Serializable,Cloneable {
    private Integer id;
    private Double price;
    private Integer stock;
    private String description;

    public ProductDto() {
    }

    public ProductDto(Integer id, Double price, Integer stock, String description) {
        this.id = id;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(price, that.price) &&
                Objects.equals(stock, that.stock) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, stock, description);
    }

    @Override
    public ProductDto clone()  {
        try {
            return (ProductDto)super.clone();
        }catch (CloneNotSupportedException ex){
            return null;
        }
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                '}';
    }
}
