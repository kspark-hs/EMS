package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.dto.BatterySummaryDto;
import com.example.demo.domain.battery.provider.BatterySummaryProvider;
import com.example.demo.domain.battery.status.ActionStatus;
import com.example.demo.domain.battery.view.BatterySummaryViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatterySummaryService {

    private final BatterySummaryProvider batterySummaryProvider;

    public BatterySummaryViewDto getSummary(List<Long> rackIds) {

        // 현재 Summary Raw 는 rack 기준 미지원 → 대표 rack(첫 번째) 기준
        Long representativeRackId = rackIds.get(0);

        BatterySummaryDto raw =
                batterySummaryProvider.getSummary(representativeRackId);

        BatterySummaryViewDto view = new BatterySummaryViewDto();

        view.setOnlineStatus(raw.isOnline() ? "ONLINE" : "OFFLINE");
        view.setRelayStatus(raw.isRelayOn() ? "ON" : "OFF");
        view.setSoc(raw.getSoc());
        view.setActionStatus(convertActionStatus(raw.getOperationStatus()));
        view.setDcVoltage(raw.getDcVoltage());
        view.setDcCurrent(raw.getDcCurrent());

        return view;
    }

    private ActionStatus convertActionStatus(String rawStatus) {

        if (rawStatus == null) {
            return ActionStatus.READY;
        }

        return switch (rawStatus) {
            case "충전 중" -> ActionStatus.CHARGING;
            case "방전 중" -> ActionStatus.DISCHARGING;
            case "Ready", "대기" -> ActionStatus.READY;
            case "정지" -> ActionStatus.STOPPED;
            case "점검 중" -> ActionStatus.MAINTENANCE;
            default -> ActionStatus.READY;
        };
    }
}
