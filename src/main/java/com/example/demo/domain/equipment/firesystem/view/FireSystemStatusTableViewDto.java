package com.example.demo.domain.equipment.firesystem.view;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * FireSystem 상태 테이블 View DTO
 *
 * - 카드 단위 View
 * - UI는 이 DTO만 사용
 * - 상태 판단 / 색상 결정 로직 포함 금지
 */
@Data
@AllArgsConstructor
public class FireSystemStatusTableViewDto {

    /** 소방설비 전체 상태 요약 (ex: 정상 / 화재 감지 / 통신 단절) */
    private String overallStatusText;

    /** 소방설비 상태 테이블 Row 목록 */
    private List<FireSystemStatusTableRowViewDto> rows;
}
