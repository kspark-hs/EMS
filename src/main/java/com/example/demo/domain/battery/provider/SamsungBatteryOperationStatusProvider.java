package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.dto.BatteryOperationStatusRawDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Samsung 배터리 운전 상태 Provider
 *
 * - 장비 / DB 원본 값 제공 전용
 * - 판단 로직 / enum 변환 절대 금지
 * - rackIds 기준 Raw Provider (구조 고정)
 */
@Component
public class SamsungBatteryOperationStatusProvider
        implements BatteryOperationStatusProvider {

    @Override
    public BatteryOperationStatusRawDto getOperationStatus(List<Long> rackIds) {

        BatteryOperationStatusRawDto raw =
                new BatteryOperationStatusRawDto();

        // 🔸 더미 값 (향후 rackIds 기반 DB / 장비 연동)
        raw.setRawOperationStatus("CHARGING"); // 장비 원본
        raw.setRawOperationMode("AUTO");       // 장비 원본

        return raw;
    }
}
