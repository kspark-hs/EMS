package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.status.BatteryAbnormalType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * Samsung 배터리 고장 상세 Provider (더미 구현)
 *
 * - Raw 데이터 제공 전용
 * - 판단 / 집계 / UI 해석 ❌
 * - BatteryAbnormalType 기준 Map 반환
 */
@Component
public class SamsungBatteryFaultDetailProvider
        implements BatteryFaultDetailProvider {

    @Override
    public Map<BatteryAbnormalType, Boolean> getFaultStatus(Long batteryId) {

        Map<BatteryAbnormalType, Boolean> map =
                new EnumMap<>(BatteryAbnormalType.class);

        // 🔸 기본값: 전부 false (항상 화면에 표시되게)
        for (BatteryAbnormalType type : BatteryAbnormalType.values()) {
            map.put(type, false);
        }

        // 🔴 더미 고장 예시 (통신 이상 하나만 ON)
        map.put(BatteryAbnormalType.RACK_SYSTEM_COMMUNICATION_FAIL, true);

        return map;
    }
}
