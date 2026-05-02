package com.ets.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplacementResponse {

    private Long id;
    private String oldSerial;
    private String newSerial;
    private String reason;
    private String technician;
    private LocalDateTime date;
}