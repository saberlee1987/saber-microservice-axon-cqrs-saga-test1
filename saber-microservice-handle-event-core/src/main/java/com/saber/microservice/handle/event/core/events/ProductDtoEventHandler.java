package com.saber.microservice.handle.event.core.events;

import com.saber.ecom.product.api.event.StockUpdateEvent;
import com.saber.microservice.handle.event.core.models.ProductDtoEntity;
import com.saber.microservice.handle.event.core.repositories.ProductDtoRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ProductDtoEventHandler {

    private final ProductDtoRepository productDtoRepository;

    public ProductDtoEventHandler(ProductDtoRepository productDtoRepository) {
        this.productDtoRepository = productDtoRepository;
    }
    @EventHandler
    public void handleUpdateProduct(StockUpdateEvent stockUpdateEvent){
        log.info("Start handleUpdateProduct ......................");
        Optional<ProductDtoEntity> optionalProductDtoEntity = this.productDtoRepository.findById(stockUpdateEvent.getId());
        if (optionalProductDtoEntity.isPresent()){
            ProductDtoEntity productDtoEntity = optionalProductDtoEntity.get();
            productDtoEntity.setStock(stockUpdateEvent.getStock());
            ProductDtoEntity entity = this.productDtoRepository.save(productDtoEntity);
            log.debug("ProductEntity Save ====> {} ",entity);
        }
        log.info("End handleUpdateProduct .........................");
    }
}
