package com.example.demo.domain.battery.status;

public class BatteryFaultSnapshot {

    private final boolean hasFault;
    private final boolean internalCommOk;
    private final boolean externalCommOk;
    private final boolean interlockOk;

    public BatteryFaultSnapshot(
            boolean hasFault,
            boolean internalCommOk,
            boolean externalCommOk,
            boolean interlockOk
    ) {
        this.hasFault = hasFault;
        this.internalCommOk = internalCommOk;
        this.externalCommOk = externalCommOk;
        this.interlockOk = interlockOk;
    }

    public boolean isHasFault() {
        return hasFault;
    }

    public boolean isInternalCommOk() {
        return internalCommOk;
    }

    public boolean isExternalCommOk() {
        return externalCommOk;
    }

    public boolean isInterlockOk() {
        return interlockOk;
    }
}
