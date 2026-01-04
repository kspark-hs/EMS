package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.provider.BatteryFaultDetailProvider;
import com.example.demo.domain.battery.status.BatteryAbnormalType;
import com.example.demo.domain.battery.view.BatteryFaultDetailViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BatteryFaultDetailService {

    private final BatteryFaultDetailProvider provider;

    /**
     * 배터리 고장 상세 목록 조회
     *
     * 책임:
     * - Provider에서 Raw fault map 수신
     * - Enum 전체 순회
     * - ViewDto 조립
     *
     * ※ PCS 패턴과 1:1 동일
     */
    public List<BatteryFaultDetailViewDto> getFaultDetails(Long batteryId) {

        Map<BatteryAbnormalType, Boolean> faultMap =
                provider.getFaultStatus(batteryId);

        List<BatteryFaultDetailViewDto> result = new ArrayList<>();

        for (BatteryAbnormalType type : BatteryAbnormalType.values()) {
            boolean fault = faultMap.getOrDefault(type, false);

            result.add(
                    new BatteryFaultDetailViewDto(
                            null,                // rackNo (현재 단계에서는 null)
                            type.getLabel(),     // 화면 표시명
                            fault,               // 고장 여부
                            type.getGroupKey()   // 그룹 키
                    )
            );
        }

        return result;
    }
}
