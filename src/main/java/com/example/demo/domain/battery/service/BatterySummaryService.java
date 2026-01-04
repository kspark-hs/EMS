package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.dto.BatterySummaryDto;
import com.example.demo.domain.battery.provider.BatterySummaryProvider;
import com.example.demo.domain.battery.view.BatterySummaryViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.domain.battery.status.ActionStatus;

@Service
@RequiredArgsConstructor
public class BatterySummaryService {

    private final BatterySummaryProvider batterySummaryProvider;

    public BatterySummaryViewDto getSummary(Long batteryId) {

        BatterySummaryDto raw = batterySummaryProvider.getSummary(batteryId);

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
