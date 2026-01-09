package com.example.demo.domain.equipment.airconditioner.dto;

import com.example.demo.domain.equipment.airconditioner.status.ControlMode;
import com.example.demo.domain.equipment.airconditioner.status.OperationMode;
import com.example.demo.domain.equipment.airconditioner.status.FanSpeed;
import com.example.demo.domain.equipment.airconditioner.status.AirConditionerHealthStatusType;

import lombok.Getter;
import lombok.Setter;

/**
 * AirConditioner Summary DTO
 *
 * - Service ↔ Scheduler ↔ View 공용 DTO
 * - UI 표현용 문자열 금지
 * - 제어 판단에 필요한 최소 상태만 포함
 */
@Getter
@Setter
public class AirConditionerSummaryDto {

    /** 통신 정상 여부 */
    private boolean online;

    /** 종합 상태 (NORMAL / WARNING / FAULT / DISCONNECTED) */
    private AirConditionerHealthStatusType healthStatus;

    /** 제어 모드 (AUTO / MANUAL) */
    private ControlMode controlMode;

    /** 운전 모드 (COOL / HEAT / FAN / DRY / OFF 등) */
    private OperationMode operationMode;

    /** 설정 온도 (℃) */
    private Integer setTemperature;

    /** 풍량 */
    private FanSpeed fanSpeed;

    /** 현재 운전 중 여부 */
    private boolean running;
}

