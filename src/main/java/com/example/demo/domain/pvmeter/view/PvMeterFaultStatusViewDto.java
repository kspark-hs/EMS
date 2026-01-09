package com.example.demo.domain.pvmeter.view;

import lombok.Getter;
import lombok.Setter;

/**
 * PV 전력량계 - 고장 상태 카드 View DTO
 *
 * 표시 항목:
 * - 고장 발생 여부
 * - 내부 통신 상태
 * - 외부 통신 상태
 * - 인터록 상태
 *
 * ※ Provider / Status 계층 미사용 (현재 단계 정책)
 */
@Getter
@Setter
public class PvMeterFaultStatusViewDto {

    /** 고장 발생 여부 (예: 없음 / 발생) */
    private String faultOccurred;

    /** 내부 통신 상태 (예: 정상 / 이상) */
    private String internalCommStatus;

    /** 외부 통신 상태 (예: 정상 / 이상) */
    private String externalCommStatus;

    /** 인터록 상태 (예: 정상 / 차단) */
    private String interlockStatus;
}

