package com.example.demo.domain.battery.view;

import lombok.Data;

@Data
public class BatteryFaultStatusViewDto {

    /** 고장 발생 여부 (없음 / 있음) */
    private String faultOccurred;

    /** 내부 통신 상태 (BMS ↔ RTU) */
    private String internalCommStatus;

    /** 외부 통신 상태 (RTU ↔ EMS) */
    private String externalCommStatus;

    /** 인터록 상태 (정상 / 동작) */
    private String interlockStatus;
}

