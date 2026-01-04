package com.example.demo.domain.pcs.view;

import lombok.Data;

/**
 * PCS 요약 카드 전용 View DTO
 * - 화면 표시 목적
 * - 숫자는 이미 포맷된 문자열만 가진다
 */
@Data
public class PcsSummaryViewDto {

    /** PCS 상태 (운전 / 정지 / 고장 등) */
    private String pcsStatus;

    /** 충·방전 상태 (충전 / 방전 / 대기) */
    private String chargeDischargeStatus;

    /** PV 유효전력 (kW) */
    private String pvActivePowerKw;

    /** 충·방전 Reference (kW) */
    private String chargeDischargeReferenceKw;

    /** PCS 유효전력 (kW) */
    private String pcsActivePowerKw;
}
