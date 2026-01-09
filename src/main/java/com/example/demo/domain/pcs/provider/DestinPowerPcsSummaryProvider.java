package com.example.demo.domain.pcs.provider;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * DestinPower PCS 요약 Provider
 *
 * - 메모리맵 기반 Raw 데이터 제공
 * - 계산/판결 ❌
 * - 요약 카드 숫자 테스트 전용
 *
 * ⚠️ 테스트 종료 후
 *     → 메모리맵 / RTU / DB 값으로 이 파일만 교체
 */
@Component
@Primary
public class DestinPowerPcsSummaryProvider implements PcsSummaryProvider {

    @Override
    public String getPcsStatus(Long pcsId) {
        // 요약 카드에서는 Aggregate 결과를 사용하므로
        // 여기 값은 의미 없음 (구조 유지를 위해 정상 반환)
        return "정상";
    }

    @Override
    public String getChargeDischargeStatus(Long pcsId) {
        // SummaryService에서 Aggregate 기준으로 재판결됨
        return "대기";
    }

    @Override
    public boolean isOperable(Long pcsId) {
        // 운전 가능 여부도 Aggregate 기준 사용
        return true;
    }

    /**
     * PV 유효전력 (kW)
     */
    @Override
    public Double getPvActivePowerKw(Long pcsId) {
        // 🧪 TEST
        // PCS #1 : PV 발전 중
        // PCS #2 : 발전 없음
        return pcsId == 1L ? 120.0 : 0.0;
    }

    /**
     * 충/방전 Reference (kW)
     */
    @Override
    public Double getChargeDischargeReferenceKw(Long pcsId) {
        // 🧪 TEST
        // PCS #1 : 충전 지시
        // PCS #2 : 정지
        return pcsId == 1L ? 100.0 : 0.0;
    }

    /**
     * PCS 유효전력 (kW)
     */
    @Override
    public Double getPcsActivePowerKw(Long pcsId) {
        // 🧪 TEST
        // PCS #1 : 실제 충전 수행
        // PCS #2 : 출력 없음
        return pcsId == 1L ? 98.5 : 0.0;
    }
}
