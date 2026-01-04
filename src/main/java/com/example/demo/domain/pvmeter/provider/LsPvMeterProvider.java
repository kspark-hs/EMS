package com.example.demo.domain.pvmeter.provider;

import org.springframework.stereotype.Component;

/**
 * LS산전(LS ELECTRIC) PV 전력량계 Provider
 *
 * - 대상 모델: GIPAM-2200DG
 * - 통신: Modbus TCP / RTU
 * - 역할: 계측(Read Only)
 */
@Component
public class LsPvMeterProvider implements PvMeterProvider {

    @Override
    public boolean isOnline(Long pvMeterId) {
        // TODO: Modbus 통신 상태 기반 판단
        return true;
    }

    @Override
    public double getActivePower(Long pvMeterId) {
        // TODO: GIPAM-2200DG
        // Register: 30041 (Total Active Power)
        return 0.0;
    }

    @Override
    public double getTotalEnergy(Long pvMeterId) {
        // TODO: GIPAM-2200DG
        // Register: 30051 (Total Active Energy)
        return 0.0;
    }
}

