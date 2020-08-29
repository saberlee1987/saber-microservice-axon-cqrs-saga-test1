package com.saber.microservice.handle.event.core.repositories;

import com.saber.microservice.handle.event.core.models.ProductDtoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDtoRepository extends JpaRepository<ProductDtoEntity,Integer> {
}
