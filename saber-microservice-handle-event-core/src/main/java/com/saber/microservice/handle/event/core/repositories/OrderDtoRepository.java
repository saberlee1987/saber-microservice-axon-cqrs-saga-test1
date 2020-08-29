package com.saber.microservice.handle.event.core.repositories;


import com.saber.microservice.handle.event.core.models.OrderDtoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDtoRepository extends JpaRepository<OrderDtoEntity,Integer> {
}
