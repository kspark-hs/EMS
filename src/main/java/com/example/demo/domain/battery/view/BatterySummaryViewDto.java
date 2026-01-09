package com.example.demo.domain.battery.view;

import com.example.demo.domain.battery.status.ActionStatus;
import lombok.Data;

@Data
public class BatterySummaryViewDto {

    /** 온라인 상태 표시용 */
    private String onlineStatus;

    /** 메인 릴레이 상태 */
    private String relayStatus;


    /** 동작 상태 (충전/방전/대기 등) */
    private ActionStatus actionStatus;

    /** SOC (%) */
    private Double soc;

    /** DC 전압 (V) */
    private Double dcVoltage;

    /** DC 전류 (A) */
    private Double dcCurrent;
}
