package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.status.BatteryRackHealthStatusType;
import com.example.demo.domain.battery.view.BatteryRackStatusTableRowViewDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SamsungBatteryRackStatusTableProvider
        implements BatteryRackStatusTableProvider {

    @Override
    public List<BatteryRackStatusTableRowViewDto> getRackStatusRows(Long batteryId) {

        return List.of(
                new BatteryRackStatusTableRowViewDto(
                        1, "RACK #1-1", BatteryRackHealthStatusType.NORMAL,
                        810.50, 4.20, 90.0, 22.4, 18.1
                ),
                new BatteryRackStatusTableRowViewDto(
                        2, "RACK #2-1", BatteryRackHealthStatusType.FAULT,
                        null, null, null, null, null
                ),
                new BatteryRackStatusTableRowViewDto(
                        3, "RACK #3-1", BatteryRackHealthStatusType.NORMAL,
                        809.80, 3.90, 88.5, 21.9, 17.8
        )
        );
    }
}
