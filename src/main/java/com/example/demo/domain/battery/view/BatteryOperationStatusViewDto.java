package com.example.demo.domain.battery.view;

import com.example.demo.domain.battery.status.ActionStatus;
import com.example.demo.domain.battery.status.BatteryOperationModeType;
import com.example.demo.domain.battery.status.BatteryOperationStatusType;
import lombok.Data;

@Data
public class BatteryOperationStatusViewDto {

    /** 동작 상태 (충전/방전/대기/정지) */
    private ActionStatus actionStatus;

    /** 운전 상태 (정상/파셜/제한/점검) */
    private BatteryOperationStatusType operationStatus;

    /** 운전 모드 (AUTO / MANUAL) */
    private BatteryOperationModeType operationMode;

    /** 보조 설명 */
    private String description;

    /* =========================
     * UI 표시용
     * ========================= */
    public String getActionStatusLabel() {
        return actionStatus != null ? actionStatus.getLabel() : "-";
    }

    public String getOperationStatusLabel() {
        return operationStatus != null ? operationStatus.getLabel() : "-";
    }

    public String getOperationModeLabel() {
        return operationMode != null ? operationMode.getLabel() : "-";
    }
}

