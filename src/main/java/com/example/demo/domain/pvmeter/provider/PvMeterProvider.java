package com.example.demo.domain.pvmeter.provider;

/**
 * PV 전력량계 Provider Contract
 *
 * - 벤더 무관 공통 인터페이스
 * - 발전소에 설치된 PV 전력량계가
 *   반드시 제공해야 하는 데이터 계약
 *
 * 현재는 Read 전용 (계측기)
 */
public interface PvMeterProvider {

    /**
     * PV 전력량계 온라인 여부
     */
    boolean isOnline(Long pvMeterId);

    /**
     * 현재 유효전력 (kW)
     */
    double getActivePower(Long pvMeterId);

    /**
     * 누적 발전량 (kWh)
     */
    double getTotalEnergy(Long pvMeterId);
}
