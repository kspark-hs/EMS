package com.example.demo.domain.pcs.status;

/**
 * PCS Fault Type (UI 기준)
 *
 * - Modbus Memory Map 다수 bit → 의미 단위 Fault로 통합
 * - UI / Alarm / History / Notification 공통 기준
 */
public enum PcsFaultType {

    /* =========================
     * A. 전력 변환부
     * ========================= */

    IGBT_FAULT("IGBT Fault", true, "IGBT"),

    IGBT_U_FAULT("IGBT U Phase Fault", false, "IGBT"),
    IGBT_V_FAULT("IGBT V Phase Fault", false, "IGBT"),
    IGBT_W_FAULT("IGBT W Phase Fault", false, "IGBT"),

    DC_CB_TRIP("DC CB Trip", true, null),
    DC_SIDE_ABNORMAL("DC Side Abnormal", true, null),

    /* =========================
     * B. 전압 / 전류 이상
     * ========================= */
    DC_OVER_VOLTAGE("DC Over Voltage", true, null),
    DC_UNDER_VOLTAGE("DC Under Voltage", true, null),
    AC_OVER_VOLTAGE("AC Over Voltage", true, null),
    AC_UNDER_VOLTAGE("AC Under Voltage", true, null),
    OVER_CURRENT("Over Current", true, null),

    /* =========================
     * C. 주파수 / 계통
     * ========================= */
    OVER_FREQUENCY("Over Frequency", true, null),
    UNDER_FREQUENCY("Under Frequency", true, null),
    ISLANDING_ABNORMAL("Islanding Abnormal", true, null),
    GRID_CB_FAULT("Grid CB Fault", true, null),

    /* =========================
     * D. 보호 / 안전
     * ========================= */
    GROUND_FAULT("Ground Fault", true, null),
    EMERGENCY_STOP("Emergency Stop", true, null),
    DOOR_OPEN("Door Open", true, null),

    /* =========================
     * E. 온도 / 냉각
     * ========================= */
    OVER_TEMPERATURE("Over Temperature", true, null),
    FAN_FAULT("Fan Fault", true, null),

    /* =========================
     * F. 제어 / 설정 / 경고
     * ========================= */
    FUSE_FAULT("Fuse Fault", true, null),
    MC_FAULT("MC Fault", true, null),
    PARAMETER_ERROR("Parameter Error", true, null),
    SOC_WARNING("SOC Warning", true, null),
    TEST_MODE("Test Mode", true, null),
    SMPS_FAULT("SMPS Fault", true, null);

    private final String label;
    private final boolean uiVisible;
    private final String groupKey;

    PcsFaultType(String label, boolean uiVisible, String groupKey) {
        this.label = label;
        this.uiVisible = uiVisible;
        this.groupKey = groupKey;
    }

    public String getLabel() {
        return label;
    }

    public boolean isUiVisible() {
        return uiVisible;
    }

    public String getGroupKey() {
        return groupKey;
    }
}

