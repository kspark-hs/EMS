package com.example.demo.domain.equipment.airconditioner.provider;

import com.example.demo.domain.equipment.airconditioner.dto.AirConditionerSummaryDto;
import java.util.List;

/**
 * AirConditioner Status Table Provider
 *
 * - 에어컨 상태 테이블 전용 Provider
 * - 에어컨 n대의 상태 요약 목록 제공
 * - UI / Scheduler / View 로직 금지
 */
public interface AirConditionerStatusTableProvider {

    /**
     * 에어컨 상태 테이블용 요약 정보 목록 조회
     *
     * @return 에어컨 요약 정보 리스트 (n대)
     */
    List<AirConditionerSummaryDto> getStatusSummaries();
}
