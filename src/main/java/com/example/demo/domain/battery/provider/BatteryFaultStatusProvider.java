package com.example.demo.domain.battery.provider;

/**
 * 배터리 고장 상태 Provider
 *
 * 책임:
 * - 장비 / DB 원본 값을 읽어
 * - "고장 여부 / 통신 상태 / 인터록 상태"를 boolean으로 제공
 *
 * ※ PCS FaultStatusProvider와 100% 동일한 시그니처
 * ※ Raw DTO는 Provider 내부 구현에서만 사용
 */
public interface BatteryFaultStatusProvider {

    /** 고장 발생 여부 */
    boolean hasFault();

    /** 내부 통신 정상 여부 */
    boolean isInternalCommOk();

    /** 외부 통신 정상 여부 */
    boolean isExternalCommOk();

    /** 인터록 정상 여부 */
    boolean isInterlockOk();
}

