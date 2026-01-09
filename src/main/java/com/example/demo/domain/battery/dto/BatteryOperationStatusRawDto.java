package com.example.demo.domain.battery.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 배터리 운전 상태 원본 DTO (Provider 전용)
 *
 * - 장비 / DB에서 읽은 원본 값만 담는다
 * - 판단 / 정책 / enum 변환 금지
 * - Service에서 BatteryOperationStatusDto로 변환된다
 */
@Getter
@Setter
public class BatteryOperationStatusRawDto {

    /** 배터리 ID */
    private Long batteryId;

    /** 장비 원본 운전 상태 (예: CHARGING, DISCHARGING, READY) */
    private String rawOperationStatus;

    /** 장비 원본 운전 모드 (예: AUTO, MANUAL) */
    private String rawOperationMode;
}
