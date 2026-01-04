package com.example.demo.domain.pcs.provider;

import com.example.demo.domain.pcs.status.PcsFaultType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * PCS Fault Dummy Provider
 *
 * - 메모리맵 RAW bit → 의미 단위 Fault 변환
 * - IGBT OR 로직 포함
 * - 추후 DB / RTU Provider로 교체 예정
 */
@Primary
@Component
public class DestinPowerPcsFaultDetailProvider implements PcsFaultDetailProvider {

    @Override
    public Map<PcsFaultType, Boolean> getFaultStatus(Long pcsId) {

        Map<PcsFaultType, Boolean> map =
                new EnumMap<>(PcsFaultType.class);

        /* =========================
         * 0. 기본값 false 초기화
         * ========================= */
        for (PcsFaultType type : PcsFaultType.values()) {
            map.put(type, false);
        }

        /* =========================
         * 1. 메모리맵 RAW bit (더미)
         * ========================= */
        boolean igbtRFault = true;     // IGBT R Fault
        boolean igbtSFault = false;    // IGBT S Fault
        boolean igbtTFault = false;    // IGBT T Fault
        boolean igbtOverTemp = false;  // IGBT Over Temp

        boolean dcCbTrip = true;
        boolean dcOverVoltage = false;
        boolean gridOverVoltage = false;

        /* =========================
         * 2. IGBT OR 매핑 (핵심)
         * ========================= */
        boolean igbtFault =
                igbtRFault
                        || igbtSFault
                        || igbtTFault
                        || igbtOverTemp;

        map.put(PcsFaultType.IGBT_FAULT, igbtFault);

        /* =========================
         * 3. 단일 Fault 매핑
         * ========================= */
        map.put(PcsFaultType.DC_CB_TRIP, dcCbTrip);
        map.put(PcsFaultType.DC_OVER_VOLTAGE, dcOverVoltage);
        map.put(PcsFaultType.AC_OVER_VOLTAGE, gridOverVoltage);

        // 필요 시 계속 추가
        // map.put(PcsFaultType.OVER_CURRENT, ...);
        // map.put(PcsFaultType.GROUND_FAULT, ...);

        return map;
    }
}
