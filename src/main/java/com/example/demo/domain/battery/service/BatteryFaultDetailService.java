package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.provider.BatteryFaultDetailProvider;
import com.example.demo.domain.battery.status.BatteryAbnormalType;
import com.example.demo.domain.battery.view.BatteryFaultDetailViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BatteryFaultDetailService {

    private final BatteryFaultDetailProvider provider;

    /**
     * 🔴 Rack 고장 상세 (PCS와 동일 패턴)
     *
     * 기준:
     * - 고장 최소 단위 = Rack
     * - BatteryAbnormalType 전체 항목 항상 노출
     * - 발생 여부만 true / false
     *
     * ※ Battery 전체 OR 집계 고장 개념은 제거됨
     */
    public List<BatteryFaultDetailViewDto> getRackFaultDetails(Long rackId) {

        Map<String, Boolean> faultMap = new LinkedHashMap<>();

        // 1️⃣ BatteryAbnormalType 전체 초기화
        for (BatteryAbnormalType type : BatteryAbnormalType.values()) {
            faultMap.put(type.getLabel(), false);
        }

        // 2️⃣ 실제 Rack 고장 반영
        List<BatteryFaultDetailViewDto> occurred =
                provider.getFaultDetails(rackId);

        for (BatteryFaultDetailViewDto dto : occurred) {
            faultMap.put(dto.faultName(), true);
        }

        // 3️⃣ View DTO 생성
        return faultMap.entrySet().stream()
                .map(e -> new BatteryFaultDetailViewDto(
                        null,       // rackNo (현재 UI 미사용)
                        e.getKey(), // faultName
                        e.getValue(), // occurred
                        null        // groupKey (차후 확장 여지)
                ))
                .toList();
    }
}
