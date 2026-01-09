package com.example.demo.domain.equipment.airconditioner.provider;

import com.example.demo.domain.equipment.airconditioner.dto.AirConditionerSummaryDto;
import com.example.demo.domain.equipment.airconditioner.status.AirConditionerHealthStatusType;
import com.example.demo.domain.equipment.airconditioner.status.ControlMode;
import com.example.demo.domain.equipment.airconditioner.status.FanSpeed;
import com.example.demo.domain.equipment.airconditioner.status.OperationMode;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * LG AirConditioner Status Table Provider
 *
 * - 화면 검증용 더미 데이터
 */
@Component
public class LgAirConditionerStatusTableProvider
        implements AirConditionerStatusTableProvider {

    @Override
    public List<AirConditionerSummaryDto> getStatusSummaries() {

        /* 에어컨 #1 : 수동 / 냉방 26℃ */
        AirConditionerSummaryDto ac1 = new AirConditionerSummaryDto();
        ac1.setOnline(true);
        ac1.setHealthStatus(AirConditionerHealthStatusType.NORMAL);
        ac1.setControlMode(ControlMode.MANUAL);
        ac1.setRunning(true);
        ac1.setOperationMode(OperationMode.COOL);
        ac1.setSetTemperature(26);
        ac1.setFanSpeed(FanSpeed.MEDIUM);

        /* 에어컨 #2 : 자동 / 난방 22℃ */
        AirConditionerSummaryDto ac2 = new AirConditionerSummaryDto();
        ac2.setOnline(true);
        ac2.setHealthStatus(AirConditionerHealthStatusType.NORMAL);
        ac2.setControlMode(ControlMode.AUTO);
        ac2.setRunning(true);
        ac2.setOperationMode(OperationMode.HEAT);
        ac2.setSetTemperature(22);
        ac2.setFanSpeed(FanSpeed.LOW);

        /* 에어컨 #3 : 통신 단절 */
        AirConditionerSummaryDto ac3 = new AirConditionerSummaryDto();
        ac3.setOnline(false);
        ac3.setHealthStatus(AirConditionerHealthStatusType.DISCONNECTED);
        ac3.setRunning(false);

        /* 에어컨 #4 : 통신 단절 */
        AirConditionerSummaryDto ac4 = new AirConditionerSummaryDto();
        ac4.setOnline(false);
        ac4.setHealthStatus(AirConditionerHealthStatusType.DISCONNECTED);
        ac4.setRunning(false);

        return List.of(ac1, ac2, ac3, ac4);
    }
}
