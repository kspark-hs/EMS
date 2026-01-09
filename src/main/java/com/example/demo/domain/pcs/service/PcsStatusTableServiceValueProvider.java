package com.example.demo.domain.pcs.service;

/**
 * PCS 상태 테이블 수치 전용 Provider
 *
 * - 순수 Raw 수치 제공
 * - 판단 로직 절대 금지
 */
public interface PcsStatusTableServiceValueProvider {

    double getDcVoltage(Long pcsId);
    double getDcCurrent(Long pcsId);
    double getDcPower(Long pcsId);

    double getAcLineVoltage(Long pcsId);
    double getAcCurrent(Long pcsId);

    double getActivePower(Long pcsId);
    double getFrequency(Long pcsId);
}
