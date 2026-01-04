package com.example.demo.domain.battery.status;

public enum BatteryFaultStatus {

    NORMAL("정상"),
    FAULT("고장 발생");

    private final String label;

    BatteryFaultStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
