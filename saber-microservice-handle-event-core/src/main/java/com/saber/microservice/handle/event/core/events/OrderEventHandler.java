package com.saber.microservice.handle.event.core.events;

import com.saber.ecom.order.api.event.OrderCreatedEvent;
import com.saber.microservice.handle.event.core.models.OrderDtoEntity;
import com.saber.microservice.handle.event.core.repositories.OrderDtoRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventHandler {

    private final OrderDtoRepository orderDtoRepository;
       public OrderEventHandler(OrderDtoRepository orderDtoRepository) {
        this.orderDtoRepository = orderDtoRepository;
          }
    @EventHandler
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent){
        log.info("Start handleOrderCreatedEvent ...........");
        OrderDtoEntity orderDtoEntity= new OrderDtoEntity();
        orderDtoEntity.setId(orderCreatedEvent.getOrderId());
        orderDtoEntity.setDescription(orderCreatedEvent.getProductDescription());
        orderDtoEntity.setPrice(orderCreatedEvent.getPrice());
        orderDtoEntity.setNumber(orderCreatedEvent.getNumber());
        orderDtoEntity.setStatus(orderCreatedEvent.getOrderStatus());

        OrderDtoEntity entity = this.orderDtoRepository.save(orderDtoEntity);
        log.debug("orderEntity Saved ===> {} ",entity);
        log.info("End handleOrderCreatedEvent ...........");
    }
}
