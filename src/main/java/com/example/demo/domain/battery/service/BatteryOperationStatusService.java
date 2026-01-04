package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.provider.BatteryOperationStatusProvider;
import com.example.demo.domain.battery.provider.BatteryRackStatusTableProvider;
import com.example.demo.domain.battery.status.ActionStatus;
import com.example.demo.domain.battery.status.BatteryOperationModeType;
import com.example.demo.domain.battery.status.BatteryOperationStatusType;
import com.example.demo.domain.battery.status.BatteryRackHealthStatusType;
import com.example.demo.domain.battery.view.BatteryOperationStatusViewDto;
import com.example.demo.domain.battery.view.BatteryRackStatusTableRowViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatteryOperationStatusService {

    private final BatteryRackStatusTableProvider batteryRackStatusProvider;
    private final BatteryOperationStatusProvider batteryOperationStatusProvider;

    public BatteryOperationStatusViewDto decide(Long batteryId) {

        // 1️⃣ Rack 상태 집계
        List<BatteryRackStatusTableRowViewDto> rows =
                batteryRackStatusProvider.getRackStatusRows(batteryId);

        long faultRackCount = rows.stream()
                .filter(row ->
                        row.getStatus() == BatteryRackHealthStatusType.FAULT
                                || row.getStatus() == BatteryRackHealthStatusType.DISCONNECTED)
                .count();

        // 2️⃣ Raw 상태 (현재는 사용 안 해도 됨 — 구조 확보용)
        batteryOperationStatusProvider.getOperationStatus(batteryId);

        // 3️⃣ View DTO 생성 (중요)
        BatteryOperationStatusViewDto dto =
                new BatteryOperationStatusViewDto();

        dto.setOperationMode(BatteryOperationModeType.AUTO);

        if (faultRackCount == 0) {
            dto.setActionStatus(ActionStatus.CHARGING);
            dto.setOperationStatus(BatteryOperationStatusType.NORMAL);
        }
        else if (faultRackCount <= 2) {
            dto.setActionStatus(ActionStatus.CHARGING);
            dto.setOperationStatus(BatteryOperationStatusType.PARTIAL);
            dto.setDescription("Rack 일부 탈락");
        }
        else {
            dto.setActionStatus(ActionStatus.STOPPED);
            dto.setOperationStatus(BatteryOperationStatusType.MAINTENANCE);
            dto.setDescription("Rack 다수 탈락");
        }

        return dto;
    }
}
