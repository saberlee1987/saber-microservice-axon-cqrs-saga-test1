package com.saber.microservice.command.rest.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ecom_order_audit")
@Data
@EqualsAndHashCode
@ToString
public class OrderAuditDtoEntity {
    @Id
    private Integer id;
    @Column(name = "status",nullable = false,length = 50)
    private String status;
    @Column(name = "date",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
