package com.example.demo.domain.pcs.view;

import lombok.Data;

/**
 * PCS Aggregate Status View DTO
 *
 * - 단일 PCS 기준 최종 상태 판결 DTO
 * - Fault / Comm / Interlock / Operation 상태를 한 번에 제공
 * - Service에서 모든 판단 종료 (UI는 표시만 담당)
 */
@Data
public class PcsAggregateStatusViewDto {

    private boolean hasFault;
    private boolean internalCommOk;
    private boolean externalCommOk;
    private boolean interlockActive;

    // 🔒 최상위 Gate
    private boolean canOperate;

    private boolean running;
    private boolean charging;
    private boolean discharging;

    private String operationState;
    private String operationMode;
    private String controlMode;
}
