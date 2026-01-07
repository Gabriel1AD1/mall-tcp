package com.labotec.pe.infra.controller;

import com.labotec.pe.domain.dto.RequestDeviceDTO;
import com.labotec.pe.domain.entity.Device;
import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.domain.model.DeviceResponse;
import com.labotec.pe.infra.server.ActiveChannelsRegistry;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.labotec.pe.app.port.input.DeviceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.labotec.pe.app.constants.api.ApiManager.v1;

@RestController
@RequestMapping(v1 + "devices")
@RequiredArgsConstructor
@Tag(name = "Device API", description = "Operaciones relacionadas con dispositivos")
public class DeviceController {

    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);
    private final DeviceService deviceService;
    @Value("${key.rest}")
    private String key;

    @PostMapping("{imei}/command")
    public ResponseEntity<Object> test(@PathVariable String imei,
                                     @RequestBody String message) {
        var response = deviceService.sendCommand(imei, message);
        return ResponseEntity.ok(Map.of("send", response));
    }
    @GetMapping("/all")
    public Object getAllDevices(@RequestHeader ("key") String authorization) {
        if (!key.equals(authorization)) {
            throw new RuntimeException("Unauthorized access");
        }
        return ActiveChannelsRegistry.getAllDevices();
    }

    @GetMapping
    Set<Device> getAllDevices(@RequestHeader ("key") String authorization ,@RequestHeader List<DeviceStatus> statuses) {
        log.info("Tratando de obtener todos los dispositivos");
        if (!key.equals(authorization)) {
            throw new RuntimeException("Unauthorized access");
        }
        return deviceService.getAllDevices(statuses);
    }
}
