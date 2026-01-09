package com.example.demo.domain.battery.status;

/**
 * Battery 충·방전 상태
 *
 * - UI 표현용 상태
 * - 값은 향후 메모리맵/설정 연동으로 교체 가능
 */
public enum BatteryChargeDischargeStatusType {
    CHARGING,
    DISCHARGING,
    IDLE,
    STOP
}
