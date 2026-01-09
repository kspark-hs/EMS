package com.example.demo.domain.equipment.firesystem.view;

import com.example.demo.domain.equipment.firesystem.status.FireSystemHealthStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * FireSystem 상태 테이블 Row View DTO
 *
 * - PCS StatusTableRowViewDto 구조와 동일
 * - UI는 이 Row를 List 형태로만 사용
 * - 상태 해석 로직 포함 금지
 */
@Data
@AllArgsConstructor
public class FireSystemStatusTableRowViewDto {

    /** 소방설비 식별자 (ex: FS-01, MAIN-FIRE) */
    private String fireSystemName;

    /** 소방설비 상태 */
    private FireSystemHealthStatusType status;

    /** 화재 감지 여부 (센서 기준 Raw 결과) */
    private Boolean fireDetected;

    /** 통신 상태 */
    private Boolean communicationAlive;
}

