package com.example.demo.domain.equipment.temperaturehumidity.service;

import com.example.demo.domain.equipment.temperaturehumidity.provider.TemperatureHumidityStatusTableProvider;
import com.example.demo.domain.equipment.temperaturehumidity.view.TemperatureHumidityStatusTableRowViewDto;
import com.example.demo.domain.equipment.temperaturehumidity.view.TemperatureHumidityStatusTableViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemperatureHumidityStatusTableServiceImpl
        implements TemperatureHumidityStatusTableService {

    private final TemperatureHumidityStatusTableProvider statusTableProvider;

    @Override
    public TemperatureHumidityStatusTableViewDto getStatusTable() {

        TemperatureHumidityStatusTableViewDto view =
                statusTableProvider.getStatusTable();

        calculateRepresentativeValues(view);

        return view;
    }

    /**
     * 온습도 대표값 계산
     * - online 센서만 대상
     * - max 기준
     */
    private void calculateRepresentativeValues(
            TemperatureHumidityStatusTableViewDto view
    ) {
        List<TemperatureHumidityStatusTableRowViewDto> rows = view.getRows();

        if (rows == null || rows.isEmpty()) {
            return;
        }

        Double temperatureMax = rows.stream()
                .filter(row -> Boolean.TRUE.equals(row.getCommunicationAlive()))
                .map(TemperatureHumidityStatusTableRowViewDto::getTemperature)
                .filter(v -> v != null)
                .max(Double::compareTo)
                .orElse(null);

        Double humidityMax = rows.stream()
                .filter(row -> Boolean.TRUE.equals(row.getCommunicationAlive()))
                .map(TemperatureHumidityStatusTableRowViewDto::getHumidity)
                .filter(v -> v != null)
                .max(Double::compareTo)
                .orElse(null);

        view.setTempMax(temperatureMax);
        view.setHumidityMax(humidityMax);
    }
}
