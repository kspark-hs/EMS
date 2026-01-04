package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.dto.BatterySummaryDto;
import org.springframework.stereotype.Component;

@Component
public class SamsungBatterySummaryProvider implements BatterySummaryProvider {

    @Override
    public BatterySummaryDto getSummary(Long batteryId) {

        BatterySummaryDto dto = new BatterySummaryDto();

        // 🔹 샘플 데이터 (화면 확인용)
        dto.setOnline(true);
        dto.setRelayOn(true);
        dto.setOperationStatus("Ready");
        dto.setSoc(67.9);
        dto.setDcVoltage(921.6);
        dto.setDcCurrent(0.0);

        return dto;
    }
}

