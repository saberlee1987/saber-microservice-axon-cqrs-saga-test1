package com.saber.microservice.command.rest.repositories;

import com.saber.microservice.command.rest.models.OrderDtoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDtoRepository extends JpaRepository<OrderDtoEntity,Integer> {
}
