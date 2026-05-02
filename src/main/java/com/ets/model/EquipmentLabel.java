package com.ets.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipment_label")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EquipmentLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String qrCodeData;
    private String stickerText;

    @OneToOne
    private Equipment equipment;

    private LocalDateTime generatedAt;
}