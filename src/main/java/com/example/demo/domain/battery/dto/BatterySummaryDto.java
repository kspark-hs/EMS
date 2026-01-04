package com.example.demo.domain.battery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatterySummaryDto {

    /** 외부 통신 상태 */
    private boolean online;

    /** 메인 릴레이 ON / OFF */
    private boolean relayOn;

    /** 충방전 상태 (Ready / Charging / Discharging / 제한됨) */
    private String operationStatus;

    /** SOC (%) */
    private Double soc;

    /** DC 전압 (V) */
    private Double dcVoltage;

    /** DC 전류 (A) */
    private Double dcCurrent;
}

