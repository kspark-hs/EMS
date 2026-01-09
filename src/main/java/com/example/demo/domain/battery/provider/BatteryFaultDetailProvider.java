package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.view.BatteryFaultDetailViewDto;
import java.util.List;

public interface BatteryFaultDetailProvider {

    /**
     * Rack 기준 고장 상세 (Raw)
     */
    List<BatteryFaultDetailViewDto> getFaultDetails(Long rackId);
}
