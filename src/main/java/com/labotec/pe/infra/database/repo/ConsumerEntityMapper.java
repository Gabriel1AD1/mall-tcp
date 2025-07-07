package com.labotec.pe.infra.database.repo;

import com.labotec.pe.domain.entity.Consumer;
import com.labotec.pe.infra.database.entity.ConsumerEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConsumerEntityMapper {
    ConsumerEntity toEntity(Consumer consumer);

    Consumer toDto(ConsumerEntity consumerEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ConsumerEntity partialUpdate(Consumer consumer, @MappingTarget ConsumerEntity consumerEntity);
}