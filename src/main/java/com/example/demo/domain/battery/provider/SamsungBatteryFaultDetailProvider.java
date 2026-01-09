package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.status.BatteryAbnormalType;
import com.example.demo.domain.battery.view.BatteryFaultDetailViewDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class SamsungBatteryFaultDetailProvider
        implements BatteryFaultDetailProvider {

    @Override
    public List<BatteryFaultDetailViewDto> getFaultDetails(Long rackId) {

        Map<BatteryAbnormalType, Boolean> raw =
                new EnumMap<>(BatteryAbnormalType.class);

        // 🔴 더미 RAW (장비 연동 전)
        raw.put(BatteryAbnormalType.CELL_OVER_VOLTAGE, false);
        raw.put(BatteryAbnormalType.CELL_UNDER_VOLTAGE, false);
        raw.put(BatteryAbnormalType.CELL_OVER_TEMPERATURE, true);
        raw.put(BatteryAbnormalType.RACK_SYSTEM_COMMUNICATION_FAIL, false);

        List<BatteryFaultDetailViewDto> result = new ArrayList<>();

        for (Map.Entry<BatteryAbnormalType, Boolean> entry : raw.entrySet()) {
            result.add(
                    new BatteryFaultDetailViewDto(
                            rackId.intValue(),           // rackNo
                            entry.getKey().getLabel(),   // faultName
                            entry.getValue(),            // occurred
                            null                         // groupKey (Service에서 판단)
                    )
            );
        }

        return result;
    }
}
