package com.example.demo.domain.equipment.temperaturehumidity.view;

import com.example.demo.domain.equipment.temperaturehumidity.status.TemperatureHumidityHealthStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Temperature / Humidity Status Table Row View DTO
 *
 * - PCS / FireSystem StatusTableRow 구조와 동일
 * - 다중 센서 대응
 * - 정책 판단 / Detail 카드 / AUTO 제어 공통 사용
 */
@Data
@AllArgsConstructor
public class TemperatureHumidityStatusTableRowViewDto {

    /** 센서 ID (TH-01, TH-02 ...) */
    private String sensorId;

    /** 센서 표시명 */
    private String sensorName;

    /** 통신 상태 */
    private Boolean communicationAlive;

    /** 상태 */
    private TemperatureHumidityHealthStatusType status;

    /** 상태 표시 텍스트 (정상 / 주의 / 단절) */
    private String statusText;

    /** 현재 온도 (°C) */
    private Double temperature;

    /** 온도 최소값 (°C) */
    private Double temperatureMin;

    /** 온도 최대값 (°C) */
    private Double temperatureMax;

    /** 현재 습도 (%) */
    private Double humidity;

    /** 습도 최대값 (%) */
    private Double humidityMax;
}
