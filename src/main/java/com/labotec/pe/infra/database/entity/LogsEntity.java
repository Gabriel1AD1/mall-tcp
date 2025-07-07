package com.labotec.pe.infra.database.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity()
@Table(name = "tbl_ultimos_datos", schema = "tcp_server",indexes = {
        @Index(name = "idx_imei", columnList = "imei"),
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogsEntity {
    @Id
    @Column(name = "imei")
    private String imei;

    @Column(name = "posicion",columnDefinition = "TEXT")
    private String position;

    @Column(name = "ultima_actualizacion")
    private Instant lastUpdate;
}
