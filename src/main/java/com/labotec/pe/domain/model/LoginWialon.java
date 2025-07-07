package com.labotec.pe.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginWialon {
    private String imei;
    private String password;
}
