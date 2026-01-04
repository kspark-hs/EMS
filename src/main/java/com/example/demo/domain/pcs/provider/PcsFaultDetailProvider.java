package com.example.demo.domain.pcs.provider;

import com.example.demo.domain.pcs.status.PcsFaultType;
import java.util.Map;

/**
 * PCS Fault 상세 Provider
 *
 * 책임:
 * - PCS 개별 Fault Bit 제공
 * - Raw 상태만 반환
 * - 해석/표현은 Service 책임
 */
public interface PcsFaultDetailProvider {

    /**
     * PCS Fault 상세 상태 조회
     *
     * @param pcsId PCS 식별자
     * @return FaultType 별 발생 여부
     */
    Map<PcsFaultType, Boolean> getFaultStatus(Long pcsId);
}

