package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.provider.BatteryFaultStatusProvider;
import com.example.demo.domain.battery.view.BatteryFaultStatusViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatteryFaultStatusService {

    private final BatteryFaultStatusProvider provider;

    public BatteryFaultStatusViewDto getStatus(List<Long> rackIds) {

        boolean hasFault = false;
        boolean internalCommOk = true;
        boolean externalCommOk = true;
        boolean interlockOk = true;

        for (Long rackId : rackIds) {
            hasFault |= provider.hasFault(rackId);
            internalCommOk &= provider.isInternalCommOk(rackId);
            externalCommOk &= provider.isExternalCommOk(rackId);
            interlockOk &= provider.isInterlockOk(rackId);
        }

        BatteryFaultStatusViewDto dto = new BatteryFaultStatusViewDto();
        dto.setFaultOccurred(hasFault ? "있음" : "없음");
        dto.setInternalCommStatus(internalCommOk ? "정상" : "이상");
        dto.setExternalCommStatus(externalCommOk ? "정상" : "이상");
        dto.setInterlockStatus(interlockOk ? "정상" : "이상");

        return dto;
    }
}
