package com.example.demo.domain.pcs.view;

import lombok.Data;

/**
 * PCS 운전 상태 카드 전용 View DTO
 * - 화면 표시 전용
 * - 숫자는 이미 포맷된 문자열만 가진다
 */
@Data
public class PcsOperationStatusViewDto {

    /** 동작 상태 (운전 중 / 정지 / 보호정지 등) */
    private String operationState;

    /** 운전 모드 (AUTO / SCHEDULE / MANUAL) */
    private String operationMode;

    /** 제어 모드 (REMOTE / LOCAL) */
    private String controlMode;

    /** 금일 충전량 (kWh) */
    private String todayChargeEnergyKwh;

    /** 금일 방전량 (kWh) */
    private String todayDischargeEnergyKwh;
}
