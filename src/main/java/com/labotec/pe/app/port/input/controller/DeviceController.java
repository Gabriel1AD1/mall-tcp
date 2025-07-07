package com.labotec.pe.app.port.input.controller;

import com.labotec.pe.domain.dto.CreateDeviceDTO;
import com.labotec.pe.domain.dto.UpdateDeviceDTO;
import org.springframework.http.ResponseEntity;

public interface DeviceController {

    ResponseEntity<Void> create(CreateDeviceDTO deviceDTO);
    ResponseEntity<Void> update(UpdateDeviceDTO deviceDTO,String imei);
    ResponseEntity<Void> delete(String imei);
}
