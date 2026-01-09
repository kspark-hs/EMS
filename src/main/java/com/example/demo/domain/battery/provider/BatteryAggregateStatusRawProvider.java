package com.example.demo.domain.battery.provider;

/**
 * Battery Aggregate Status 판결 전용 Raw Provider
 *
 * - Aggregate 판단용 Raw 상태 제공
 * - boolean / flag 값만 제공
 * - 판단 / 집계 / 문자열 변환 ❌
 */
public interface BatteryAggregateStatusRawProvider {

    /* 운전 상태 */
    boolean isRunning(Long batteryId);
    boolean isCharging(Long batteryId);
    boolean isDischarging(Long batteryId);

    /* 통신 상태 (Battery ↔ Rack / RTU 기준) */
    boolean isInternalCommOk(Long batteryId);
    boolean isExternalCommOk(Long batteryId);
}
