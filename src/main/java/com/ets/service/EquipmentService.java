package com.ets.service;

import com.ets.dto.*;
import com.ets.model.Equipment;

import java.util.List;

public interface EquipmentService {
    ReplacementResponse replaceEquipment(ReplacementRequest request);
    List<ReplacementResponse> getAllOperations();
    Equipment createEquipment(Equipment equipment);

}