package com.example.demo.domain.pcs.view;

import lombok.Data;

/**
 * PCS 상태 테이블 1행 View DTO
 * - 화면 표시 전용
 * - 숫자는 모두 포맷된 문자열
 */
@Data
public class PcsStatusTableRowViewDto {

    private Integer pcsNo;
    private String pcsName;        // PCS #1
    private String status;         // NORMAL / FAULT

    private String dcVoltage;      // V
    private String dcCurrent;      // A
    private String dcPower;        // kW

    private String acLineVoltage;  // V
    private String acCurrent;      // A

    private String activePower;    // kW
    private String frequency;      // Hz
}

