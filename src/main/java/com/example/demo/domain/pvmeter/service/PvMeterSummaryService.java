package com.example.demo.domain.pvmeter.service;

import com.example.demo.domain.pvmeter.view.PvMeterSummaryViewDto;
import com.example.demo.model.format.ValueFormatter;
import org.springframework.stereotype.Service;

@Service
public class PvMeterSummaryService {

    public PvMeterSummaryViewDto getSummary() {

        // 1️⃣ Raw 값 (예시)
        String onlineStatus = "ONLINE";
        Double activePowerKw = 32.9234;
        Double powerFactor = 0.9966;
        Double frequencyHz = 60.03;

        // 2️⃣ 요약 DTO 생성
        PvMeterSummaryViewDto dto = new PvMeterSummaryViewDto();

        // 3️⃣ 요약 카드 기준 필드만 세팅
        dto.setOnlineStatus(onlineStatus);
        dto.setActivePowerKw(ValueFormatter.f2(activePowerKw));
        dto.setPowerFactor(ValueFormatter.f2(powerFactor));
        dto.setFrequencyHz(ValueFormatter.f2(frequencyHz));

        return dto;
    }
}

