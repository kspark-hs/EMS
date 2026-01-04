package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.provider.PcsSummaryProvider;
import com.example.demo.domain.pcs.view.PcsSummaryViewDto;
import com.example.demo.model.format.ValueFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PcsSummaryService {

    private final PcsSummaryProvider pcsSummaryProvider;

    public PcsSummaryViewDto getSummary() {

        // 1️⃣ Raw 값 (Provider에서만 가져옴)
        Double pvActivePowerKw = pcsSummaryProvider.getPvActivePowerKw();
        Double referenceKw     = pcsSummaryProvider.getChargeDischargeReferenceKw();
        Double pcsActivePowerKw = pcsSummaryProvider.getPcsActivePowerKw();

        String pcsStatus = pcsSummaryProvider.getPcsStatus();
        String chargeDischargeStatus = pcsSummaryProvider.getChargeDischargeStatus();

        // 2️⃣ View DTO 생성
        PcsSummaryViewDto dto = new PcsSummaryViewDto();

        // 3️⃣ 상태 값
        dto.setPcsStatus(pcsStatus);
        dto.setChargeDischargeStatus(chargeDischargeStatus);

        // 4️⃣ 숫자는 여기서 단 한 번 포맷 (유효전력 기준)
        dto.setPvActivePowerKw(ValueFormatter.f2(pvActivePowerKw));
        dto.setChargeDischargeReferenceKw(ValueFormatter.f2(referenceKw));
        dto.setPcsActivePowerKw(ValueFormatter.f2(pcsActivePowerKw));

        return dto;
    }
}
