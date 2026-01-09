package com.example.demo.domain.pcs.provider;

/**
 * PCS 요약 카드 Provider 계약
 *
 * - 실시간 상태 + 전력(kW) Raw 제공
 * - 판결/정책/조합 금지
 */
public interface PcsSummaryProvider {

    /** PCS 상태 텍스트 (정상 / 고장 / 정지 등) */
    String getPcsStatus(Long pcsId);

    /** 충·방전 상태 (충전 / 방전 / 대기) */
    String getChargeDischargeStatus(Long pcsId);

    /** 운전 가능 여부 */
    boolean isOperable(Long pcsId);

    /** PV 유효전력 (kW, Raw 값) */
    Double getPvActivePowerKw(Long pcsId);

    /** 충·방전 Reference (kW, Raw 값) */
    Double getChargeDischargeReferenceKw(Long pcsId);

    /** PCS 유효전력 (kW, Raw 값) */
    Double getPcsActivePowerKw(Long pcsId);
}
