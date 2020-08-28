package com.saber.rmicroservice.commandand.event.sagas;

import com.saber.ecom.order.api.event.OrderCancelEvent;
import com.saber.ecom.order.api.event.OrderConfirmEvent;
import com.saber.ecom.order.api.event.OrderCreatedEvent;
import com.saber.ecom.product.api.command.ProductStockRevertCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.EndSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Component
public class OrderProcessSaga extends AbstractAnnotatedSaga implements Serializable {

    private Integer orderId;
    private Integer productId;
    private Integer count;
    private transient CommandGateway commandGateway;

    @Autowired
    public OrderProcessSaga(@Qualifier(value = "distributedCommandGateway") CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handleOrderCreationEvent(OrderCreatedEvent orderCreatedEvent) {
        log.info("Start handleOrderCreationEvent ........ ");
        log.debug("Order Id for handleOrderCreationEvent : {} ", orderCreatedEvent.getOrderId());
        this.orderId = orderCreatedEvent.getOrderId();
        this.productId = orderCreatedEvent.getProductId();
        this.count = orderCreatedEvent.getNumber();
        log.debug("Saga started for new order created with id: {}, for {} nos. of product with id: {} ",
                orderId, count, productId);
        log.info("End handleOrderCreationEvent");
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handleOrderCanceledEvent(OrderCancelEvent orderCancelEvent) {
        log.info("Start handleOrderCanceledEvent");
        log.debug("Order ID for OrderCancelledEvent : {}", orderCancelEvent.getOrderId());
        this.commandGateway.send(new ProductStockRevertCommand(productId, count));
        log.debug("Saga ending for order with id: {}, Command send for reverting {} nos. of product with id: {}",
                orderId, count, productId);

        log.info("End handleOrderCanceledEvent");
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handleOrderConfirmedEvent(OrderConfirmEvent orderConfirmEvent){
        log.info("Start handleOrderConfirmedEvent ............");
        log.debug("Order ID for OrderConfirmedEvent : {}", orderConfirmEvent.getOrderId());
        log.debug("Saga ending for order with id: {}, Order confirmed.", orderId);
        log.info("End handleOrderConfirmedEvent ..........");
    }


}
