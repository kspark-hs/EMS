package com.example.demo.domain.equipment.temperaturehumidity.provider;

/**
 * Temperature / Humidity Raw Provider
 *
 * - 장비 / RTU / DB에서 "있는 그대로" 값만 제공
 * - 상태 판단 ❌
 * - 기준값 ❌
 * - UI 의미 ❌
 *
 * ※ PCS의 Raw Provider들과 동일한 책임 모델
 */
public interface TemperatureHumidityProvider {

    /**
     * 온도 조회 (채널별)
     *
     * @param sensorId 온습도계 ID (TH-01, TH-02 ...)
     * @param channel  채널 번호 (1 ~ 4)
     * @return 온도(℃), null = 통신/신호 이상
     */
    Double getTemperature(String sensorId, int channel);

    /**
     * 습도 조회 (채널별)
     *
     * @param sensorId 온습도계 ID (TH-01, TH-02 ...)
     * @param channel  채널 번호 (1 ~ 4)
     * @return 습도(%), null = 통신/신호 이상
     */
    Double getHumidity(String sensorId, int channel);

    /**
     * 통신 상태 조회 (채널별)
     *
     * @param sensorId 온습도계 ID
     * @param channel  채널 번호
     * @return true = 통신 정상, false = 통신 단절
     */
    boolean isCommunicationAlive(String sensorId, int channel);
}
