package com.saber.rmicroservice.commandand.event.models.order;

import com.saber.ecom.order.api.event.OrderCancelEvent;
import com.saber.ecom.order.api.event.OrderConfirmEvent;
import com.saber.ecom.order.api.event.OrderCreatedEvent;
import com.saber.rmicroservice.commandand.event.models.product.ProductEntity;
import org.axonframework.domain.AbstractAggregateRoot;

import javax.persistence.*;

@Entity
@Table(name = "ecom_order")
public class OrderEntity extends AbstractAggregateRoot<Integer> {
    @Id
    private Integer id;
    @Column(name = "price",nullable = false)
    private Double price;
    @Column(name = "number",nullable = false)
    private Integer number;
    @Column(name = "order_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public OrderEntity() {
    }

    public OrderEntity(Integer id, Double price, Integer number,
                       OrderStatusEnum orderStatusEnum, ProductEntity product) {
        this.id=id;
        this.price=price;
        this.number=number;
        this.orderStatusEnum=orderStatusEnum;
        this.product=product;
        registerEvent(new OrderCreatedEvent(id,price,number,
                product.getDescription(),orderStatusEnum.toString(),
                product.getId()));
    }

    @Override
    public Integer getIdentifier() {
        return this.id;
    }

    public void updateOrderStatus(OrderStatusEnum orderStatusEnum){
        if (orderStatusEnum == OrderStatusEnum.CONFIRMED){
             this.orderStatusEnum=orderStatusEnum;
             registerEvent(new OrderConfirmEvent(this.id));
        }
        else if (orderStatusEnum == OrderStatusEnum.CANCELLED){
            this.orderStatusEnum=orderStatusEnum;
            registerEvent(new OrderCancelEvent(this.id));
        }
    }
}
