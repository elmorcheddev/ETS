package com.ets.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "replacement")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Replacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Equipment oldEquipment;

    @ManyToOne
    private Equipment newEquipment;

    private String reason;
    private String technician;

    private LocalDateTime date;
}