package com.example.demo.domain.pvmeter.view;

import lombok.Data;

@Data
public class PvMeterSummaryViewDto {

    /** 온라인 상태 (ONLINE / OFFLINE) */
    private String onlineStatus;

    /** 출력전력 (kW) */
    private String activePowerKw;

    /** 역률 */
    private String powerFactor;

    /** 주파수 (Hz) */
    private String frequencyHz;
}

