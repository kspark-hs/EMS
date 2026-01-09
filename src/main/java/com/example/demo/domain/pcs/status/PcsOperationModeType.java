package com.example.demo.domain.pcs.status;

/**
 * PCS 운전 모드
 *
 * - AUTO   : 자동 운전
 * - MANUAL: 수동 운전 (원격)
 * - LOCAL : 현장 로컬 운전
 *
 * ※ 제어 주체(REMOTE/LOCAL)와는 다른 개념
 */
public enum PcsOperationModeType {

    AUTO("자동"),
    MANUAL("수동"),
    STOP("정지"),
    LOCAL("로컬");

    private final String label;

    PcsOperationModeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}


