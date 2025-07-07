package com.labotec.pe.app.factory;

import com.labotec.pe.app.constants.service.DataPacketError;
import com.labotec.pe.app.constants.util.ExtendedDataPacketStatus;
import com.labotec.pe.app.constants.util.ShortDataPacketStatus;
import com.labotec.pe.app.constants.util.TypeDataPacket;

import java.util.Map;
import java.util.Optional;

public class ErrorDataPacketFactory {

    private static final Map<DataPacketError, String> SHORT_ERROR_MAP = Map.of(
            DataPacketError.STRUCTURE_ERROR, ShortDataPacketStatus.STRUCTURE_ERROR,
            DataPacketError.INCORRECT_TIME, ShortDataPacketStatus.INCORRECT_TIME,
            DataPacketError.ERROR_COORDINATES, ShortDataPacketStatus.ERROR_COORDINATES,
            DataPacketError.ERROR_SPEED_COURSE_ALT, ShortDataPacketStatus.ERROR_SPEED_COURSE_ALT,
            DataPacketError.ERROR_SATS_HDOP, ShortDataPacketStatus.ERROR_SATS,
            DataPacketError.CHECKSUM_ERROR, ShortDataPacketStatus.CHECKSUM_ERROR
    );

    private static final Map<DataPacketError, String> EXTENDED_ERROR_MAP = Map.of(
            DataPacketError.STRUCTURE_ERROR, ExtendedDataPacketStatus.STRUCTURE_ERROR,
            DataPacketError.INCORRECT_TIME, ExtendedDataPacketStatus.INCORRECT_TIME,
            DataPacketError.ERROR_COORDINATES, ExtendedDataPacketStatus.ERROR_COORDINATES,
            DataPacketError.ERROR_SPEED_COURSE_ALT, ExtendedDataPacketStatus.ERROR_SPEED_COURSE_ALT,
            DataPacketError.ERROR_SATS_HDOP, ExtendedDataPacketStatus.ERROR_SATS_HDOP,
            DataPacketError.ERROR_INPUTS_OUTPUTS, ExtendedDataPacketStatus.ERROR_INPUTS_OUTPUTS,
            DataPacketError.ERROR_ADC, ExtendedDataPacketStatus.ERROR_ADC,
            DataPacketError.ERROR_PARAMS, ExtendedDataPacketStatus.ERROR_PARAMS,
            DataPacketError.CHECKSUM_ERROR, ExtendedDataPacketStatus.CHECKSUM_ERROR
    );

    public static Optional<String> getErrorForDataPacket(TypeDataPacket typeDataPacket, DataPacketError dataPacketError) {
        return switch (typeDataPacket) {
            case SHORT -> Optional.ofNullable(SHORT_ERROR_MAP.get(dataPacketError));
            case EXTENDED -> Optional.ofNullable(EXTENDED_ERROR_MAP.get(dataPacketError));
            default -> Optional.empty();
        };
    }
}
