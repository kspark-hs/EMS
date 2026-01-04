package com.example.demo.domain.pcs.view;

import lombok.Data;

/**
 * PCS 고장 상태 카드 전용 View DTO
 * - PCS/통신/인터록 요약 상태
 * - DB / Entity 비의존
 */
@Data
public class PcsFaultStatusViewDto {

    /** 고장 발생 여부 (없음 / 있음) */
    private String faultOccurred;

    /** 내부 통신 상태 (PCS ↔ RTU) */
    private String internalCommStatus;

    /** 외부 통신 상태 (RTU ↔ EMS) */
    private String externalCommStatus;

    /** 인터록 상태 (정상 / 동작) */
    private String interlockStatus;
}

