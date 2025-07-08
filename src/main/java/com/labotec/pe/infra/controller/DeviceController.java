package com.labotec.pe.infra.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.labotec.pe.app.port.input.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.labotec.pe.app.constants.api.ApiManager.v1;

@RestController
@RequestMapping(v1 + "device")
@AllArgsConstructor
@Tag(name = "Device API", description = "Operaciones relacionadas con dispositivos")
public class DeviceController {

    private final DeviceService deviceService;


    @PostMapping("{imei}/command")
    public ResponseEntity<Object> test(@PathVariable String imei,
                                     @RequestBody String message) {
        var response = deviceService.sendCommand(imei, message);
        return ResponseEntity.ok(Map.of("send", response));
    }

}
