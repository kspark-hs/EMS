package com.example.demo.domain.pcs.view;

import lombok.Data;

/**
 * PCS 상태 테이블 1행 View DTO (최종 확정본)
 *
 * 원칙:
 * - 화면 기준 DTO
 * - Snapshot 결과만 표현
 * - 판단 로직 없음
 * - PCS 상태 카드 전용
 */
@Data
public class PcsStatusTableRowViewDto {

    /* =========================
     * 기본 정보
     * ========================= */
    private int pcsNo;
    private String pcsName;

    /* =========================
     * 상태 (한글 표시 기준)
     * ========================= */
    private String status;                 // 정상 / 고장 / 통신 이상
    private String internalCommStatus;     // 정상 / 두절
    private String externalCommStatus;     // 정상 / 두절

    /* =========================
     * DC 계통
     * ========================= */
    private Double dcVoltage;              // V
    private Double dcCurrent;              // A
    private Double dcPower;                // kW

    /* =========================
     * AC 계통
     * ========================= */
    private Double acLineVoltage;          // V
    private Double acCurrent;              // A
    private Double activePower;            // kW
    private Double frequency;              // Hz
}
