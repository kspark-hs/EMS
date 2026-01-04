package com.example.demo.domain.equipment.airconditioner.view;

import com.example.demo.domain.equipment.airconditioner.status.ControlMode;
import com.example.demo.domain.equipment.airconditioner.status.FanSpeed;
import com.example.demo.domain.equipment.airconditioner.status.OperationMode;
import lombok.Data;

@Data
public class AirConditionerViewDto {

    /** 에어컨 ID (AC-01, AC-02 …) */
    private String acId;

    /** 제조사 (LG, AirMajor 등) */
    private String vendor;

    /** 통신 상태 */
    private boolean communicationOk;

    /** 전원 상태 */
    private boolean powerOn;

    /** 제어 방식 (AUTO / MANUAL) */
    private ControlMode controlMode;

    /** 운전 모드 (COOL / HEAT / FAN / DRY / STOP) */
    private OperationMode operationMode;

    /** 설정 온도 (℃) */
    private Integer setTemp;

    /** 풍량 (LOW / MEDIUM / HIGH / AUTO) */
    private FanSpeed fanSpeed;

    /** UI 표시용 상태 문구 */
    private String operationStatusText;

    /** EMS 대응 상태 (능동대응 / 수동유지 / 대기 등) */
    private String responseState;
}


