package com.example.demo.domain.battery.status;

/**
 * Battery 전체 상태 (집계 결과)
 *
 * - Rack 상태들을 집계한 "표현/판결용" 상태
 * - UI / Service 공통 기준
 */
public enum BatteryOverallStatusType {

    /** 전체 정상 */
    NORMAL("정상"),

    /** 일부 Rack 고장 (Partial Operation 가능) */
    PARTIAL_FAULT("부분 고장"),

    /** 전체 Rack 고장 */
    FAULT("고장"),

    /** 통신 장애 */
    COMM_ERROR("통신 이상"),

    /** 운전 정지 상태 (제어/운영 결과) */
    STOPPED("정지");

    private final String label;

    BatteryOverallStatusType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
