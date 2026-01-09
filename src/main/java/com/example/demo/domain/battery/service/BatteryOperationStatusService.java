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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatteryOperationStatusService {

    private final BatteryRackStatusTableProvider batteryRackStatusTableProvider;
    private final BatteryOperationStatusProvider batteryOperationStatusProvider;

    /**
     * Battery 운전 상태 판결
     * - rackIds 기준
     * - Controller → Service 책임 분리 완료본
     */
    public BatteryOperationStatusViewDto decide(List<Long> rackIds) {

        /* =========================
         * 1️⃣ Rack 상태 전체 집계
         * ========================= */
        List<BatteryRackStatusTableRowViewDto> rows = new ArrayList<>();

        for (Long rackId : rackIds) {
            rows.addAll(
                    batteryRackStatusTableProvider.getRackStatusRows(rackId)
            );
        }

        long faultRackCount = rows.stream()
                .filter(row ->
                        row.getStatus() == BatteryRackHealthStatusType.FAULT
                                || row.getStatus() == BatteryRackHealthStatusType.DISCONNECTED
                )
                .count();

        /* =========================
         * 2️⃣ Raw Operation 상태 (구조 유지용)
         * ========================= */
        // 현재는 rack 기준 Raw 없음 → 호출만 유지
        // 추후 rackIds 기반 Raw Provider로 확장 가능
        batteryOperationStatusProvider.getOperationStatus(rackIds);

        /* =========================
         * 3️⃣ View DTO 판결
         * ========================= */
        BatteryOperationStatusViewDto dto = new BatteryOperationStatusViewDto();
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
