package com.example.demo.domain.pcs.provider;

import com.example.demo.domain.pcs.status.PcsFaultType;

import java.util.Map;

/**
 * PCS Fault Raw Provider (통합)
 *
 * - 모든 PCS 고장 비트를 Raw Map 형태로 제공
 * - 판단 / 해석 / UI 로직 절대 금지
 */
public interface PcsFaultProvider {

    Map<PcsFaultType, Boolean> getRawFaultMap(Long pcsId);
}

