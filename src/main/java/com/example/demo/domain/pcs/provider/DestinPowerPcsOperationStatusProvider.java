package com.example.demo.domain.pcs.provider;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * DestinPower PCS 운전 상태 Provider
 *
 * - DestinPower PCS 메모리맵 기반 Raw 상태 제공
 * - 해석 로직 없음 (Service에서 처리)
 * - 현재는 더미 값
 * - 추후 RTU / DB 연동 시 이 파일만 수정
 */
@Primary
@Component
public class DestinPowerPcsOperationStatusProvider
        implements PcsOperationStatusProvider {

    @Override
    public boolean isRunning() {
        // TODO: DestinPower PCS RUN 상태 비트 연동
        return true;
    }

    @Override
    public String getOperationMode() {
        // TODO: AUTO / MANUAL 상태 레지스터 연동
        return "AUTO";
    }

    @Override
    public String getControlMode() {
        // TODO: LOCAL / REMOTE 상태 레지스터 연동
        return "REMOTE";
    }

    @Override
    public boolean isCharging() {
        // TODO: 충전 상태 비트 연동
        return false;
    }

    @Override
    public boolean isDischarging() {
        // TODO: 방전 상태 비트 연동
        return false;
    }
}
