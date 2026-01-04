package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.provider.PcsFaultStatusProvider;
import com.example.demo.domain.pcs.view.PcsFaultStatusViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * PCS 고장 상태 Service
 *
 * 책임:
 * - Provider에서 Raw 상태 수집
 * - 정상/고장 여부 및 상태 문자열 해석
 * - View DTO 조립
 */
@Service
@RequiredArgsConstructor
public class PcsFaultStatusService {

    private final PcsFaultStatusProvider provider;

    public PcsFaultStatusViewDto getStatus(Long pcsId) {

        boolean hasFault = provider.hasFault();
        boolean internalCommOk = provider.isInternalCommOk();
        boolean externalCommOk = provider.isExternalCommOk();
        boolean interlockOk = provider.isInterlockOk();

        String faultOccurred = hasFault ? "있음" : "없음";
        String internalCommStatus = internalCommOk ? "정상" : "이상";
        String externalCommStatus = externalCommOk ? "정상" : "이상";
        String interlockStatus = interlockOk ? "정상" : "이상";

        PcsFaultStatusViewDto dto = new PcsFaultStatusViewDto();
        dto.setFaultOccurred(faultOccurred);
        dto.setInternalCommStatus(internalCommStatus);
        dto.setExternalCommStatus(externalCommStatus);
        dto.setInterlockStatus(interlockStatus);

        return dto;
    }
}

