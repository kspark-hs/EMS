package com.example.demo.domain.pcs.provider;

/**
 * PCS 고장 상태 요약 Provider
 *
 * 책임:
 * - PCS 고장 "상태"에 대한 Raw 정보 제공
 * - 개별 Fault Bit 나열 ❌
 * - 해석(정상/고장/표현 문자열)은 Service 책임
 */
public interface PcsFaultStatusProvider {

    /** PCS 고장 발생 여부 */
    boolean hasFault();

    /** 내부 통신 정상 여부 */
    boolean isInternalCommOk();

    /** 외부 통신 정상 여부 */
    boolean isExternalCommOk();

    /** 인터락 정상 여부 */
    boolean isInterlockOk();
}

