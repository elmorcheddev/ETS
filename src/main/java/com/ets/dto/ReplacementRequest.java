package com.ets.dto;

import lombok.*;

@Getter @Setter
public class ReplacementRequest {

    private String oldSerialNumber;
    private String newSerialNumber;
    private String type;
    private String location;
    private String reason;
    private String technician;
}