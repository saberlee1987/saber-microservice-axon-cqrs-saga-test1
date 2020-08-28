package com.saber.microservice.command.rest.controllers;

import com.saber.ecom.order.dto.OrderAuditDto;
import com.saber.microservice.command.rest.services.EcomService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/orderAudit")
public class OrderAuditController {

    private final EcomService ecomService;

    public OrderAuditController(EcomService ecomService) {
        this.ecomService = ecomService;
    }
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderAuditDto>> getOrderAuditDtoList(){
        return ResponseEntity.ok(this.ecomService.getOrderAuditList());
    }
}
