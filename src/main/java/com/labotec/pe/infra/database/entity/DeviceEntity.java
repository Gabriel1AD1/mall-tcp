package com.labotec.pe.infra.database.entity;

import com.labotec.pe.domain.enums.DeviceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "devices",indexes = {
        @Index(name = "idx_imei", columnList = "imei")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceEntity {

    @Id
    private Long id;

    @Column(name = "imei", unique = true, nullable = false, length = 50)
    private String imei;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "device_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Estado del dispositivo online - offline - unknown")
    private DeviceStatus status;

}
