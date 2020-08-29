package com.saber.rmicroservice.commandand.event.commands.product;

import com.saber.ecom.product.api.command.ProductStockRevertCommand;
import com.saber.rmicroservice.commandand.event.models.product.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductRevertCommandHandler {
    @Qualifier(value = "productRepository")
    @Autowired
    private Repository<ProductEntity> productEntityRepository;


    @CommandHandler
    public void handleNewOrder(ProductStockRevertCommand productStockRevertCommand) {
        log.info("Start productStockRevertCommand ............");
        try {
            ProductEntity productEntity = this.productEntityRepository.load(productStockRevertCommand.getProductId());
            productEntity.revertStock(productStockRevertCommand.getCount());
            log.debug("Stock of Product with id: {} reverted back by: {}",
                    productStockRevertCommand.getProductId(), productStockRevertCommand.getCount());
        } catch (Exception ex) {
            log.error("this Product with id {} Not Found ", productStockRevertCommand.getProductId());
        }
        log.info("End productStockRevertCommand .................");
    }
}
