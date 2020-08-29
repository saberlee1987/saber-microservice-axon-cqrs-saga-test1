package com.saber.microservice.handle.event.core.events;

import com.saber.ecom.order.api.event.OrderCancelEvent;
import com.saber.ecom.order.api.event.OrderConfirmEvent;
import com.saber.ecom.order.api.event.OrderCreatedEvent;
import com.saber.microservice.handle.event.core.models.OrderAuditDtoEntity;
import com.saber.microservice.handle.event.core.repositories.OrderAuditDtoRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class OrderAuditDtoHandler {
    private final OrderAuditDtoRepository orderAuditDtoRepository;
       public OrderAuditDtoHandler(OrderAuditDtoRepository orderAuditDtoRepository) {
        this.orderAuditDtoRepository = orderAuditDtoRepository;
    }
    @EventHandler
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent){
        log.info("Start handleOrderCreatedEvent ..............");
        log.debug("OrderCreated Event ===> {}",orderCreatedEvent);
        OrderAuditDtoEntity orderAuditDtoEntity = new OrderAuditDtoEntity();
        orderAuditDtoEntity.setId(orderCreatedEvent.getOrderId());
        orderAuditDtoEntity.setStatus(orderCreatedEvent.getOrderStatus());
        orderAuditDtoEntity.setDate(new Date());
       this.orderAuditDtoRepository.save(orderAuditDtoEntity);

        log.info("End handleOrderCreatedEvent ..............");
    }

    @EventHandler
    public void handleOrderConfirmedEvent(OrderConfirmEvent orderConfirmEvent){
        log.info("Start handleOrderConfirmedEvent ....................");
        log.debug("OrderConfirmedEvent  ===> {} ",orderConfirmEvent);
        Optional<OrderAuditDtoEntity> optionalOrderAuditDtoEntity = this.orderAuditDtoRepository.findById(orderConfirmEvent.getOrderId());
        if (optionalOrderAuditDtoEntity.isPresent()){
            OrderAuditDtoEntity orderAuditDtoEntity = optionalOrderAuditDtoEntity.get();
            orderAuditDtoEntity.setStatus("CONFIRMED");
            orderAuditDtoEntity.setDate(new Date());
            OrderAuditDtoEntity entity = this.orderAuditDtoRepository.save(orderAuditDtoEntity);
            log.debug("OrderAuditDto Entity Updated ===> {}",entity);
        }
        log.info("End handleOrderConfirmedEvent ....................");
    }

    @EventHandler
    public void handleOrderCanceledEvent(OrderCancelEvent orderCancelEvent){
        log.info("Start handleOrderCanceledEvent ....................");
        log.debug("OrderCancelEvent  ===> {} ",orderCancelEvent);
        Optional<OrderAuditDtoEntity> optionalOrderAuditDtoEntity = this.orderAuditDtoRepository.findById(orderCancelEvent.getOrderId());
        if (optionalOrderAuditDtoEntity.isPresent()){
            OrderAuditDtoEntity orderAuditDtoEntity = optionalOrderAuditDtoEntity.get();
            orderAuditDtoEntity.setStatus("CANCELLED");
            orderAuditDtoEntity.setDate(new Date());
            OrderAuditDtoEntity entity = this.orderAuditDtoRepository.save(orderAuditDtoEntity);
            log.debug("OrderAuditDto Entity Updated ===> {}",entity);
        }
        log.info("End handleOrderCanceledEvent ....................");
    }
}
