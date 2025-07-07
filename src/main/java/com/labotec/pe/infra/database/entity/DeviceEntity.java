package com.labotec.pe.infra.database.entity;

import com.labotec.pe.domain.enums.DeviceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity()
@Table(name = "tbl_device", schema = "tcp_server",indexes = {
        @Index(name = "idx_imei", columnList = "imei")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imei", unique = true, nullable = false, length = 50)
    private String imei;

    @Column(name = "contraseña", nullable = false, length = 255)
    private String password;
    @Column(name = "latitude", nullable = false)
    @Comment("Latitud del dispositivo")
    private Double latitude;

    @Column(name = "longitude",nullable = false)
    @Comment("Longitud del dispositivo")
    private Double longitude;
    @Column(name = "device_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Estado del dispositivo online - offline - unknown")
    private DeviceStatus status;
    @Column(name = "topic" ,nullable = false)
    private String topic;
}
