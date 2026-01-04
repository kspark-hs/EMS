package com.example.demo.domain.pcs.provider;

/**
 * PCS 요약 카드(PCS Summary) Raw 데이터 제공 Provider
 * - Service/DB/RTU 연동 전에는 더미 값을 제공
 * - 추후 연동 시 구현체만 교체
 */
public interface PcsSummaryProvider {

    /** PV 유효전력(kW) */
    Double getPvActivePowerKw();

    /** PCS 유효전력(kW) */
    Double getPcsActivePowerKw();

    /** 충/방전 Reference(kW) */
    Double getChargeDischargeReferenceKw();

    /** PCS 상태(운전/정지/고장 등) */
    String getPcsStatus();

    /** 충/방전 상태(충전/방전/대기) */
    String getChargeDischargeStatus();
}
