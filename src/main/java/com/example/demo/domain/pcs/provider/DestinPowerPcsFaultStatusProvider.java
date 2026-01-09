package com.example.demo.domain.pcs.provider;

import com.example.demo.domain.pcs.status.PcsFaultType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * DestinPower PCS 고장 상태 Provider
 *
 * - DestinPower PCS 메모리맵 기반
 * - Fault Detail Provider 결과를 집계하여 Raw 상태 제공
 * - 해석 / 판결 / 표현 ❌
 * - 추후 RTU / DB 연동 시 이 파일만 수정
 */
@Primary
@Component
@RequiredArgsConstructor
public class DestinPowerPcsFaultStatusProvider
        implements PcsFaultStatusProvider {

    private final PcsFaultDetailProvider faultDetailProvider;

    /**
     * PCS 내부 Fault 존재 여부 (INTERLOCK 제외)
     */
    @Override
    public boolean hasFault(Long pcsId) {

        Map<PcsFaultType, Boolean> faultMap =
                faultDetailProvider.getFaultStatus(pcsId);

        return faultMap.entrySet().stream()
                .filter(e -> e.getKey() != PcsFaultType.INTERLOCK)
                .anyMatch(e -> Boolean.TRUE.equals(e.getValue()));
    }

    /**
     * PCS 내부 통신 상태
     */
    @Override
    public boolean isInternalCommOk(Long pcsId) {
        // TODO: PCS ↔ 내부 모듈 통신 bit 연동
        return true;
    }

    /**
     * 외부 연계 통신 상태
     */
    @Override
    public boolean isExternalCommOk(Long pcsId) {
        // TODO: EMS / KESCO / 외부 API 연동
        return true;
    }

    /**
     * 인터록 활성 여부
     *
     * true  = 인터록 활성 (운전 차단)
     * false = 인터록 해제
     */
    @Override
    public boolean isInterlockActive(Long pcsId) {

        Map<PcsFaultType, Boolean> faultMap =
                faultDetailProvider.getFaultStatus(pcsId);

        return Boolean.TRUE.equals(faultMap.get(PcsFaultType.INTERLOCK));
    }
}
