package com.labotec.pe.infra.config;

import lombok.Data;
import com.labotec.pe.app.constants.server.ProfileServerTcp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ConfigConstant {
    @Value("${server.port.tcp}")
    private int tcpServerPort;
    @Value("${server.profile.tcp}")
    private ProfileServerTcp profileServerTcp;
    @Value("${devices.time.minus.status.unknown}")
    private Long minusSecond;
}
