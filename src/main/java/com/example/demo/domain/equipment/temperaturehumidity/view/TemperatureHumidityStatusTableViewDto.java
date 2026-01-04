package com.example.demo.domain.equipment.temperaturehumidity.view;

import com.example.demo.domain.equipment.temperaturehumidity.status.TemperatureHumidityHealthStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Temperature / Humidity Status Table View DTO
 *
 * - FireSystemStatusTableViewDto와 구조 완전 동일
 * - UI 색상 판단 기준 단일화
 * - "상태 판단"과 "표현 값"을 명확히 분리
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureHumidityStatusTableViewDto {

    /* =========================
     * 상태 요약 (상단 상태 표시)
     * ========================= */
    private TemperatureHumidityHealthStatusType overallStatus;
    private String overallStatusText;

    /* =========================
     * 대표 값 (상단 카드 표시용)
     * ========================= */
    /**
     * 전체 채널 중 최소 온도
     */
    private Double tempMin;

    /**
     * 전체 채널 중 최대 온도
     */
    private Double tempMax;

    /**
     * 전체 채널 중 최대 습도
     */
    private Double humidityMax;

    /* =========================
     * 운전 판단 (충·방전 가능 여부)
     * ========================= */
    private Boolean operable;
    private String operableText;

    /* =========================
     * 정책 기준 (UI 표기용)
     * ========================= */
    private Double 기준온도Min;
    private Double 기준온도Max;
    private Double 기준습도Max;

    /* =========================
     * 상세 Row (채널별 상태)
     * ========================= */
    private List<TemperatureHumidityStatusTableRowViewDto> rows;
}
