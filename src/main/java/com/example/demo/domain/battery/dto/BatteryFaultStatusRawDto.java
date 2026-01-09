package com.example.demo.domain.battery.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 배터리 고장 상태 원본 DTO (Provider 전용)
 *
 * - 장비 / DB에서 읽은 원본 값만 담는다
 * - 판단 / 집계 / enum 변환 절대 금지
 * - Service에서 BatteryFaultStatusViewDto로 변환된다
 */
@Getter
@Setter
public class BatteryFaultStatusRawDto {

    /** 배터리 ID */
    private Long batteryId;

    /** 고장 발생 여부 (장비 원본) */
    private boolean rawFault;

    /** 내부 통신 상태 (장비 원본: OK / FAIL 등) */
    private String rawInternalCommStatus;

    /** 외부 통신 상태 (장비 원본) */
    private String rawExternalCommStatus;

    /** 인터록 상태 (장비 원본) */
    private String rawInterlockStatus;
}
