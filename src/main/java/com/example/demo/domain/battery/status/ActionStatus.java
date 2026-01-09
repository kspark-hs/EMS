package com.example.demo.domain.battery.status;

public enum ActionStatus {

    CHARGING("충전 중"),
    DISCHARGING("방전 중"),
    READY("대기"),
    STOPPED("정지"),
    MAINTENANCE("점검");

    private final String label;

    ActionStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

