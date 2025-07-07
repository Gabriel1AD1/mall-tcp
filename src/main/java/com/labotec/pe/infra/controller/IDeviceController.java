package com.labotec.pe.infra.controller;

import com.labotec.pe.app.constants.util.StatusLogin;
import com.labotec.pe.infra.server.ActiveChannelsRegistry;
import com.labotec.pe.infra.server.MessageHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.labotec.pe.app.port.input.controller.DeviceController;
import com.labotec.pe.app.port.output.DeviceService;
import com.labotec.pe.domain.dto.CreateDeviceDTO;
import com.labotec.pe.domain.dto.UpdateDeviceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;

import static com.labotec.pe.app.constants.api.ApiManager.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "device")
@AllArgsConstructor
@Tag(name = "Device API", description = "Operaciones relacionadas con dispositivos")
public class IDeviceController implements DeviceController {

    private final DeviceService deviceService;


    @PostMapping("/test/{imei}")
    public ResponseEntity<Void> test(@PathVariable String imei,
                                     @RequestBody String message) {

       var chanel =  ActiveChannelsRegistry.getChannel(imei);
       if (chanel!=null){
           // Convierte el mensaje a ByteBuf
           ByteBuf responseBuf = MessageHandler.handleOutput(message+ StatusLogin.LINE_BREAK);

           // Envía el mensaje al cliente con un listener para manejar errores
           chanel.write(responseBuf).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
           chanel.flush();
       }
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping
    @Operation(summary = "Crear un dispositivo", description = "Crea un nuevo dispositivo en el sistema.")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateDeviceDTO deviceDTO) {
        return ResponseEntity.created(URI.create(API_VERSION_V1 + deviceService.create(deviceDTO).getId())).build();
    }

    @Override
    @PutMapping("/imei/{imei}")
    @Operation(summary = "Actualizar un dispositivo", description = "Actualiza un dispositivo existente por su IMEI.")
    public ResponseEntity<Void> update(@Valid @RequestBody UpdateDeviceDTO deviceDTO,
                                       @PathVariable("imei") String imei) {
        deviceService.update(deviceDTO, imei);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/imei/{imei}")
    @Operation(summary = "Eliminar un dispositivo", description = "Elimina un dispositivo existente por su IMEI.")
    public ResponseEntity<Void> delete(@PathVariable String imei) {
        deviceService.delete(imei);
        return ResponseEntity.ok().build();
    }
}
