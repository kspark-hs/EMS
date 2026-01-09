package com.example.demo.domain.battery.status;

import lombok.Getter;

/**
 * Samsung SDI Battery Abnormal Type (FULL Memory Map)
 *
 * 원칙:
 * - 삼성 SDI Modbus 메모리맵 기준 FULL 반영
 * - PCS PcsFaultType 과 1:1 동일 패턴
 * - Enum = UI 표시 항목 수
 * - 수집 여부와 무관하게 항상 전체 노출
 */
@Getter
public enum BatteryAbnormalType {

    /* =========================
     * 🔴 System / BMS
     * ========================= */
    SYSTEM_STOP("System Stop", "SYSTEM"),
    BATTERY_EMERGENCY_STOP("Battery Emergency Stop", "SYSTEM"),
    BMS_ALARM("BMS Alarm", "SYSTEM"),

    /* =========================
     * 🔴 Communication
     * ========================= */
    RACK_SYSTEM_COMMUNICATION_FAIL("Rack <-> System Communication Fail", "COMM"),
    RACK_MODULE_COMMUNICATION_FAIL("Rack <-> Module Communication Fail", "COMM"),
    BATTERY_COMMUNICATION_FAIL("Battery Communication Fail", "COMM"),
    RTU_COMMUNICATION_FAIL("RTU Communication Fail", "COMM"),
    TCP_502_COMMUNICATION_FAIL("TCP(502) Communication Fail", "COMM"),
    TCP_602_COMMUNICATION_FAIL("TCP(602) Communication Fail", "COMM"),

    /* =========================
     * 🔴 Cell Voltage
     * ========================= */
    CELL_OVER_VOLTAGE("Cell Over Voltage", "CELL_VOLT"),
    CELL_UNDER_VOLTAGE("Cell Under Voltage", "CELL_VOLT"),
    CELL_VOLTAGE_IMBALANCE("Cell Voltage Imbalance", "CELL_VOLT"),

    /* =========================
     * 🔴 Cell Temperature
     * ========================= */
    CELL_OVER_TEMPERATURE("Cell Over Temperature", "CELL_TEMP"),
    CELL_UNDER_TEMPERATURE("Cell Under Temperature", "CELL_TEMP"),
    CELL_TEMPERATURE_IMBALANCE("Cell Temperature Imbalance", "CELL_TEMP"),

    /* =========================
     * 🔴 Charge / Discharge Limit
     * ========================= */
    CELL_CHARGE_OPERATION_LIMIT("Cell Charge Operation Limit", "CELL_LIMIT"),
    CELL_DISCHARGE_OPERATION_LIMIT("Cell Discharge Operation Limit", "CELL_LIMIT"),

    /* =========================
     * 🔴 String Voltage / Current
     * ========================= */
    STRING_OVER_VOLTAGE("String Over Voltage", "STRING"),
    STRING_UNDER_VOLTAGE("String Under Voltage", "STRING"),
    STRING_CHARGE_OVER_CURRENT("String Charge Over Current", "STRING"),
    STRING_DISCHARGE_OVER_CURRENT("String Discharge Over Current", "STRING"),
    STRING_VOLTAGE_SENSING_DIFFERENCE("String Voltage Sensing Difference", "STRING"),
    STRING_CURRENT_SENSOR_FAIL("String Current Sensor Fail", "STRING"),
    STRING_COUNT_IMBALANCE("String Count Imbalance", "STRING"),

    /* =========================
     * 🔴 Rack Voltage / Current
     * ========================= */
    RACK_OVER_VOLTAGE("Rack Over Voltage", "RACK"),
    RACK_UNDER_VOLTAGE("Rack Under Voltage", "RACK"),
    RACK_CHARGE_OVER_CURRENT("Rack Charge Over Current", "RACK"),
    RACK_DISCHARGE_OVER_CURRENT("Rack Discharge Over Current", "RACK"),
    RACK_STRING_CURRENT_IMBALANCE("Rack String Current Imbalance", "RACK"),

    /* =========================
     * 🔴 Fuse / DC Switch
     * ========================= */
    RACK_FUSE_FAIL("Rack Fuse Fail", "FUSE"),
    RACK_FUSE_FAIL_OPTION("Rack Fuse Fail (Option)", "FUSE"),
    FUSE_OR_DC_CONTACTOR_SENSING_FAIL("Fuse or DC Contactor Sensing Fail", "FUSE"),

    DC_SWITCH_COUNT_FAIL("DC Switch Count Fail", "DC_SWITCH"),
    DC_SWITCH_1_SENSING_FAIL("Fuse1 or DC Switch#1 Sensing Fail", "DC_SWITCH"),
    DC_SWITCH_2_SENSING_FAIL("Fuse2 or DC Switch#2 Sensing Fail", "DC_SWITCH"),

    RACK_DC_SWITCH_1_SENSING_FAIL("Rack DC Switch#1 (String#1) Sensing Fail", "DC_SWITCH"),
    RACK_DC_SWITCH_2_SENSING_FAIL("Rack DC Switch#2 (String#2) Sensing Fail", "DC_SWITCH"),
    RACK_DC_SWITCH_3_SENSING_FAIL("Rack DC Switch#3 (Main) Sensing Fail", "DC_SWITCH"),

    RACK_DC_SWITCH_1_FAIL("Rack DC Switch#1 (String#1) Fail", "DC_SWITCH"),
    RACK_DC_SWITCH_2_FAIL("Rack DC Switch#2 (String#2) Fail", "DC_SWITCH"),
    RACK_DC_SWITCH_3_FAIL("Rack DC Switch#3 (Main) Fail", "DC_SWITCH"),

    RACK_DC_SWITCH_1_COUNT_FAIL("Rack DC Switch#1 (String#1) Count Fail", "DC_SWITCH"),
    RACK_DC_SWITCH_2_COUNT_FAIL("Rack DC Switch#2 (String#2) Count Fail", "DC_SWITCH"),
    RACK_DC_SWITCH_3_COUNT_FAIL("Rack DC Switch#3 (Main) Count Fail", "DC_SWITCH"),

    RACK_DC_SWITCH_2_ON("Rack DC Switch#2 (String#2) On", "DC_SWITCH"),
    RACK_DC_SWITCH_3_ON("Rack DC Switch#3 (Main) On", "DC_SWITCH"),

    /* =========================
     * 🔴 Balance / Operation
     * ========================= */
    OPERATING_CELL_BALANCING("Operating Cell Balancing during the break", "BALANCE"),

    /* =========================
     * 🔴 Option / Feedback
     * ========================= */
    ADDITIONAL_PROTECTION_FAIL("Additional Protection Fail", "OPTION"),
    ADDITIONAL_FEEDBACK_SIGNAL("Additional Feedback Signal", "OPTION");

    /* =========================
     * Common Fields
     * ========================= */
    private final String label;
    private final String groupKey;

    BatteryAbnormalType(String label, String groupKey) {
        this.label = label;
        this.groupKey = groupKey;
    }
}
