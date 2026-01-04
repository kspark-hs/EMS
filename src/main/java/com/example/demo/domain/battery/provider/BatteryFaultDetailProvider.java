package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.status.BatteryAbnormalType;
import java.util.Map;

/**
 * 배터리 고장 상세 Provider (Raw 전용)
 *
 * 책임:
 * - 배터리 고장 상태 원본 데이터 제공
 * - 판단 / 집계 / 문자열 변환 ❌
 *
 * 사용처:
 * - BatteryFaultDetailService
 *
 * ※ PCS PcsFaultDetailProvider와 1:1 동일 구조
 */
public interface BatteryFaultDetailProvider {

    /**
     * 배터리 고장 상태 조회 (Raw)
     *
     * @param batteryId 배터리 ID
     * @return BatteryAbnormalType 기준 고장 여부 Map
     */
    Map<BatteryAbnormalType, Boolean> getFaultStatus(Long batteryId);
}
