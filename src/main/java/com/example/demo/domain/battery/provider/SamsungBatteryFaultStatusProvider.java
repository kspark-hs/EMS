package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.dto.BatteryFaultStatusRawDto;
import org.springframework.stereotype.Component;

@Component
public class SamsungBatteryFaultStatusProvider
        implements BatteryFaultStatusProvider {

    /**
     * rackId 기준 Raw 데이터 로딩
     * (현재는 샘플, 추후 rackId → 메모리맵 분기)
     */
    private BatteryFaultStatusRawDto loadRaw(Long rackId) {
        BatteryFaultStatusRawDto raw = new BatteryFaultStatusRawDto();
        raw.setRawFault(true);
        raw.setRawInternalCommStatus("OK");
        raw.setRawExternalCommStatus("OK");
        raw.setRawInterlockStatus("OK");
        return raw;
    }

    @Override
    public boolean hasFault(Long rackId) {
        return loadRaw(rackId).isRawFault();
    }

    @Override
    public boolean isInternalCommOk(Long rackId) {
        return "OK".equals(loadRaw(rackId).getRawInternalCommStatus());
    }

    @Override
    public boolean isExternalCommOk(Long rackId) {
        return "OK".equals(loadRaw(rackId).getRawExternalCommStatus());
    }

    @Override
    public boolean isInterlockOk(Long rackId) {
        return "OK".equals(loadRaw(rackId).getRawInterlockStatus());
    }
}
