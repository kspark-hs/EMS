package com.example.demo.domain.common.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 고장 상세 카드 공용 View DTO
 * - PCS / Battery / Rack 공통 사용
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaultItemViewDto {

    /** 고장 발생 여부 */
    private boolean fault;

    /** 화면에 표시할 고장 이름 */
    private String label;
}

