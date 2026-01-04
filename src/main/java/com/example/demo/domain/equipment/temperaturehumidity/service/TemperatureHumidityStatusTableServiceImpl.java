package com.example.demo.domain.equipment.temperaturehumidity.service;

import com.example.demo.domain.equipment.temperaturehumidity.provider.TemperatureHumidityStatusTableProvider;
import com.example.demo.domain.equipment.temperaturehumidity.view.TemperatureHumidityStatusTableViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemperatureHumidityStatusTableServiceImpl
        implements TemperatureHumidityStatusTableService {

    private final TemperatureHumidityStatusTableProvider statusTableProvider;

    @Override
    public TemperatureHumidityStatusTableViewDto getStatusTable() {
        return statusTableProvider.getStatusTable();
    }
}

