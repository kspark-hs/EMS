package com.example.demo.domain.equipment.firesystem.status;

/**
 * FireSystem Health Status
 *
 * - PCS / Battery / AirConditioner 와 의미 통일
 * - UI, Service, Alarm, History 공통 사용
 * - 상태 판단 기준은 Provider 에서만 결정
 */
public enum FireSystemHealthStatusType {

    /**
     * 정상
     * - 화재 신호 없음
     * - 통신 정상
     */
    NORMAL,

    /**
     * 주의
     * - 센서 이상 감지
     * - 임계치 근접
     * - 예비 경보 상태
     */
    WARNING,

    /**
     * 고장 / 화재 발생
     * - 화재 신호 감지
     * - 장비 내부 Fault
     */
    FAULT,

    /**
     * 단절
     * - 통신 불가
     * - RTU 미응답
     */
    DISCONNECTED
}

