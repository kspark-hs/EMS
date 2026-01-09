package com.example.demo.domain.pcs.provider;

/**
 * PCS Aggregate Status 판결 전용 Raw Provider
 *
 * 네이밍 규칙:
 * - Aggregate : Aggregate 계층 전용
 * - Status    : 상태 판단 재료
 * - Raw       : boolean / flag 값만 제공
 *
 * 책임:
 * - 판결에 필요한 Raw 상태 제공
 * - UI / View / DTO / 문자열 변환 ❌
 */
public interface PcsAggregateStatusRawProvider {

    /* 운전 상태 */
    boolean isRunning(Long pcsId);
    boolean isCharging(Long pcsId);
    boolean isDischarging(Long pcsId);

    /* 통신 상태 */
    boolean isInternalCommOk(Long pcsId);
    boolean isExternalCommOk(Long pcsId);
}

