package com.example.demo.domain.pcs.view;

import lombok.Data;

/**
 * PCS 고장 상태 카드 전용 View DTO (전체 기준)
 * 판단 값만 포함 (boolean)
 */
@Data
public class PcsFaultStatusViewDto {

    /** PCS 중 하나라도 고장 */
    private boolean hasFault;

    /** 내부 통신 정상 여부 (PCS ↔ RTU) */
    private boolean internalCommOk;

    /** 외부 통신 정상 여부 (RTU ↔ 서버) */
    private boolean externalCommOk;

}
