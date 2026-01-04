package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.provider.BatteryRackStatusTableProvider;
import com.example.demo.domain.battery.view.BatteryRackStatusTableViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Battery Rack 상태 테이블 Service
 *
 * 책임:
 * 1. Rack 상태 Row 조회
 * 2. UI 표시용 Table ViewDto 조립
 *
 * ※ PCS StatusTableService와 1:1 구조
 * ※ 운전 상태 판단 / 정책 로직은 절대 포함하지 않는다
 */
@Service
@RequiredArgsConstructor
public class BatteryRackStatusTableService {

    private final BatteryRackStatusTableProvider rackStatusTableProvider;

    /**
     * Rack 상태 테이블 조회 (UI 전용)
     */
    public BatteryRackStatusTableViewDto getRackStatusTable(Long batteryId) {
        return new BatteryRackStatusTableViewDto(
                rackStatusTableProvider.getRackStatusRows(batteryId)
        );
    }
}
