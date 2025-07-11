package com.labotec.pe.app.port.output;

import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.domain.enums.DeviceOperationStatus;
import com.labotec.pe.domain.enums.DeviceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DeviceRepository {
    Optional<Device>getDeviceByImei(String imi);

    Device save(Device device);

    void updateById(Long id, DeviceStatus deviceStatus);

    boolean existeDeviceByImei(@NotBlank(message = "El IMEI no puede estar vacío") @Size(min = 15, max = 15, message = "El IMEI debe tener exactamente 15 caracteres") @Pattern(regexp = "\\d+", message = "El IMEI debe contener solo números") String imei);

    Optional<Device> findById(Long id);

    void deleteById(Long id);

    Set<Device> findAllByStatusIn(List<DeviceStatus> status);
}
