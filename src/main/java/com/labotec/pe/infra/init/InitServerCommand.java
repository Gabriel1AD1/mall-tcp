package com.labotec.pe.infra.init;

import com.labotec.pe.domain.enums.DeviceStatus;
import com.labotec.pe.infra.database.repo.DeviceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitServerCommand implements CommandLineRunner {
    private final DeviceEntityRepository deviceEntityRepository;
    @Override
    public void run(String... args) {
        /*
        * Inicializa o status do dispositivo como offline
        * */
        deviceEntityRepository.updateStatus(DeviceStatus.offline);
    }
}
