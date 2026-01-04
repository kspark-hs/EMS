package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.dto.BatteryOperationStatusRawDto;
import org.springframework.stereotype.Component;

/**
 * Samsung 배터리 운전 상태 Provider
 *
 * - 장비 / DB 원본 값 제공 전용
 * - 판단 로직 / enum 변환 절대 금지
 * - 현재는 더미 구현
 */
@Component
public class SamsungBatteryOperationStatusProvider
        implements BatteryOperationStatusProvider {

    @Override
    public BatteryOperationStatusRawDto getOperationStatus(Long batteryId) {

        BatteryOperationStatusRawDto raw =
                new BatteryOperationStatusRawDto();

        // 🔸 더미 값 (향후 DB / 장비 연동 시 교체)
        raw.setBatteryId(batteryId);
        raw.setRawOperationStatus("CHARGING"); // 장비 원본
        raw.setRawOperationMode("AUTO");       // 장비 원본

        return raw;
    }
}

