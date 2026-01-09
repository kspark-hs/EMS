package com.example.demo.domain.equipment.airconditioner.view;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * AirConditioner Status Table View DTO
 *
 * - 에어컨 상세 테이블 전체를 표현
 * - 여러 대의 에어컨 Row 정보를 포함
 * - PCS / 배터리 / PV전력량계와 동일한 구조
 */
@Getter
@Setter
public class AirConditionerStatusTableViewDto {

    /** 에어컨 상세 Row 목록 */
    private List<AirConditionerDetailRowViewDto> rows;

    /**
     * 에어컨 전체 운전 요약
     *
     * 예)
     * - 냉방 2대 · 난방 2대 혼합 운전 중
     * - 냉방 4대 운전 중
     */
    private String overallOperationSummaryText;
}
