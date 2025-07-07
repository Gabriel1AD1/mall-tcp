package com.labotec.pe.app.service;

import lombok.AllArgsConstructor;
import com.labotec.pe.app.constants.util.ExtendedDataPacketStatus;
import com.labotec.pe.app.constants.util.ShortDataPacketStatus;
import com.labotec.pe.app.constants.util.TypeDataPacket;
import com.labotec.pe.app.port.input.annotation.AppService;
import com.labotec.pe.app.port.output.PositionService;
import com.labotec.pe.app.proto.WialonProtocolDecoder;
import com.labotec.pe.domain.model.DataPosition;
import com.labotec.pe.domain.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
@AppService
@AllArgsConstructor
public class IPositionService implements PositionService {
    private final Logger logger = LoggerFactory.getLogger(IPositionService.class);
    private final WialonProtocolDecoder wialonProtocolDecoder = new WialonProtocolDecoder();

    @Override
    public DataPosition getDataPacket(Object message,String imei) {
        TypeDataPacket typeMessageDecode = wialonProtocolDecoder.getDataTypeMessage(message);
        logger.info("Tipo de mensaje recibido : {}", typeMessageDecode);

        String dataMessage = wialonProtocolDecoder.getDataMessage(message);

        logger.debug("Datos del mensaje recibidos : {}", dataMessage);

        // Intentar decodificar la posición
        Position position = (Position) wialonProtocolDecoder.decode(Objects.requireNonNull(handlerDecode(typeMessageDecode)), dataMessage);
        position.setImei(imei);
        return DataPosition.builder()
                .messageDecode(getStatus(typeMessageDecode))
                .position(position)
                .build();
    }
    private String handlerDecode(TypeDataPacket typeDataPacket){
        switch (typeDataPacket){
            case MESSAGE -> {
                return "M";
            }
            case EXTENDED -> {
                return "D";
            }
            case SHORT -> {
                return "SD";
            }
            case UNKNOWN -> {
                return "UNKNOWN";
            }
            case LOGIN -> {
                return "L";
            }
            default -> {
                return null;
            }
        }
    }
    private String getStatus(TypeDataPacket typeDataPacket){
        switch (typeDataPacket){
            case MESSAGE -> {
                return "M";
            }
            case EXTENDED -> {
                return ExtendedDataPacketStatus.SUCCESS;
            }
            case SHORT -> {
                return ShortDataPacketStatus.SUCCESS;
            }
            default -> {
                return null;
            }
        }
    }
}
