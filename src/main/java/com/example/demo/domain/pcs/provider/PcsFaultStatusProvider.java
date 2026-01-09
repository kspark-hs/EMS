package com.example.demo.domain.pcs.provider;

/**
 * PCS 고장 상태 요약 Provider
 *
 * 책임:
 * - PCS 고장 "Raw 상태" 제공
 * - 해석 / 문자열 변환 ❌
 */
public interface PcsFaultStatusProvider {

    /** PCS 고장 발생 여부 */
    boolean hasFault(Long pcsId);

    /** 내부 통신 정상 여부 */
    boolean isInternalCommOk(Long pcsId);

    /** 외부 통신 정상 여부 */
    boolean isExternalCommOk(Long pcsId);

    /** 인터락 활성 여부 */
    boolean isInterlockActive(Long pcsId);
}
