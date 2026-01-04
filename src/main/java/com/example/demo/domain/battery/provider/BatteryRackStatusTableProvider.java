package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.view.BatteryRackStatusTableRowViewDto;
import java.util.List;

public interface BatteryRackStatusTableProvider {

    List<BatteryRackStatusTableRowViewDto> getRackStatusRows(Long batteryId);
}


