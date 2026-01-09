package com.example.demo.domain.battery.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Battery Aggregate Status Snapshot
 *
 * - Rack 상태를 집계한 Battery 최종 판결 결과
 * - Partial Operation (부분 운전) 지원
 * - Immutable Snapshot
 */
@Getter
@RequiredArgsConstructor
public class BatteryAggregateStatusSnapshot {

    /** 전체 배터리 상태 */
    private final BatteryOverallStatusType overallStatus;

    /** 충·방전 상태 */
    private final BatteryChargeDischargeStatusType chargeDischargeStatus;

    /** 운전 모드 */
    private final BatteryOperationModeType operationMode;

    /** 내부 통신 정상 여부 (전체 rack 기준) */
    private final boolean internalCommOk;

    /** 외부 통신 정상 여부 */
    private final boolean externalCommOk;

    /** 전체 Rack 개수 */
    private final int totalRackCount;

    /** 고장 Rack 개수 */
    private final int faultRackCount;

    /** 일부 Rack 고장 여부 (partial operation 판단용) */
    private final boolean partialRackFault;

    /** 운전 가능 여부 (최종 판결) */
    private final boolean operable;
}
