package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.status.BatteryRackHealthStatusType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Samsung SDI Battery Rack 상태 Provider
 *
 * - SDI 메모리맵 해석 책임
 * - Vendor 종속 로직은 여기서만 존재
 * - Rack 단일 기준 Raw 판단만 수행
 * - 집계 / Table / ViewDto / UI 개념 ❌
 */
@Component
@Primary
public class SamsungBatteryRackStatusProvider
        implements BatteryRackStatusProvider {

    @Override
    public BatteryRackHealthStatusType getHealthStatus(Long rackId) {

        // TODO
        // 1. rackId → SDI 메모리맵 offset 결정
        // 2. RTU / 장비 데이터 조회
        // 3. 상태 코드 판정

        // ===== 더미 데이터 (rackId 기준) =====
        if (rackId == 1L) {
            return BatteryRackHealthStatusType.NORMAL;
        }

        if (rackId == 2L) {
            return BatteryRackHealthStatusType.WARNING;
        }

        return BatteryRackHealthStatusType.FAULT;
    }

    @Override
    public boolean isInternalCommOk(Long rackId) {

        // TODO
        // rackId 기준 내부 통신 상태 판단

        // ===== 더미 =====
        return rackId != 3L;
    }
}
