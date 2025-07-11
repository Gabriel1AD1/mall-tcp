package com.labotec.pe.infra.init;

import com.labotec.pe.app.port.input.DeviceService;
import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.infra.database.repo.DeviceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Order()
public class InitServerCommand implements ApplicationRunner {
    private final DeviceEntityRepository deviceEntityRepository;

    @Override
    public void run(ApplicationArguments args) {
        deviceEntityRepository.updateStatus(DeviceStatus.offline);
    }
}
