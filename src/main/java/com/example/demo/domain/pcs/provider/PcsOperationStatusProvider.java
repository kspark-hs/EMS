package com.example.demo.domain.pcs.provider;

/**
 * PCS 운전 상태 Provider 계약
 *
 * ✔ 벤더/브랜드별 Raw 데이터 해석
 * ✔ 1차 상태 판단 수행
 *
 * ❌ UI / View / Controller 의존 금지
 */
public interface PcsOperationStatusProvider {

    /**
     * 고장 / 보호정지 존재 여부
     */
    boolean isFaultExist(Long pcsId);

    /**
     * 운전 가능 여부
     * - 인터록
     * - 보호정지
     * - 통신 불가
     */
    boolean isOperationAvailable(Long pcsId);

    /**
     * 운전 모드
     * 허용 값: AUTO / MANUAL / LOCAL
     */
    String getOperationMode(Long pcsId);

    /**
     * 제어 모드
     * 허용 값: REMOTE / LOCAL
     */
    String getControlMode(Long pcsId);
}
