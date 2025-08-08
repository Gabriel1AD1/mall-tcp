package com.labotec.pe.infra.init;

import com.labotec.pe.app.port.input.DeviceService;
import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.infra.database.repo.DeviceEntityRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Order()
public class InitServerCommand implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(InitServerCommand.class);
    private final DeviceEntityRepository deviceEntityRepository;
    @Value("${server.port.tcp}")
    private Long tcpServerPort;
    @Override
    public void run(ApplicationArguments args) {
        log.info("Inicializando servidor TCP en puerto {}", tcpServerPort);
        deviceEntityRepository.updateStatus(DeviceStatus.offline);
    }
}
