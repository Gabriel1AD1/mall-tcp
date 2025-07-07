package com.labotec.pe.domain.entity;

import com.labotec.pe.infra.database.entity.ConsumerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * DTO for {@link ConsumerEntity}
 */
@AllArgsConstructor
@Getter
@ToString
public class Consumer implements Serializable {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
}