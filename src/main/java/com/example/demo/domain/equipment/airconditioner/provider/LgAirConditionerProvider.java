package com.example.demo.domain.equipment.airconditioner.provider;

import com.example.demo.domain.equipment.airconditioner.status.ControlMode;
import com.example.demo.domain.equipment.airconditioner.status.FanSpeed;
import com.example.demo.domain.equipment.airconditioner.status.OperationMode;
import org.springframework.stereotype.Component;

/**
 * LG 에어컨 Provider
 *
 * - LG 에어컨 Modbus RTU 기준
 * - EMS ↔ 에어컨 사이의 "하드웨어 경계"
 * - Service / UI는 이 구현을 전혀 모름
 */
@Component
public class LgAirConditionerProvider implements AirConditionerProvider {

    /* =====================================================
     * 1. 통신 / 상태 조회 영역
     * ===================================================== */

    /**
     * 통신 가능 여부
     *
     * - Modbus 응답 유무
     * - Timeout / CRC Error 시 false
     */
    @Override
    public boolean isCommunicationOk(String airConditionerId) {
        return true; // TODO 더미
    }

    /**
     * 전원 상태
     *
     * - ON / OFF
     * - LG 메모리맵: Power Status Register
     */
    @Override
    public boolean isPowerOn(String airConditionerId) {
        return true; // TODO 더미
    }

    /* =====================================================
     * 2. 운전 상태 조회
     * ===================================================== */

    /**
     * 운전 모드
     *
     * - COOL / HEAT / FAN / DRY / STOP
     */
    @Override
    public OperationMode getOperationMode(String airConditionerId) {
        return OperationMode.COOL; // TODO 더미
    }

    /**
     * 제어 방식
     *
     * - AUTO / MANUAL
     * - EMS 자동 제어 여부 판단 기준
     */
    @Override
    public ControlMode getControlMode(String airConditionerId) {
        return ControlMode.AUTO; // TODO 더미
    }

    /**
     * 설정 온도 (℃)
     */
    @Override
    public Integer getSetTemperature(String airConditionerId) {
        return 25; // TODO 더미
    }

    /**
     * 풍량
     *
     * - LOW / MEDIUM / HIGH / AUTO
     */
    @Override
    public FanSpeed getFanSpeed(String airConditionerId) {
        return FanSpeed.AUTO; // TODO 더미
    }

    /* =====================================================
     * 3. 제어 명령 영역 (AUTO / MANUAL 공통)
     * ===================================================== */

    /**
     * 전원 ON / OFF
     *
     * - MANUAL 모드에서만 실제 반영
     */
    @Override
    public void setPower(String airConditionerId, boolean on) {
        // TODO Modbus Write
    }

    /**
     * 제어 모드 설정 (AUTO / MANUAL)
     */
    @Override
    public void setControlMode(String airConditionerId, ControlMode mode) {
        // TODO Modbus Write
    }

    /**
     * 운전 모드 설정
     *
     * - MANUAL 모드에서만 실제 반영
     */
    @Override
    public void setOperationMode(String airConditionerId, OperationMode mode) {
        // TODO Modbus Write
    }

    /**
     * 설정 온도 변경
     */
    @Override
    public void setSetTemperature(String airConditionerId, int temperature) {
        // TODO Modbus Write
    }

    /**
     * 풍량 설정
     */
    @Override
    public void setFanSpeed(String airConditionerId, FanSpeed fanSpeed) {
        // TODO Modbus Write
    }
}
