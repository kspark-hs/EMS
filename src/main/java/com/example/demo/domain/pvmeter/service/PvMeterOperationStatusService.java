package com.example.demo.domain.pvmeter.service;

import com.example.demo.domain.pvmeter.view.PvMeterOperationStatusViewDto;
import org.springframework.stereotype.Service;

@Service
public class PvMeterOperationStatusService {

    /**
     * PV 전력량계 운전 상태 조회
     */
    public PvMeterOperationStatusViewDto getStatus(Long pvMeterId) {

        PvMeterOperationStatusViewDto dto =
                new PvMeterOperationStatusViewDto();

        // TODO: DB / RTU / 메모리맵 연동 예정
        dto.setActionStatus("운전 중");
        dto.setOperationMode("AUTO");
        dto.setControlMode("REMOTE");

        return dto;
    }
}

