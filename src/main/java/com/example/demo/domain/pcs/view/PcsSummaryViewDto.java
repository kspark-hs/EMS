package com.example.demo.domain.pcs.view;

import lombok.Data;

/**
 * PCS 요약 카드 전용 View DTO
 *
 * - PCS 상태 + 운전 가능 여부 + 실시간 전력 요약
 * - 화면 표시 전용
 * - 숫자는 이미 포맷된 문자열만 가진다
 */
@Data
public class PcsSummaryViewDto {

    /** PCS 상태 (정상 / 고장 / 정지 등) */
    private String pcsStatus;

    /** 충·방전 상태 (충전 / 방전 / 대기) */
    private String chargeDischargeStatus;

    /** 운전 가능 여부 (true = 운전 가능, false = 차단) */
    private boolean operable;

    /** PV 유효전력 (kW) */
    private String pvActivePowerKw;

    /** 충·방전 Reference (kW) */
    private String chargeDischargeReferenceKw;

    /** PCS 유효전력 (kW) */
    private String pcsActivePowerKw;
}
