package com.ets.controller;

import com.ets.dto.*;
import com.ets.model.Equipment;
import com.ets.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping("/replace")
    public ReplacementResponse replace(@RequestBody ReplacementRequest request) {
        return equipmentService.replaceEquipment(request);
    }
    @PostMapping("/create")
    public Equipment create(@RequestBody Equipment equipment) {
        return equipmentService.createEquipment(equipment);
    }
    @GetMapping("/replacements")
    public List<ReplacementResponse> getAll() {
        return equipmentService.getAllOperations();
    }
}