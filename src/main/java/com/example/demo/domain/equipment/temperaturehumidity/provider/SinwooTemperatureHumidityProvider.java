package com.example.demo.domain.equipment.temperaturehumidity.provider;

import org.springframework.stereotype.Component;

/**
 * Sinwoo Temperature / Humidity Raw Provider
 *
 * - 신우시엠 온습도계 메모리맵 기반 Raw 값 제공
 * - 판단 로직 ❌
 * - 기준값 ❌
 * - 상태 해석 ❌
 *
 * ※ PCS / 배터리 Raw Provider와 동일한 책임
 */
@Component
public class SinwooTemperatureHumidityProvider
        implements TemperatureHumidityProvider {

    @Override
    public Double getTemperature(String sensorId, int channel) {
        // TODO: sensorId + channel 기반 실제 통신 연동
        // 예) TH-01, CH1 ~ CH4
        return switch (channel) {
            case 1 -> 25.0;
            case 2 -> 29.5;
            case 3 -> 25.0;
            case 4 -> 25.0;
            default -> null;
        };
    }

    @Override
    public Double getHumidity(String sensorId, int channel) {
        // TODO: sensorId + channel 기반 실제 통신 연동
        return switch (channel) {
            case 1 -> 60.0;
            case 2 -> 60.0;
            case 3 -> 60.0;
            case 4 -> 85.0;
            default -> null;
        };
    }

    @Override
    public boolean isCommunicationAlive(String sensorId, int channel) {
        // TODO: 채널별 통신 상태 비트 연동
        // return channel >= 1 && channel <= 4;

        // 테스트 시나리오:
        // CH3 통신 두절, 나머지 정상
        return channel != 3;
    }
}
