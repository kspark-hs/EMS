package com.example.demo.domain.pcs.provider;

import com.example.demo.domain.pcs.status.PcsFaultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DestinPowerPcsFaultProvider implements PcsFaultProvider {

    private final PcsFaultDetailProvider faultDetailProvider;

    /**
     * PCS Fault RAW Map 제공
     * - 판단 로직 없음
     * - Detail Provider 위임
     */
    @Override
    public Map<PcsFaultType, Boolean> getRawFaultMap(Long pcsId) {
        return faultDetailProvider.getFaultStatus(pcsId);
    }
}
