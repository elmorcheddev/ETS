package com.ets.service.impl;

import com.ets.dto.*;
import com.ets.model.*;
import com.ets.repository.*;
import com.ets.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final ReplacementRepository replacementRepository;
    private final EquipmentLabelRepository labelRepository;

    @Override
    @Transactional
    public ReplacementResponse replaceEquipment(ReplacementRequest request) {

        // 1. OLD equipment
        Equipment oldEq = equipmentRepository.findBySerialNumber(request.getOldSerialNumber())
                .orElseThrow(() -> new RuntimeException("Old equipment not found"));

        // 2. NEW equipment (create or reuse)
        Equipment newEq = equipmentRepository.findBySerialNumber(request.getNewSerialNumber())
                .orElseGet(() -> Equipment.builder()
                        .serialNumber(request.getNewSerialNumber())
                        .type(request.getType())
                        .location(request.getLocation())
                        .status(EquipmentStatus.DRAFT)
                        .createdAt(LocalDateTime.now())
                        .build());

        equipmentRepository.save(newEq);

        // 3. STATUS UPDATE (swap)
        oldEq.setStatus(EquipmentStatus.REMOVED);
        newEq.setStatus(EquipmentStatus.INSTALLED);

        oldEq.setReplacedBy(newEq);

        equipmentRepository.save(oldEq);
        equipmentRepository.save(newEq);

        // 4. Replacement history
        Replacement replacement = Replacement.builder()
                .oldEquipment(oldEq)
                .newEquipment(newEq)
                .reason(request.getReason())
                .technician(request.getTechnician())
                .date(LocalDateTime.now())
                .build();

        replacementRepository.save(replacement);

        // 5. QR + STICKER GENERATION
        generateLabel(oldEq, newEq);

        return new ReplacementResponse(
                replacement.getId(),
                oldEq.getSerialNumber(),
                newEq.getSerialNumber(),
                replacement.getReason(),
                replacement.getTechnician(),
                replacement.getDate()
        );
    }

    // 🔥 QR GENERATION
    private void generateLabel(Equipment oldEq, Equipment newEq) {

        String qr = "OLD:" + oldEq.getSerialNumber()
                + "|NEW:" + newEq.getSerialNumber()
                + "|TYPE:" + newEq.getType()
                + "|LOC:" + newEq.getLocation();

        EquipmentLabel label = EquipmentLabel.builder()
                .equipment(oldEq)
                .qrCodeData(qr)
                .stickerText("REPLACED BY " + newEq.getSerialNumber())
                .generatedAt(LocalDateTime.now())
                .build();

        labelRepository.save(label);
    }

    @Override
    public List<ReplacementResponse> getAllOperations() {
        return replacementRepository.findAll()
                .stream()
                .map(rep -> new ReplacementResponse(
                        rep.getId(),
                        rep.getOldEquipment().getSerialNumber(),
                        rep.getNewEquipment().getSerialNumber(),
                        rep.getReason(),
                        rep.getTechnician(),
                        rep.getDate()
                ))
                .toList();
    }

    @Override
    public Equipment createEquipment(Equipment request) {

        Equipment equipment = Equipment.builder()
                .serialNumber(request.getSerialNumber())
                .type(request.getType())
                .location(request.getLocation())
                .status(EquipmentStatus.INSTALLED)
                .createdAt(LocalDateTime.now())
                .build();

        return equipmentRepository.save(equipment);
    }
}