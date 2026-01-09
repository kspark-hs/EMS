package com.example.demo.domain.pcs.status;

public enum PcsOverallStatusType {

    NORMAL("정상"),
    STOPPED("정지"),
    FAULT("고장"),
    COMM_ERROR("통신 이상");

    private final String label;

    PcsOverallStatusType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

