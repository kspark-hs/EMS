package com.example.demo.domain.battery.status;

/**
 * 배터리 운전 모드
 * - "누가 / 어떤 방식으로 제어 중인가"의 기준
 */
public enum BatteryOperationModeType {

    AUTO("AUTO"),              // EMS 실시간 자동 제어
    SCHEDULE("SCHEDULE"),      // 스케줄 기반 제어
    MANUAL("MANUAL"),          // 관리자 수동 제어 (원격)
    LOCAL("LOCAL");            // 현장 수동 제어 (장비 직접)

    private final String label;

    BatteryOperationModeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

