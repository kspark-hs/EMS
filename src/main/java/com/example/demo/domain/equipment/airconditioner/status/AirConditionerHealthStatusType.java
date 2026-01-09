package com.example.demo.domain.equipment.airconditioner.status;

/**
 * AirConditioner Health Status
 *
 * - UI 상태 색상
 * - Scheduler 판단
 * - Service 공용 기준
 */
public enum AirConditionerHealthStatusType {

    /** 정상 */
    NORMAL,

    /** 경고 (온습도 임계 근접 등) */
    WARNING,

    /** 장애 */
    FAULT,

    /** 통신 단절 */
    DISCONNECTED
}

