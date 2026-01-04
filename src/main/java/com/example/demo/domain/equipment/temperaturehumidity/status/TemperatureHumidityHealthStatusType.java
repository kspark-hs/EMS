package com.example.demo.domain.equipment.temperaturehumidity.status;

/**
 * Temperature / Humidity Health Status
 *
 * - PCS / FireSystem HealthStatus 기준과 동일
 * - 색상 판단은 UI에서:
 *   NORMAL -> green
 *   그 외  -> red
 */
public enum TemperatureHumidityHealthStatusType {

    /** 정상 */
    NORMAL,

    /** 주의 (임계 근접, 경고 수준) */
    WARNING,

    /** 고장 (임계 초과, 운전 불가) */
    FAULT,

    /** 통신 단절 */
    DISCONNECTED
}
