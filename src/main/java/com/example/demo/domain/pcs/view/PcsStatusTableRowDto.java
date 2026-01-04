package com.example.demo.domain.pcs.dto;

import lombok.Data;

/**
 * PCS 상태 테이블 Row Raw DTO
 *
 * - Provider → Service 전달용
 * - 숫자/원시값 유지
 * - 포맷/단위/문자열 변환 ❌
 */
@Data
public class PcsStatusTableRowDto {

    private Integer pcsNo;
    private String pcsName;        // PCS #1
    private String status;         // NORMAL / FAULT

    private Double dcVoltage;      // V
    private Double dcCurrent;      // A
    private Double dcPower;        // kW

    private Double acLineVoltage;  // V
    private Double acCurrent;      // A

    private Double activePower;    // kW
    private Double frequency;      // Hz
}

