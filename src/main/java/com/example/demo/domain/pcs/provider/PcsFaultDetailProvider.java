package com.example.demo.domain.pcs.provider;

import com.example.demo.domain.pcs.status.PcsFaultType;

import java.util.Map;

/**
 * PCS Fault Detail Provider
 *
 * - PCS 단위 고장 RAW Map 제공
 * - 판단 / 집계 / UI 로직 금지
 */
public interface PcsFaultDetailProvider {

    /**
     * PCS 개별 Fault 상태 Map
     *
     * @param pcsNo PCS 번호
     * @return FaultType -> 발생 여부
     */
    Map<PcsFaultType, Boolean> getFaultStatus(Long pcsNo);
}

