package com.labotec.pe.infra.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_consumidor", schema = "tcp_server",indexes = {
        @Index(name = "idx_nombre", columnList = "nombre"),
        @Index(name = "idx_email", columnList = "email")
})
public class ConsumerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "telefono", length = 20)
    private String phone;
}
