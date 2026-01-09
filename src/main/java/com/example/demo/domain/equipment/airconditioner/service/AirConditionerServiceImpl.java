package com.example.demo.domain.equipment.airconditioner.service;

import com.example.demo.domain.equipment.airconditioner.provider.AirConditionerProvider;
import com.example.demo.domain.equipment.airconditioner.status.ControlMode;
import com.example.demo.domain.equipment.airconditioner.status.FanSpeed;
import com.example.demo.domain.equipment.airconditioner.status.OperationMode;
import com.example.demo.domain.equipment.airconditioner.view.AirConditionerSummaryViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 에어컨 서비스 구현체
 *
 * ✔ UI 조회용 로직
 * ✔ AUTO / MANUAL 분기
 * ✔ 실제 제어는 Provider로 위임 (아직 미구현)
 *
 * ❗ 온습도 연계 AUTO 제어는
 *    정책 확정 후 Scheduler 단계에서 구현
 */
@Service
@RequiredArgsConstructor
public class AirConditionerServiceImpl implements AirConditionerService {

    private final AirConditionerProvider provider;

    @Override
    public AirConditionerSummaryViewDto getView(String airConditionerId) {

        AirConditionerSummaryViewDto dto = new AirConditionerSummaryViewDto();

        dto.setAcId(airConditionerId);
        dto.setVendor("LG");

        dto.setCommunicationOk(true);
        dto.setPowerOn(true);

        dto.setControlMode(ControlMode.AUTO);
        dto.setOperationMode(OperationMode.COOL);
        dto.setSetTemp(25);
        dto.setFanSpeed(FanSpeed.AUTO);

        dto.setOperationStatusText("정상 운전 중");
        dto.setResponseState("능동대응");

        return dto;
    }

    @Override
    public List<AirConditionerSummaryViewDto> getAirConditionerList() {
        return List.of(
                getView("AC-01"),
                getView("AC-02")
        );
    }

    @Override
    public void autoControlIfNeeded(String airConditionerId) {

        AirConditionerSummaryViewDto ac = getView(airConditionerId);

        // MANUAL 모드면 자동 제어 금지
        if (ac.getControlMode() == ControlMode.MANUAL) return;

        // 통신 이상 시 제어 금지
        if (!ac.isCommunicationOk()) return;

        /*
         * TODO
         * - TemperatureHumidity StatusTable 기반 AUTO 제어
         * - Scheduler 연계
         * - 정책 문서 확정 후 구현
         */
    }
}
