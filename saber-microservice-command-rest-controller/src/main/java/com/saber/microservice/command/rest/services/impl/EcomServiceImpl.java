package com.saber.microservice.command.rest.services.impl;

import com.saber.ecom.order.dto.OrderAuditDto;
import com.saber.ecom.order.dto.OrderDto;
import com.saber.ecom.product.dto.ProductDto;
import com.saber.microservice.command.rest.models.OrderAuditDtoEntity;
import com.saber.microservice.command.rest.models.OrderDtoEntity;
import com.saber.microservice.command.rest.models.ProductDtoEntity;
import com.saber.microservice.command.rest.repositories.OrderAuditDtoRepository;
import com.saber.microservice.command.rest.repositories.OrderDtoRepository;
import com.saber.microservice.command.rest.repositories.ProductDtoRepository;
import com.saber.microservice.command.rest.services.EcomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class EcomServiceImpl implements EcomService {
    private final OrderDtoRepository orderDtoRepository;
    private final OrderAuditDtoRepository orderAuditDtoRepository;
    private final ProductDtoRepository productDtoRepository;

    public EcomServiceImpl(OrderDtoRepository orderDtoRepository,
                           OrderAuditDtoRepository orderAuditDtoRepository,
                           ProductDtoRepository productDtoRepository) {
        this.orderDtoRepository = orderDtoRepository;
        this.orderAuditDtoRepository = orderAuditDtoRepository;
        this.productDtoRepository = productDtoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrderDtoList() {
        return this.getOrderDtoList(this.orderDtoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductDtoList() {
        return this.getProductDtoList(this.productDtoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderAuditDto> getOrderAuditList() {
        return this.getOrderAuditDto(this.orderAuditDtoRepository.findAll());
    }

    private List<OrderDto> getOrderDtoList(List<OrderDtoEntity> orderDtoEntityList){
        List<OrderDto> orderDtoList= new ArrayList<>();
        OrderDto orderDtoClone= new OrderDto();

        for (OrderDtoEntity entity : orderDtoEntityList) {
            OrderDto orderDto = orderDtoClone.clone();
            orderDto.setId(entity.getId());
            orderDto.setNumber(entity.getNumber());
            orderDto.setOrderStatus(entity.getStatus());
            orderDto.setPrice(entity.getPrice());
            orderDto.setProductDescription(entity.getDescription());

            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }

    private List<ProductDto> getProductDtoList(List<ProductDtoEntity> productDtoEntityList){
        List<ProductDto> productDtoList =new ArrayList<>();
        ProductDto productDtoClone= new ProductDto();

        for (ProductDtoEntity entity : productDtoEntityList) {
            ProductDto productDto= productDtoClone.clone();

            productDto.setId(entity.getId());
            productDto.setDescription(entity.getDescription());
            productDto.setPrice(entity.getPrice());
            productDto.setStock(entity.getStock());

            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    private List<OrderAuditDto> getOrderAuditDto(List<OrderAuditDtoEntity> orderAuditDtoEntityList){
        List<OrderAuditDto> orderAuditDtoList = new ArrayList<>();
        OrderAuditDto orderAuditDtoClone= new OrderAuditDto();

        for (OrderAuditDtoEntity entity : orderAuditDtoEntityList) {
            OrderAuditDto orderAuditDto= orderAuditDtoClone.clone();

            orderAuditDto.setId(entity.getId());
            orderAuditDto.setDate(entity.getDate());
            orderAuditDto.setOrderStatus(entity.getStatus());

            orderAuditDtoList.add(orderAuditDto);
        }


        return orderAuditDtoList;
    }
}
