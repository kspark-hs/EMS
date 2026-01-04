package com.example.demo.domain.pcs.provider;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * DestinPower PCS 요약 Provider
 *
 * - DestinPower PCS 메모리맵 기반 구현
 * - 현재는 더미 값
 * - 추후 RTU / DB 연동 시 이 파일만 수정
 */
@Primary
@Component
public class DestinPowerPcsSummaryProvider implements PcsSummaryProvider {

    @Override
    public Double getPvActivePowerKw() {
        // TODO: DestinPower PV 유효전력 레지스터 연동
        return 0.0;
    }

    @Override
    public Double getPcsActivePowerKw() {
        // TODO: DestinPower PCS 유효전력 레지스터 연동
        return 0.0;
    }

    @Override
    public Double getChargeDischargeReferenceKw() {
        // TODO: DestinPower 충/방전 Reference 레지스터 연동
        return 0.0;
    }

    @Override
    public String getPcsStatus() {
        // TODO: DestinPower PCS 상태 레지스터/비트 연동
        return "운전";
    }

    @Override
    public String getChargeDischargeStatus() {
        // TODO: DestinPower 충/방전 상태 레지스터/비트 연동
        return "충전";
    }
}
