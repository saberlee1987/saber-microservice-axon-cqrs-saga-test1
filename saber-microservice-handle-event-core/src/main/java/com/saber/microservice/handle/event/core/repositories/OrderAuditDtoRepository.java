package com.saber.microservice.handle.event.core.repositories;


import com.saber.microservice.handle.event.core.models.OrderAuditDtoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAuditDtoRepository extends JpaRepository<OrderAuditDtoEntity,Integer> {

}
