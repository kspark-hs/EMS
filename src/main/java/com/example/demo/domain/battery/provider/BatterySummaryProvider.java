package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.dto.BatterySummaryDto;

public interface BatterySummaryProvider {

    BatterySummaryDto getSummary(Long batteryId);
}

