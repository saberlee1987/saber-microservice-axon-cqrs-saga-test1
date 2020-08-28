package com.saber.microservice.command.rest.repositories;

import com.saber.microservice.command.rest.models.OrderAuditDtoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAuditDtoRepository extends JpaRepository<OrderAuditDtoEntity,Integer> {

}
