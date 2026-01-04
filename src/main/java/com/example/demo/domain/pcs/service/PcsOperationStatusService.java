package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.provider.PcsOperationStatusProvider;
import com.example.demo.domain.pcs.view.PcsOperationStatusViewDto;
import com.example.demo.model.format.ValueFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PcsOperationStatusService {

    private final PcsOperationStatusProvider provider;

    public PcsOperationStatusViewDto getStatus() {

        // 1️⃣ Raw 상태 수집 (Provider)
        boolean running = provider.isRunning();
        boolean charging = provider.isCharging();
        boolean discharging = provider.isDischarging();

        String operationMode = provider.getOperationMode(); // AUTO / MANUAL
        String controlMode = provider.getControlMode();     // LOCAL / REMOTE

        // 2️⃣ 상태 해석 (정책은 Service 책임)
        String operationState;
        if (!running) {
            operationState = "정지";
        } else if (charging) {
            operationState = "충전 중";
        } else if (discharging) {
            operationState = "방전 중";
        } else {
            operationState = "대기 중";
        }

        // 3️⃣ 일간 에너지 (임시 더미 → 추후 Energy Provider로 분리 가능)
        Double todayCharge = 0.0;
        Double todayDischarge = 0.0;

        // 4️⃣ View DTO 조립
        PcsOperationStatusViewDto dto = new PcsOperationStatusViewDto();
        dto.setOperationState(operationState);
        dto.setOperationMode(operationMode);
        dto.setControlMode(controlMode);

        // 5️⃣ 숫자 포맷은 Service에서 단 한 번
        dto.setTodayChargeEnergyKwh(ValueFormatter.f2(todayCharge));
        dto.setTodayDischargeEnergyKwh(ValueFormatter.f2(todayDischarge));

        return dto;
    }
}
