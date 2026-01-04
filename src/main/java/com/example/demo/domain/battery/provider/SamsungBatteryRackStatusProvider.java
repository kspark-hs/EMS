package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.status.BatteryRackHealthStatusType;
import com.example.demo.domain.battery.view.BatteryRackStatusTableRowViewDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Samsung SDI Battery Rack 상태 Provider
 *
 * - SDI 메모리맵 해석 책임
 * - Vendor 종속 로직은 여기서만 존재
 * - Table / UI 개념 없음
 */
@Component
@Primary
public class SamsungBatteryRackStatusProvider
        implements BatteryRackStatusProvider {

    @Override
    public List<BatteryRackStatusTableRowViewDto> getRackStatusRows(Long batteryId) {

        // TODO
        // 1. batteryId → 장비 / Rack 식별
        // 2. SDI 메모리맵 or RTU 데이터 조회
        // 3. Rack별 상태 매핑

        List<BatteryRackStatusTableRowViewDto> rows = new ArrayList<>();

        // ===== 더미 데이터 (현재 기준본) =====
        rows.add(createRow(
                "Rack #1",
                BatteryRackHealthStatusType.NORMAL,
                923.4,
                0.0,
                67.9,
                31.2,
                27.5
        ));

        rows.add(createRow(
                "Rack #2",
                BatteryRackHealthStatusType.WARNING,
                922.8,
                1.3,
                67.4,
                35.1,
                28.0
        ));

        rows.add(createRow(
                "Rack #3",
                BatteryRackHealthStatusType.FAULT,
                0.0,
                0.0,
                0.0,
                null,
                null
        ));
        // ==================================

        return rows;
    }

    private BatteryRackStatusTableRowViewDto createRow(
            String rackName,
            BatteryRackHealthStatusType status,
            Double rackVoltage,
            Double dcCurrent,
            Double soc,
            Double maxTemp,
            Double minTemp
    ) {
        BatteryRackStatusTableRowViewDto dto =
                new BatteryRackStatusTableRowViewDto();

        dto.setRackName(rackName);
        dto.setStatus(status);
        dto.setRackVoltage(rackVoltage);
        dto.setDcCurrent(dcCurrent);
        dto.setSoc(soc);
        dto.setMaxTemp(maxTemp);
        dto.setMinTemp(minTemp);

        return dto;
    }
}
