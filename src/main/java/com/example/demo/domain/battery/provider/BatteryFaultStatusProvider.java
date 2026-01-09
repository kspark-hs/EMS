package com.example.demo.domain.battery.provider;

/**
 * 배터리 랙 고장 상태 Provider
 *
 * 책임:
 * - 장비 / DB 원본 값을 읽어
 * - "고장 여부 / 통신 상태 / 인터록 상태"를 boolean으로 제공
 *
 * 기준:
 * - 최소 판단 단위 = rackId
 * - PCS FaultStatusProvider와 100% 동일한 책임 구조
 *
 * 주의:
 * - 집계 금지
 * - List 처리 금지
 * - UI 의미 해석 금지
 */
public interface BatteryFaultStatusProvider {

    /** 랙 고장 발생 여부 */
    boolean hasFault(Long rackId);

    /** 랙 내부 통신 정상 여부 */
    boolean isInternalCommOk(Long rackId);

    /** 랙 외부 통신 정상 여부 */
    boolean isExternalCommOk(Long rackId);

    /** 랙 인터록 정상 여부 */
    boolean isInterlockOk(Long rackId);
}
