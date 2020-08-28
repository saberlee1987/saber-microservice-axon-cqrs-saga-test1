package com.saber.microservice.command.rest.controllers;

import com.saber.ecom.order.api.command.NewOrderCommand;
import com.saber.ecom.order.api.command.OrderStatusUpdateCommand;
import com.saber.ecom.order.dto.OrderCreatedDto;
import com.saber.ecom.order.dto.OrderDto;
import com.saber.ecom.order.dto.OrderStatusUpdateDto;
import com.saber.microservice.command.rest.services.EcomService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final EcomService ecomService;

    private final CommandGateway commandGateway;

    public OrderController(EcomService ecomService,
                           @Qualifier(value = "distributedCommandGateway")
                                   CommandGateway commandGateway) {
        this.ecomService = ecomService;
        this.commandGateway = commandGateway;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Void> addNewOrder(@RequestBody OrderCreatedDto orderCreatedDto) {
        NewOrderCommand orderCommand = new NewOrderCommand(orderCreatedDto.getProductId(),
                orderCreatedDto.getPrice(),
                orderCreatedDto.getNumber());
        this.commandGateway.sendAndWait(orderCommand);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<Void> updateOrder(@RequestBody OrderStatusUpdateDto orderStatusUpdateDto){
        OrderStatusUpdateCommand orderStatusUpdateCommand= new OrderStatusUpdateCommand();

        orderStatusUpdateCommand.setOrderId(orderStatusUpdateDto.getOrderId());
        orderStatusUpdateCommand.setOrderStatus(orderStatusUpdateDto.getOrderStatus());

        this.commandGateway.sendAndWait(orderStatusUpdateCommand);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<OrderDto>> getOrderDtoList(){
        return ResponseEntity.ok(this.ecomService.getOrderDtoList());
    }

}
