package com.example.demo.domain.equipment.airconditioner.view;

import com.example.demo.domain.equipment.airconditioner.status.AirConditionerHealthStatusType;
import lombok.Getter;
import lombok.Setter;

/**
 * AirConditioner Detail Row View DTO
 *
 * - 에어컨 상세 테이블 1행 표현용 DTO
 * - Service에서 모든 표현 문자열 조합 완료
 * - View(Thymeleaf)는 단순 출력만 담당
 */
@Getter
@Setter
public class AirConditionerDetailRowViewDto {

    /** 에어컨 표시 ID (예: 01, 02) */
    private String displayId;

    /** 종합 상태 ENUM */
    private AirConditionerHealthStatusType healthStatus;

    /** 상태 표시 텍스트 (정상 / 경고 / 고장 / 단절) */
    private String healthStatusText;

    /** 상태 색상 클래스 (text-green-600 등) */
    private String healthStatusClass;

    /** 요약 카드용 제어 요약 (한 줄) */
    private String controlSummaryText;

    /** 상세 카드용 제어 요약 (두 줄, HTML 허용) */
    private String controlSummaryHtml;

    /** 🔹 AUTO 제어 보조 문구 (UI 설명용) */
    private String controlSubText;

    /** 운전 요약 텍스트 */
    private String operationSummaryText;

    /** 통신 상태 */
    private boolean online;
}

