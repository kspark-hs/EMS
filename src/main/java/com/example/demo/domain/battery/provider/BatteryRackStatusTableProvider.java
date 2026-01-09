package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.view.BatteryRackStatusTableRowViewDto;
import java.util.List;

/**
 * Battery Rack 상태 테이블(Row) Provider
 *
 * 책임:
 * - Rack 상태 테이블에 필요한 Raw 결과 제공
 * - 해석 / 문자열 변환 ❌
 * - Service 계층에서 ViewDto 조립
 */
public interface BatteryRackStatusTableProvider {

    /**
     * Battery Rack 상태 Row 목록 조회
     *
     * @param batteryId 배터리 ID
     * @return Battery Rack 상태 Row View DTO 리스트
     */
    List<BatteryRackStatusTableRowViewDto> getRackStatusRows(Long batteryId);
}
