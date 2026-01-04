package com.example.demo.domain.battery.status;

/**
 * 배터리 운전 상태
 * - "지금 운전 가능한가?" 판단의 1차 기준
 */
public enum BatteryOperationStatusType {

    NORMAL("정상 운전"),          // 제약 없음
    PARTIAL("파셜 운전"),          // 일부 Rack 제한
    LIMITED("운전 제한"),          // 조건부 허용 (온도, SOC 등)
    STOPPED("운전 정지"),          // 제어에 의한 정지
    MAINTENANCE("점검 중");        // 현장/관리자 점검

    private final String label;

    BatteryOperationStatusType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

