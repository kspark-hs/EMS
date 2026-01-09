package com.example.demo.domain.pcs.provider;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * DestinPower PCS 운전 상태 Provider
 *
 * ✔ 벤더별 메모리맵 해석
 * ✔ 1차 상태 판단
 */
@Component
@Primary
public class DestinPowerPcsOperationStatusProvider
        implements PcsOperationStatusProvider {

    @Override
    public boolean isFaultExist(Long pcsId) {
        // TODO 보호정지 / FAULT / 인터록 판단
        return false;
    }

    @Override
    public boolean isOperationAvailable(Long pcsId) {
        // TODO 통신 / 차단 / 인터록 종합 판단
        return true;
    }

    @Override
    public String getOperationMode(Long pcsId) {
        // TODO AUTO / MANUAL / LOCAL 판정
        return "AUTO";
    }

    @Override
    public String getControlMode(Long pcsId) {
        // TODO REMOTE / LOCAL 판정
        return "REMOTE";
    }
}
