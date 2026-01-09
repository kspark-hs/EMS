package com.example.demo.domain.pcs.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * PCS Aggregate Status Snapshot
 *
 * - PCS 단일 기준 "최종 판결 결과"
 * - UI / Summary / Table / Button Enable 기준
 * - 계산 로직 없음
 * - ❗ 모든 값은 Service에서 확정되어 주입됨 (Immutable)
 */
@Getter
@RequiredArgsConstructor
public class PcsAggregateStatusSnapshot {

    /** 전체 PCS 상태 */
    private final PcsOverallStatusType overallStatus;

    /** 충·방전 상태 */
    private final PcsChargeDischargeStatusType chargeDischargeStatus;

    /** 운전 모드 (AUTO / MANUAL / LOCAL) */
    private final PcsOperationModeType operationMode;

    /** 제어 모드 (REMOTE / LOCAL) */
    private final PcsControlModeType controlMode;

    /** 내부 통신 정상 여부 */
    private final boolean internalCommOk;

    /** 외부 통신 정상 여부 */
    private final boolean externalCommOk;

    /** 운전 가능 여부 (최종 판결 결과) */
    private final boolean operable;
}
