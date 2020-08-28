package com.saber.microservice.command.rest.controllers;

import com.saber.ecom.product.dto.ProductDto;
import com.saber.microservice.command.rest.services.EcomService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final EcomService ecomService;

    public ProductController(EcomService ecomService) {
        this.ecomService = ecomService;
    }
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> getProductDtoList(){
        return ResponseEntity.ok(this.ecomService.getProductDtoList());
    }
}
