package com.saber.rmicroservice.commandand.event.models.product;

import com.saber.ecom.product.api.event.StockUpdateEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.axonframework.domain.AbstractAggregateRoot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ecom_product")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class ProductEntity extends AbstractAggregateRoot<Integer> {
    @Id
    private Integer id;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "stock", nullable = false)
    private Integer stock;
    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public Integer getIdentifier() {
        return this.id;
    }

    public void depreciateStock(int count) {
        if (this.stock >= count) {
            this.stock -= count;
            registerEvent(new StockUpdateEvent(this.id, stock));
        } else {
            throw new RuntimeException(" out of Stock ==>" + stock + " <= " + count);
        }
    }

    public void revertStock(int count) {
        this.stock += count;
        registerEvent(new StockUpdateEvent(this.id, stock));
    }
}
