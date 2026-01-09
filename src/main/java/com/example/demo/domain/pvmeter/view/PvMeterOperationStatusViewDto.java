package com.example.demo.domain.pvmeter.view;

import lombok.Getter;
import lombok.Setter;

/**
 * PV 전력량계 - 운전 상태 카드 View DTO
 *
 * 표시 항목:
 * - 동작 상태
 * - 운전 모드
 * - 제어 모드
 *
 * ※ UI_CONTRACT 기준:
 * OperationStatusCard 표준 항목 중
 * PV 전력량계는 3개 항목만 사용한다.
 */
@Getter
@Setter
public class PvMeterOperationStatusViewDto {

    /** 동작 상태 (예: 운전 중, 정지) */
    private String actionStatus;

    /** 운전 모드 (예: AUTO, MANUAL) */
    private String operationMode;

    /** 제어 모드 (예: LOCAL, REMOTE) */
    private String controlMode;
}

