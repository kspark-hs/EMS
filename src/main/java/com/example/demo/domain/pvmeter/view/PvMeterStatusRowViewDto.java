package com.example.demo.domain.pvmeter.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PvMeterStatusRowViewDto {

    /** PV 전력량계 식별자 (#1) */
    private String meterName;

    /** 상태 (NORMAL / FAULT / DISCONNECTED) */
    private String status;

    /** 출력전력 (PV 실측 발전량) */
    private Double outputPower; // kW

    /** AC 유효전력 */
    private Double activePower; // kW

    /** 주파수 */
    private Double frequency; // Hz

    /** 마지막 데이터 수신 시각 (표시용, 이미 포맷된 문자열) */
    private String lastReceivedAt;

    /** 데이터 수신 주기 (ms) */
    private Long receiveIntervalMs;
}
