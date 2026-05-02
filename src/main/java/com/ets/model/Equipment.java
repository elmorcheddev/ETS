package com.ets.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipment")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String serialNumber;

    private String type;
    private String location;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;

    private LocalDateTime createdAt;

    // 🔥 traceability link
    @ManyToOne
    private Equipment replacedBy;
}