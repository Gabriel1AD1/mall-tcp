package com.labotec.pe.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataPosition {
    private String messageDecode;
    private Position position;
}
