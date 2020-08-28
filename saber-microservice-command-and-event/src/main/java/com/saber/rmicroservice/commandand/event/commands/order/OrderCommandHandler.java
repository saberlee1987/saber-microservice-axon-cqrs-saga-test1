package com.saber.rmicroservice.commandand.event.commands.order;

import com.saber.ecom.order.api.command.NewOrderCommand;
import com.saber.ecom.order.api.command.OrderStatusUpdateCommand;
import com.saber.rmicroservice.commandand.event.models.order.OrderEntity;
import com.saber.rmicroservice.commandand.event.models.order.OrderStatusEnum;
import com.saber.rmicroservice.commandand.event.models.product.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@Slf4j
public class OrderCommandHandler {

    private final Repository<OrderEntity> orderEntityRepository;
    private final Repository<ProductEntity> productEntityRepository;
    private final SecureRandom random;

    public OrderCommandHandler(@Qualifier(value = "orderRepository")
                                       Repository<OrderEntity> orderEntityRepository,
                               @Qualifier(value = "productRepository")
                                       Repository<ProductEntity> productEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
        this.productEntityRepository = productEntityRepository;
        this.random = new SecureRandom();
    }

    @CommandHandler
    public void handleNewOrder(NewOrderCommand orderCommand) {
        log.info("Start handleNewOrder ................");
        ProductEntity productEntity = this.productEntityRepository.load(orderCommand.getProductId());
        productEntity.depreciateStock(orderCommand.getNumber());
        Integer id = random.nextInt() + 1;
        OrderEntity orderEntity =
                new OrderEntity(id, orderCommand.getPrice(), orderCommand.getNumber(), OrderStatusEnum.NEW, productEntity);
        this.orderEntityRepository.add(orderEntity);
        log.debug("New Order Created with id: {}; Price: {}; Numbers: {} of Product ID: {}",
                id, orderCommand.getPrice(), orderCommand.getNumber(), productEntity.getId());

        log.info("End handleNewOrder ....................");

    }

    @CommandHandler
    public void handleUpdateOrder(OrderStatusUpdateCommand orderStatusUpdateCommand) {
        log.info("Start handleUpdateOrder .............");
        OrderEntity orderEntity = this.orderEntityRepository.load(orderStatusUpdateCommand.getOrderId());
        orderEntity.updateOrderStatus(OrderStatusEnum.valueOf(orderStatusUpdateCommand.getOrderStatus()));
        log.debug("Order with id: {} updated with status: {}", orderStatusUpdateCommand.getOrderId(),
                orderStatusUpdateCommand.getOrderStatus());
        log.info("End handleUpdateOrder .............");
    }
}
