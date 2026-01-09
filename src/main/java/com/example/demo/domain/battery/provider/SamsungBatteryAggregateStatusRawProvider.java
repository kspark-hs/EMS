package com.example.demo.domain.battery.provider;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Samsung Battery Aggregate Status Raw Provider
 *
 * - Battery 전체 기준 Raw 상태 제공
 * - boolean flag만 제공
 * - 판단 / 집계 / 의미 해석 ❌
 */
@Component
@Primary
public class SamsungBatteryAggregateStatusRawProvider
        implements BatteryAggregateStatusRawProvider {

    /* =========================
     * 운전 상태 (Raw flag)
     * ========================= */

    @Override
    public boolean isRunning(Long batteryId) {
        // Battery 운전 여부 Raw 기준 없음 (현재 고정)
        return true;
    }

    @Override
    public boolean isCharging(Long batteryId) {
        // Raw 기준 없음 → 추후 BMS 연동 시 구현
        return false;
    }

    @Override
    public boolean isDischarging(Long batteryId) {
        // Raw 기준 없음 → 추후 BMS 연동 시 구현
        return false;
    }

    /* =========================
     * 통신 상태 (Raw)
     * ========================= */

    @Override
    public boolean isInternalCommOk(Long batteryId) {
        // Rack 내부 통신은 AggregateService에서 판단
        return true;
    }

    @Override
    public boolean isExternalCommOk(Long batteryId) {
        // RTU ↔ EMS 통신 상태
        return true;
    }
}
