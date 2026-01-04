package com.example.demo.domain.pvmeter.service;

import com.example.demo.domain.pvmeter.view.PvMeterDetailViewDto;
import org.springframework.stereotype.Service;

@Service
public class PvMeterDetailService {

    /**
     * PV 전력량계 상세 정보 조회
     *
     * @param pvMeterId PV 전력량계 ID
     * @return 상세 정보 View DTO
     */
    public PvMeterDetailViewDto getDetail(Long pvMeterId) {

        PvMeterDetailViewDto pvDetail = new PvMeterDetailViewDto();

        /* =========================
         * 전압
         * ========================= */
        PvMeterDetailViewDto.VoltageSection voltage =
                new PvMeterDetailViewDto.VoltageSection();

        voltage.setAvg(235.97);
        voltage.setPhaseA(235.88);
        voltage.setPhaseB(235.54);
        voltage.setPhaseC(236.48);
        voltage.setLineAB(408.15);
        voltage.setLineBC(409.27);
        voltage.setLineCA(408.97);

        pvDetail.setVoltage(voltage);

        /* =========================
         * 전류
         * ========================= */
        PvMeterDetailViewDto.CurrentSection current =
                new PvMeterDetailViewDto.CurrentSection();

        current.setAvg(102.28);
        current.setPhaseA(102.11);
        current.setPhaseB(103.31);
        current.setPhaseC(101.43);

        pvDetail.setCurrent(current);

        /* =========================
         * 전력
         * ========================= */
        PvMeterDetailViewDto.PowerSection power =
                new PvMeterDetailViewDto.PowerSection();

        power.setActive(83.93);
        power.setReactive(5.88);
        power.setApparent(0.0);
        power.setTotalActiveEnergy(2.5);      // GWh
        power.setTotalReactiveEnergy(315.28); // MVarh
        power.setTotalApparentEnergy(0.0);

        pvDetail.setPower(power);

        /* =========================
         * 기타
         * ========================= */
        PvMeterDetailViewDto.EtcSection etc =
                new PvMeterDetailViewDto.EtcSection();

        etc.setPowerFactor(99.66);
        etc.setFrequency(60.03);
        etc.setLoadRateA(0.0);
        etc.setLoadRateB(0.0);
        etc.setLoadRateC(0.0);

        pvDetail.setEtc(etc);

        return pvDetail;
    }
}
