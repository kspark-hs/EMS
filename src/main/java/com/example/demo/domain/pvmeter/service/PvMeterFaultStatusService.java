package com.example.demo.domain.pvmeter.service;

import com.example.demo.domain.pvmeter.view.PvMeterFaultStatusViewDto;
import org.springframework.stereotype.Service;

@Service
public class PvMeterFaultStatusService {

    /**
     * PV 전력량계 고장 상태 조회
     * - 현재는 더미
     * - DB / 메모리맵 연결 시 이 메서드만 수정
     */
    public PvMeterFaultStatusViewDto getStatus(Long pvMeterId) {

        PvMeterFaultStatusViewDto dto =
                new PvMeterFaultStatusViewDto();

        dto.setFaultOccurred("없음");
        dto.setInternalCommStatus("정상");
        dto.setExternalCommStatus("정상");
        dto.setInterlockStatus("정상");

        return dto;
    }
}

