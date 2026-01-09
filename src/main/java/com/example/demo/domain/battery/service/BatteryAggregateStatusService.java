package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.provider.BatteryAggregateStatusRawProvider;
import com.example.demo.domain.battery.status.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatteryAggregateStatusService {

    private final BatteryFaultSnapshotService faultSnapshotService;
    private final BatteryAggregateStatusRawProvider statusRawProvider;

    /**
     * Battery 전체 상태 집계 (rack 단위)
     */
    public BatteryAggregateStatusSnapshot getSnapshot(List<Long> rackIds) {

        /* =========================
         * 0️⃣ Rack Count
         * ========================= */
        int totalRackCount = rackIds.size();
        int faultRackCount = 0;

        /* =========================
         * 1️⃣ Fault Aggregate
         * ========================= */
        for (Long rackId : rackIds) {
            if (faultSnapshotService.getSnapshot(rackId).isHasFault()) {
                faultRackCount++;
            }
        }

        boolean hasFault = faultRackCount > 0;

        /* =========================
         * 2️⃣ Communication Status (Raw)
         * - rack 단위 집계
         * ========================= */
        boolean internalCommOk = true;
        boolean externalCommOk = true;

        for (Long rackId : rackIds) {
            internalCommOk &= statusRawProvider.isInternalCommOk(rackId);
            externalCommOk &= statusRawProvider.isExternalCommOk(rackId);
        }

        // 🔒 통신 판결 단일화
        boolean commOk = internalCommOk && externalCommOk;

        /* =========================
         * 3️⃣ Partial Rack Fault 판단
         * ========================= */
        boolean partialRackFault =
                faultRackCount > 0 && faultRackCount < totalRackCount;

        /* =========================
         * 4️⃣ Overall Status
         * 우선순위: 통신이상 > 전체고장 > 부분고장 > 정상
         * ========================= */
        BatteryOverallStatusType overallStatus =
                !commOk
                        ? BatteryOverallStatusType.COMM_ERROR
                        : faultRackCount == totalRackCount
                        ? BatteryOverallStatusType.FAULT
                        : partialRackFault
                        ? BatteryOverallStatusType.PARTIAL_FAULT
                        : BatteryOverallStatusType.NORMAL;

        /* =========================
         * 5️⃣ Operation 가능 여부
         * ========================= */
        boolean operable =
                overallStatus == BatteryOverallStatusType.NORMAL
                        || overallStatus == BatteryOverallStatusType.PARTIAL_FAULT;

        /* =========================
         * 6️⃣ Charge / Discharge Status
         * (Battery는 아직 Raw 기준 없음 → 고정)
         * ========================= */
        BatteryChargeDischargeStatusType chargeDischargeStatus =
                operable
                        ? BatteryChargeDischargeStatusType.IDLE
                        : BatteryChargeDischargeStatusType.STOP;

        /* =========================
         * 7️⃣ Mode (고정)
         * ========================= */
        BatteryOperationModeType operationMode = BatteryOperationModeType.AUTO;

        /* =========================
         * 8️⃣ Snapshot (최종)
         * ========================= */
        return new BatteryAggregateStatusSnapshot(
                overallStatus,
                chargeDischargeStatus,
                operationMode,
                internalCommOk,
                externalCommOk,
                totalRackCount,
                faultRackCount,
                partialRackFault,
                operable
        );
    }
}
