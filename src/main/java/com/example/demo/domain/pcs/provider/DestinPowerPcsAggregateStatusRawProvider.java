package com.example.demo.domain.pcs.provider;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * DestinPower PCS Aggregate Status Raw Provider
 *
 * - 메모리맵 기반 Raw 상태 제공
 * - 판결/조합 로직 없음
 */
@Component
@Primary
public class DestinPowerPcsAggregateStatusRawProvider
        implements PcsAggregateStatusRawProvider {

    @Override
    public boolean isRunning(Long pcsId) {
        // TODO 메모리맵: 운전 상태
        return false;
    }

    @Override
    public boolean isCharging(Long pcsId) {
        // TODO 메모리맵: 충전 상태
        return false;
    }

    @Override
    public boolean isDischarging(Long pcsId) {
        // TODO 메모리맵: 방전 상태
        return false;
    }

    @Override
    public boolean isInternalCommOk(Long pcsId) {
        // TODO 내부 통신 상태
        return true;
    }

    @Override
    public boolean isExternalCommOk(Long pcsId) {
        // TODO 외부 통신 상태
        return true;
    }
}

