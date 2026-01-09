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

        Map<PcsFaultType, Boolean> map = new EnumMap<>(PcsFaultType.class);

        // 0. 전부 정상 초기화
        for (PcsFaultType type : PcsFaultType.values()) {
            map.put(type, false);
        }

        // =========================
        // TEST-PCS-FAULT-02
        // PCS #2 : DC CB Trip
        // =========================
        if (pcsId != null && pcsId == 2L) {
            map.put(PcsFaultType.DC_CB_TRIP, true);
        }

        return map;
    }
}


