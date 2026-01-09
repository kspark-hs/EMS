package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.status.*;
import com.example.demo.domain.pcs.view.PcsOperationStatusViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * PCS 운전 상태 카드 Service
 *
 * 책임:
 * - Aggregate Snapshot 기반 ViewDto 생성
 * - 상태 해석(운전/정지/고장)만 수행
 * - enum 값은 그대로 View로 전달
 */
@Service
@RequiredArgsConstructor
public class PcsOperationStatusService {

    private final PcsAggregateStatusService aggregateStatusService;

    public PcsOperationStatusViewDto getOperationStatus(Long pcsId) {

        /* =========================
         * 1️⃣ Aggregate Snapshot
         * ========================= */
        PcsAggregateStatusSnapshot snapshot =
                aggregateStatusService.getSnapshot(pcsId);

        /* =========================
         * 2️⃣ 동작 상태 (문자열 표현)
         * ========================= */
        String operationState =
                switch (snapshot.getOverallStatus()) {
                    case FAULT      -> "고장";
                    case COMM_ERROR -> "통신 이상";
                    case STOPPED    -> "정지";
                    case NORMAL     -> snapshot.isOperable() ? "운전" : "정지";
                };

        /* =========================
         * 3️⃣ ViewDto 생성
         * ========================= */
        PcsOperationStatusViewDto dto = new PcsOperationStatusViewDto();

        dto.setOperationState(operationState);
        dto.setOperationAvailable(snapshot.isOperable());

        // ✅ enum 그대로 전달 (절대 변환하지 않음)
        dto.setOperationMode(snapshot.getOperationMode());
        dto.setControlMode(snapshot.getControlMode());

        return dto;
    }
}
