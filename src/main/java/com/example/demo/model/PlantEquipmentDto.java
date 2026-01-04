package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlantEquipmentDto {
    private String equipmentName;
    private Double installCapacityKw;
    private Double operateCapacityKw;
    private Integer quantity;
    private String brand;
}

