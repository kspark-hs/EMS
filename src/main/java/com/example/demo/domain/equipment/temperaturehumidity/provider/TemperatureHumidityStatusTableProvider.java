package com.example.demo.domain.equipment.temperaturehumidity.provider;

import com.example.demo.domain.equipment.temperaturehumidity.view.TemperatureHumidityStatusTableViewDto;

/**
 * Temperature / Humidity Status Table Provider
 *
 * - UI 카드 1장을 구성하는 Provider
 * - Raw Provider 결과를 집계하여 StatusTableViewDto 반환
 */
public interface TemperatureHumidityStatusTableProvider {
    TemperatureHumidityStatusTableViewDto getStatusTable();
}

