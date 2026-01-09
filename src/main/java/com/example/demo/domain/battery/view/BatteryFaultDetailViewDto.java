package com.example.demo.domain.battery.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 배터리 고장 상세 Row View DTO
 *
 * - PCS PcsFaultDetailViewDto와 1:1 동일 구조
 * - Battery 기준 고장 상세 표현용
 * - Rack 고장정보 화면에서도 재사용됨 (rackNo 기준 필터링)
 *
 * ⚠️ 주의
 * - rackNo는 DB 연동 이후에만 값이 채워짐
 * - 현재 단계에서는 null 허용 (Battery 공통 Fault)
 */
public record BatteryFaultDetailViewDto(
        Integer rackNo,
        String faultName,
        boolean occurred,
        String groupKey
) {}
