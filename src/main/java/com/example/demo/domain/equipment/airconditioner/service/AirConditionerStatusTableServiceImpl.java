package com.example.demo.domain.equipment.airconditioner.service;

import com.example.demo.domain.equipment.airconditioner.dto.AirConditionerSummaryDto;
import com.example.demo.domain.equipment.airconditioner.provider.AirConditionerStatusTableProvider;
import com.example.demo.domain.equipment.airconditioner.status.ControlMode;
import com.example.demo.domain.equipment.airconditioner.status.OperationMode;
import com.example.demo.domain.equipment.airconditioner.view.AirConditionerDetailRowViewDto;
import com.example.demo.domain.equipment.airconditioner.view.AirConditionerStatusTableViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirConditionerStatusTableServiceImpl
        implements AirConditionerStatusTableService {

    private final AirConditionerStatusTableProvider statusTableProvider;

    /* =================================================
     * 기존 호출 (온습도 대표값 없는 경우)
     * ================================================= */
    @Override
    public AirConditionerStatusTableViewDto getStatusTable(Long airConditionerId) {
        return getStatusTableInternal(airConditionerId, null, null);
    }

    /* =================================================
     * 신규 호출 (온습도 대표값 전달)
     * ================================================= */
    @Override
    public AirConditionerStatusTableViewDto getStatusTable(
            Long airConditionerId,
            Double temperatureMax,
            Double humidityMax
    ) {
        return getStatusTableInternal(airConditionerId, temperatureMax, humidityMax);
    }

    /* =================================================
     * 실제 구현부 (단일 책임)
     * ================================================= */
    private AirConditionerStatusTableViewDto getStatusTableInternal(
            Long airConditionerId,
            Double temperatureMax,
            Double humidityMax
    ) {

        AirConditionerStatusTableViewDto view =
                new AirConditionerStatusTableViewDto();

        List<AirConditionerDetailRowViewDto> rows = new ArrayList<>();

        List<AirConditionerSummaryDto> summaries =
                statusTableProvider.getStatusSummaries();

        int displayIndex = 1;

        for (AirConditionerSummaryDto summary : summaries) {

            AirConditionerDetailRowViewDto row =
                    new AirConditionerDetailRowViewDto();

            /* =========================
             * ID (표시용)
             * ========================= */
            row.setDisplayId(String.valueOf(displayIndex++));

            /* =========================
             * 상태
             * ========================= */
            row.setHealthStatus(summary.getHealthStatus());
            row.setHealthStatusText(
                    switch (summary.getHealthStatus()) {
                        case NORMAL -> "정상";
                        case WARNING -> "경고";
                        case FAULT -> "고장";
                        case DISCONNECTED -> "단절";
                    }
            );
            row.setHealthStatusClass(
                    switch (summary.getHealthStatus()) {
                        case NORMAL -> "text-green-600";
                        case WARNING -> "text-yellow-500";
                        case FAULT -> "text-red-600";
                        case DISCONNECTED -> "text-gray-500";
                    }
            );

            /* =========================
             * 제어 요약
             * ========================= */
            row.setControlSummaryText(buildControlSummaryText(summary));
            row.setControlSummaryHtml(buildControlSummaryHtml(summary));

            /* =========================
             * 운전 요약
             * ========================= */
            row.setOperationSummaryText(buildOperationSummary(summary));

            /* =========================
             * 통신
             * ========================= */
            row.setOnline(summary.isOnline());

            /* =========================
             * AUTO 제어 보조 문구 (핵심)
             * ========================= */
            if (isAutoControl(summary.getControlMode(), summary.isOnline())) {
                row.setControlSubText(
                        buildAutoControlSubText(
                                summary.getOperationMode(),
                                temperatureMax,
                                humidityMax
                        )
                );
            } else {
                row.setControlSubText(null);
            }

            rows.add(row);
        }

        view.setRows(rows);

        /* 🔹 전체 운전 요약 문구 설정 */
        view.setOverallOperationSummaryText(
                buildOverallOperationSummary(rows)
        );

        return view;
    }

    /* =================================================
     * Helper Methods
     * ================================================= */

    private String buildControlSummaryText(AirConditionerSummaryDto summary) {

        if (!summary.isOnline()) {
            return "-";
        }

        if (summary.getControlMode() == ControlMode.MANUAL) {
            return "수동";
        }

        return "자동(온습도)";
    }

    private String buildControlSummaryHtml(AirConditionerSummaryDto summary) {

        if (!summary.isOnline()) {
            return "-";
        }

        if (summary.getControlMode() == ControlMode.MANUAL) {
            return "수동";
        }

        return "자동<br/>(온습도)";
    }

    private String buildOperationSummary(AirConditionerSummaryDto summary) {

        if (!summary.isOnline()) {
            return "-";
        }

        if (!summary.isRunning()) {
            return "정지";
        }

        String modeText = switch (summary.getOperationMode()) {
            case COOL -> "냉방";
            case HEAT -> "난방";
            case FAN  -> "송풍";
            case DRY  -> "제습";
            case STOP -> "정지";
        };

        String tempText =
                (summary.getOperationMode() == OperationMode.FAN
                        || summary.getSetTemperature() == null)
                        ? "-"
                        : summary.getSetTemperature() + "℃";

        String fanText = switch (summary.getFanSpeed()) {
            case LOW    -> "약풍";
            case MEDIUM -> "중풍";
            case HIGH   -> "강풍";
            default    -> "-";
        };

        return modeText + " / " + tempText + " / " + fanText;
    }

    private boolean isAutoControl(ControlMode controlMode, boolean online) {
        return controlMode == ControlMode.AUTO && online;
    }

    private String buildAutoControlSubText(
            OperationMode operationMode,
            Double temperatureMax,
            Double humidityMax
    ) {
        if (temperatureMax != null && temperatureMax >= 27.0) {
            return String.format("온도 %.1f℃ → 냉방 제어 중", temperatureMax);
        }

        if (humidityMax != null && humidityMax >= 75.0) {
            return String.format("습도 %.0f%% → 제습 제어 중", humidityMax);
        }

        return "정상 범위 → 대기";
    }

    /* 🔽 여기 바로 아래에 추가 */
    private String buildOverallOperationSummary(
            List<AirConditionerDetailRowViewDto> rows
    ) {
        if (rows == null || rows.isEmpty()) {
            return null;
        }

        int cool = 0;
        int heat = 0;
        int fan  = 0;
        int dry  = 0;

        for (AirConditionerDetailRowViewDto row : rows) {
            if (!row.isOnline()) {
                continue;
            }

            String op = row.getOperationSummaryText();
            if (op == null) {
                continue;
            }

            if (op.startsWith("냉방")) cool++;
            else if (op.startsWith("난방")) heat++;
            else if (op.startsWith("송풍")) fan++;
            else if (op.startsWith("제습")) dry++;
        }

        List<String> parts = new ArrayList<>();
        if (cool > 0) parts.add("냉방 " + cool + "대");
        if (heat > 0) parts.add("난방 " + heat + "대");
        if (fan  > 0) parts.add("송풍 " + fan + "대");
        if (dry  > 0) parts.add("제습 " + dry + "대");

        if (parts.isEmpty()) {
            return null;
        }

        if (parts.size() == 1) {
            return parts.get(0) + " 운전 중";
        }

        return String.join(" · ", parts) + " 혼합 운전 중";
    }


}
