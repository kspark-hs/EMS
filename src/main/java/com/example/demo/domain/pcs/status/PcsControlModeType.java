package com.example.demo.domain.pcs.status;

/**
 * PCS 제어 모드
 *
 * - REMOTE : EMS / 서버 원격 제어
 * - LOCAL  : 현장 패널 직접 제어
 */
public enum PcsControlModeType {

    REMOTE("원격"),
    LOCAL("로컬");

    private final String label;

    PcsControlModeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

