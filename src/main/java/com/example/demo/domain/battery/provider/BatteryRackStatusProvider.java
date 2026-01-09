package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.status.BatteryRackHealthStatusType;

/**
 * Battery Rack 상태 원천 Provider
 *
 * - Vendor 비의존
 * - Rack 단일 기준 Raw 계약
 * - UI / ViewDto / 문자열 ❌
 * - 집계 ❌
 * - PCS의 PcsStatusProvider와 책임 1:1 대응
 */
public interface BatteryRackStatusProvider {

    /** Rack 상태 코드 */
    BatteryRackHealthStatusType getHealthStatus(Long rackId);

    /** Rack 내부 통신 정상 여부 */
    boolean isInternalCommOk(Long rackId);
}
