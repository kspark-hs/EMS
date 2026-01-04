package com.example.demo.domain.battery.status;

/**
 * Rack 상태 (Health 기준)
 *
 * OPERATION_POLICY.md 기준:
 * - NORMAL, WARNING : 운전 가능
 * - FAULT, DISCONNECTED : 운전 불가
 */
public enum BatteryRackHealthStatusType {

    NORMAL,
    WARNING,
    FAULT,
    DISCONNECTED;

    /**
     * 운전 가능 Rack 여부
     */
    public boolean isOperable() {
        return this == NORMAL || this == WARNING;
    }
}
