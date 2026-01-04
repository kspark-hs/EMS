package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.dto.BatteryFaultStatusRawDto;
import org.springframework.stereotype.Component;

@Component
public class SamsungBatteryFaultStatusProvider
        implements BatteryFaultStatusProvider {

    private BatteryFaultStatusRawDto loadRaw(Long batteryId) {
        BatteryFaultStatusRawDto raw = new BatteryFaultStatusRawDto();
        raw.setBatteryId(batteryId);
        raw.setRawFault(true);
        raw.setRawInternalCommStatus("OK");
        raw.setRawExternalCommStatus("OK");
        raw.setRawInterlockStatus("OK");
        return raw;
    }

    @Override
    public boolean hasFault() {
        return loadRaw(null).isRawFault();
    }

    @Override
    public boolean isInternalCommOk() {
        return "OK".equals(loadRaw(null).getRawInternalCommStatus());
    }

    @Override
    public boolean isExternalCommOk() {
        return "OK".equals(loadRaw(null).getRawExternalCommStatus());
    }

    @Override
    public boolean isInterlockOk() {
        return "OK".equals(loadRaw(null).getRawInterlockStatus());
    }
}
