package com.example.demo.domain.equipment.temperaturehumidity.provider;

import com.example.demo.domain.equipment.temperaturehumidity.status.TemperatureHumidityHealthStatusType;
import com.example.demo.domain.equipment.temperaturehumidity.view.TemperatureHumidityStatusTableRowViewDto;
import com.example.demo.domain.equipment.temperaturehumidity.view.TemperatureHumidityStatusTableViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sinwoo Temperature / Humidity Status Table Provider
 *
 * - Sinwoo 온습도계 전용 StatusTable 구현
 * - Raw Provider 결과를 기반으로
 *   UI 카드 1장을 구성하는 StatusTableViewDto 생성
 */
@Component
@RequiredArgsConstructor
public class SinwooTemperatureHumidityStatusTableProvider
        implements TemperatureHumidityStatusTableProvider {

    private final TemperatureHumidityProvider temperatureHumidityProvider;

    @Override
    public TemperatureHumidityStatusTableViewDto getStatusTable() {

        /* =========================
         * 1. 기준값 (관리자 설정 예정)
         * ========================= */
        double 기준온도Min = 18.0;
        double 기준온도Max = 28.0;
        double 기준습도Max = 80.0;

        /* =========================
         * 2. 채널별 Row 생성 (CH 1 ~ CH 4)
         * ========================= */
        String sensorId = "TH-01";
        int channelCount = 4;

        List<TemperatureHumidityStatusTableRowViewDto> rows =
                new java.util.ArrayList<>();

        for (int ch = 1; ch <= channelCount; ch++) {

            String sensorName = "CH " + ch;

            Double temperature =
                    temperatureHumidityProvider.getTemperature(sensorId, ch);
            Double humidity =
                    temperatureHumidityProvider.getHumidity(sensorId, ch);

            boolean communicationAlive =
                    temperatureHumidityProvider.isCommunicationAlive(sensorId, ch);

            TemperatureHumidityHealthStatusType status;

            if (!communicationAlive) {
                status = TemperatureHumidityHealthStatusType.DISCONNECTED;
            } else if (
                    temperature < 기준온도Min
                            || temperature > 기준온도Max
                            || humidity > 기준습도Max
            ) {
                status = TemperatureHumidityHealthStatusType.WARNING;
            } else {
                status = TemperatureHumidityHealthStatusType.NORMAL;
            }


            String statusText;

            if (!communicationAlive) {
                statusText = "판단 불가";
            } else if (temperature < 기준온도Min) {
                statusText = "저온";
            } else if (temperature > 기준온도Max) {
                statusText = "고온";
            } else if (humidity > 기준습도Max) {
                statusText = "과습";
            } else {
                statusText = "정상";
            }


            TemperatureHumidityStatusTableRowViewDto row =
                    new TemperatureHumidityStatusTableRowViewDto(
                            sensorId,
                            sensorName,
                            communicationAlive,
                            status,
                            statusText,
                            temperature,
                            temperature,   // min (더미)
                            temperature,   // max (더미)
                            humidity,
                            humidity       // max (더미)
                    );

            rows.add(row);
        }


        /* =========================
         * 3. 전체 채널 기준 종합 판단
         * ========================= */
        boolean anyChannelAlive = false;
        boolean anyOutOfRange = false;

        Double minTemperature = null;
        Double maxTemperature = null;
        Double maxHumidity = null;

        for (TemperatureHumidityStatusTableRowViewDto row : rows) {

            if (!row.getCommunicationAlive()) {
                continue; // 판단 불가 채널
            }

            anyChannelAlive = true;

            Double t = row.getTemperature();
            Double h = row.getHumidity();

            // 온도 최소 / 최대
            if (minTemperature == null || t < minTemperature) {
                minTemperature = t;
            }
            if (maxTemperature == null || t > maxTemperature) {
                maxTemperature = t;
            }

            // 습도 최대
            if (maxHumidity == null || h > maxHumidity) {
                maxHumidity = h;
            }

            // 기준 초과 여부
            boolean temperatureOut =
                    t < 기준온도Min || t > 기준온도Max;

            boolean humidityOut =
                    h > 기준습도Max;

            if (temperatureOut || humidityOut) {
                anyOutOfRange = true;
            }
        }


        boolean operable;
        TemperatureHumidityHealthStatusType overallStatus;
        String overallStatusText;

        if (!anyChannelAlive) {
            operable = false;
            overallStatus = TemperatureHumidityHealthStatusType.DISCONNECTED;
            overallStatusText = "환경 정보 수신 불가";
        } else if (anyOutOfRange) {
            operable = false;
            overallStatus = TemperatureHumidityHealthStatusType.WARNING;
            overallStatusText = "환경 이상 (충·방전 정지)";
        } else {
            operable = true;
            overallStatus = TemperatureHumidityHealthStatusType.NORMAL;
            overallStatusText = "정상";
        }


        /* =========================
         * 대표 표시 값 (상단 카드용)
         * ========================= */
        Double representativeTemperature = null;
        Double representativeHumidity = null;

        if (anyChannelAlive) {
            representativeTemperature = maxTemperature; // 위험 기준 → 최대 온도
            representativeHumidity = maxHumidity;       // 습도는 최대값
        }



        String operableText =
                operable ? "충방전 운전가능" : "충방전 운전불가";


        /* =========================
         * 4. StatusTableViewDto 조립
         * ========================= */
        TemperatureHumidityStatusTableViewDto table =
                new TemperatureHumidityStatusTableViewDto();

        /* rows는 그대로 */
        table.setRows(rows);

        /* 🔴 여기부터 추가 / 수정 */
        table.setOverallStatus(overallStatus);
        table.setOverallStatusText(overallStatusText);

        table.setOperable(operable);
        table.setOperableText(
                operable ? "충방전 운전가능" : "충방전 운전정지"
        );

        /* 🔴 대표 온습도 값 설정 (상단 카드 표시용) */
        table.setTempMin(minTemperature);
        table.setTempMax(maxTemperature);
        table.setHumidityMax(maxHumidity);


        /* 기준값 */
        table.set기준온도Min(기준온도Min);
        table.set기준온도Max(기준온도Max);
        table.set기준습도Max(기준습도Max);

        return table;

    }
}
