package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.provider.BatteryFaultStatusProvider;
import com.example.demo.domain.battery.view.BatteryFaultStatusViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatteryFaultStatusService {

    private final BatteryFaultStatusProvider provider;

    public BatteryFaultStatusViewDto getStatus(Long batteryId) {

        boolean hasFault = provider.hasFault();
        boolean internalCommOk = provider.isInternalCommOk();
        boolean externalCommOk = provider.isExternalCommOk();
        boolean interlockOk = provider.isInterlockOk();

        BatteryFaultStatusViewDto dto = new BatteryFaultStatusViewDto();
        dto.setFaultOccurred(hasFault ? "있음" : "없음");
        dto.setInternalCommStatus(internalCommOk ? "정상" : "이상");
        dto.setExternalCommStatus(externalCommOk ? "정상" : "이상");
        dto.setInterlockStatus(interlockOk ? "정상" : "이상");

        return dto;
    }
}
