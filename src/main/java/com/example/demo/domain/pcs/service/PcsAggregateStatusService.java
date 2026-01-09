package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.fault.PcsFaultSnapshotService;
import com.example.demo.domain.pcs.provider.PcsAggregateStatusRawProvider;
import com.example.demo.domain.pcs.status.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PcsAggregateStatusService {

    private final PcsFaultSnapshotService faultSnapshotService;
    private final PcsAggregateStatusRawProvider statusRawProvider;

    public PcsAggregateStatusSnapshot getSnapshot(Long pcsId) {

        /* =========================
         * 1️⃣ Fault Snapshot
         * ========================= */
        var fault = faultSnapshotService.getSnapshot(pcsId);
        boolean hasFault = fault.isHasFault();

        /* =========================
         * 2️⃣ Communication Status (Raw)
         * ========================= */
        boolean internalCommOk = statusRawProvider.isInternalCommOk(pcsId);
        boolean externalCommOk = statusRawProvider.isExternalCommOk(pcsId);

        // 🔒 통신 판결은 여기서만 단일화(로컬 변수로만)
        boolean commOk = internalCommOk && externalCommOk;

        /* =========================
         * 3️⃣ Overall Status (단일 판결)
         * 우선순위: 통신이상 > 고장 > 정상
         * ========================= */
        PcsOverallStatusType overallStatus =
                !commOk
                        ? PcsOverallStatusType.COMM_ERROR
                        : hasFault
                        ? PcsOverallStatusType.FAULT
                        : PcsOverallStatusType.NORMAL;

        /* =========================
         * 4️⃣ Operation 가능 여부
         * ========================= */
        boolean operable = (overallStatus == PcsOverallStatusType.NORMAL);

        /* =========================
         * 5️⃣ Charge / Discharge Status
         * ========================= */
        boolean running     = statusRawProvider.isRunning(pcsId);
        boolean charging    = statusRawProvider.isCharging(pcsId);
        boolean discharging = statusRawProvider.isDischarging(pcsId);

        PcsChargeDischargeStatusType chargeDischargeStatus =
                !operable
                        ? PcsChargeDischargeStatusType.STOP
                        : charging
                        ? PcsChargeDischargeStatusType.CHARGING
                        : discharging
                        ? PcsChargeDischargeStatusType.DISCHARGING
                        : running
                        ? PcsChargeDischargeStatusType.IDLE
                        : PcsChargeDischargeStatusType.STOP;

        /* =========================
         * 6️⃣ Mode (고정)
         * ========================= */
        PcsOperationModeType operationMode = PcsOperationModeType.MANUAL;
        PcsControlModeType controlMode     = PcsControlModeType.REMOTE;

        /* =========================
         * 7️⃣ Snapshot (🔥 최종 - 7개 생성자)
         * ========================= */
        return new PcsAggregateStatusSnapshot(
                overallStatus,
                chargeDischargeStatus,
                operationMode,
                controlMode,
                internalCommOk,
                externalCommOk,
                operable
        );
    }
}
