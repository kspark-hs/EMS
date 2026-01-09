package com.example.demo.domain.pcs.view;

import com.example.demo.domain.pcs.status.PcsControlModeType;
import com.example.demo.domain.pcs.status.PcsOperationModeType;
import lombok.Data;

/**
 * PCS 운전 상태 카드 전용 View DTO
 *
 * ✔ 화면 표시 전용 Contract
 * ✔ Controller → View 전달 전용
 *
 * ❌ 상태 판단 로직 없음
 * ❌ 계산 / 정책 / Raw 데이터 없음
 */
@Data
public class PcsOperationStatusViewDto {

    /**
     * 동작 상태
     * 허용 값: 운전 / 정지 / 고장
     */
    private String operationState;

    /**
     * 운전 모드
     * 허용 값: AUTO / MANUAL / LOCAL
     */
    private PcsOperationModeType operationMode;

    /**
     * 제어 모드
     * 허용 값: REMOTE / LOCAL
     */
    private PcsControlModeType controlMode;

    /**
     * 운전 가능 여부
     * true  : 운전 가능
     * false : 차단
     */
    private boolean operationAvailable;
}
