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
    @Qualifier(value = "distributedCommandGateway")
    @Autowired
    private transient CommandGateway commandGateway;


    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handleOrderCreationEvent(OrderCreatedEvent orderCreatedEvent) {
        log.info("Start SAGA handleOrderCreationEvent ........ ");
        log.debug("Order Id for handleOrderCreationEvent : {} ", orderCreatedEvent.getOrderId());
        this.orderId = orderCreatedEvent.getOrderId();
        this.productId = orderCreatedEvent.getProductId();
        this.count = orderCreatedEvent.getNumber();
        log.info("Saga started for new order created with id: {}, for {} nos. of product with id: {} ",
                orderId, count, productId);
        log.info("End SAGA handleOrderCreationEvent");
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handleOrderCanceledEvent(OrderCancelEvent orderCancelEvent) {
        log.info("Start SAGA handleOrderCanceledEvent");
        log.info("Order ID for OrderCancelledEvent : {}", orderCancelEvent.getOrderId());
        this.commandGateway.send(new ProductStockRevertCommand(productId, count));
        log.info("Saga ending for order with id: {}, Command send for reverting {} nos. of product with id: {}",
                orderId, count, productId);

        log.info("End SAGA handleOrderCanceledEvent");
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handleOrderConfirmedEvent(OrderConfirmEvent orderConfirmEvent){
        log.info("Start SAGA handleOrderConfirmedEvent ............");
        log.info("Order ID for OrderConfirmedEvent : {}", orderConfirmEvent.getOrderId());
        log.info("Saga ending for order with id: {}, Order confirmed.", orderId);
        log.info("End SAGA handleOrderConfirmedEvent ..........");
    }


}
