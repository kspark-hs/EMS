package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PlantInfoDto {
    private Long id;
    private String name;
    private String address;
    private LocalDate inspectionDate;   // 사용전 검사일
    private LocalDate meterSealDate;     // 계량기 봉인일
}

