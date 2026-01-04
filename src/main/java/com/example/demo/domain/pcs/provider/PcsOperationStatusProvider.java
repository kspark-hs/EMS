package com.example.demo.domain.pcs.provider;

/**
 * PCS 운전 상태 Provider (계약)
 *
 * 책임:
 * - PCS 운전/제어 관련 Raw 상태 제공
 * - RTU / DB / Memory Map 값 그대로 반환
 * - 해석(운전중/정지/충전/방전)은 Service 책임
 */
public interface PcsOperationStatusProvider {

    /** PCS 운전 가능 상태 (RUN 신호) */
    boolean isRunning();

    /** PCS 운전 모드 (AUTO / MANUAL) */
    String getOperationMode();

    /** 제어 위치 (LOCAL / REMOTE) */
    String getControlMode();

    /** 충전 중 여부 */
    boolean isCharging();

    /** 방전 중 여부 */
    boolean isDischarging();
}
