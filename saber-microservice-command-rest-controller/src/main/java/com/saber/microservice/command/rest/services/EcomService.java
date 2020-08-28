package com.saber.microservice.command.rest.services;

import com.saber.ecom.order.dto.OrderAuditDto;
import com.saber.ecom.order.dto.OrderDto;
import com.saber.ecom.product.dto.ProductDto;

import java.util.List;

public interface EcomService {
    List<OrderDto> getOrderDtoList();
    List<ProductDto> getProductDtoList();
    List<OrderAuditDto> getOrderAuditList();
}
