package com.saber.microservice.command.rest.repositories;

import com.saber.microservice.command.rest.models.ProductDtoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDtoRepository extends JpaRepository<ProductDtoEntity,Integer> {
}
