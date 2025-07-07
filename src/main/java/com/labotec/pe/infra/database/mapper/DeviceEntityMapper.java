package com.labotec.pe.infra.database.mapper;

import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.infra.database.entity.DeviceEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeviceEntityMapper {
    DeviceEntity toEntity(Device deviceModel);

    Device toDto(DeviceEntity deviceEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DeviceEntity partialUpdate(Device deviceModel, @MappingTarget DeviceEntity deviceEntity);
}