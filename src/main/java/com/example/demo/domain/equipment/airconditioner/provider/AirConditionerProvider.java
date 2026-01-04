package com.example.demo.domain.equipment.airconditioner.provider;

import com.example.demo.domain.equipment.airconditioner.status.ControlMode;
import com.example.demo.domain.equipment.airconditioner.status.FanSpeed;
import com.example.demo.domain.equipment.airconditioner.status.OperationMode;

/**
 * 에어컨 제어 Provider
 *
 * - LG / AirMajor / 기타 제조사 공통 인터페이스
 * - 실제 Modbus / TCP / API 제어 담당
 *
 * ❗ 판단 로직 없음
 * ❗ UI 의미 없음
 */
public interface AirConditionerProvider {

    /* =========================
     * 통신 상태
     * ========================= */

    /**
     * 통신 정상 여부
     *
     * @return
     *  true  : 통신 정상
     *  false : 통신 불가
     */
    boolean isCommunicationOk(String airConditionerId);


    /* =========================
     * 상태 조회
     * ========================= */

    boolean isPowerOn(String airConditionerId);

    ControlMode getControlMode(String airConditionerId);

    OperationMode getOperationMode(String airConditionerId);

    Integer getSetTemperature(String airConditionerId);

    FanSpeed getFanSpeed(String airConditionerId);


    /* =========================
     * 제어 명령 (쓰기)
     * ========================= */

    /**
     * 전원 ON / OFF
     */
    void setPower(String airConditionerId, boolean on);

    /**
     * 제어 모드 설정 (AUTO / MANUAL)
     */
    void setControlMode(String airConditionerId, ControlMode mode);

    /**
     * 운전 모드 설정
     */
    void setOperationMode(String airConditionerId, OperationMode mode);

    /**
     * 설정 온도 변경
     */
    void setSetTemperature(String airConditionerId, int temperature);

    /**
     * 풍량 변경
     */
    void setFanSpeed(String airConditionerId, FanSpeed fanSpeed);
}

