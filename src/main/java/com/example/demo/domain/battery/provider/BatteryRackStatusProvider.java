package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.view.BatteryRackStatusTableRowViewDto;

import java.util.List;

/**
 * Battery Rack 상태 원천 Provider
 *
 * - Vendor 비의존
 * - Rack 상태의 "원천 데이터" 계약
 * - UI / Table / Service 개념 없음
 *
 * PCS의 PcsStatusProvider와 1:1 대응
 */
public interface BatteryRackStatusProvider {

    /**
     * 배터리 내 Rack 상태 목록 조회
     *
     * @param batteryId 배터리 ID
     * @return Rack 상태 Row 목록
     */
    List<BatteryRackStatusTableRowViewDto> getRackStatusRows(Long batteryId);
}

