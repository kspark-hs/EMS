package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.provider.PcsSummaryProvider;
import com.example.demo.domain.pcs.status.PcsAggregateStatusSnapshot;
import com.example.demo.domain.pcs.status.PcsChargeDischargeStatusType;
import com.example.demo.domain.pcs.status.PcsOverallStatusType;
import com.example.demo.domain.pcs.view.PcsSummaryViewDto;
import com.example.demo.model.format.ValueFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PcsSummaryService {

    private final PcsSummaryProvider pcsSummaryProvider;
    private final PcsAggregateStatusService aggregateStatusService;

    /**
     * PCS 1대 분량 Summary 생성
     * - 선택된 PCS 기준
     */
    public PcsSummaryViewDto getSummary(Long pcsId) {

        /* =========================
         * 1️⃣ Snapshot (판결 결과)
         * ========================= */
        PcsAggregateStatusSnapshot snapshot =
                aggregateStatusService.getSnapshot(pcsId);

        /* =========================
         * 2️⃣ 수치 데이터 (Raw Provider)
         * ========================= */
        Double pvActivePowerKw =
                pcsSummaryProvider.getPvActivePowerKw(pcsId);

        Double referenceKw =
                pcsSummaryProvider.getChargeDischargeReferenceKw(pcsId);

        Double pcsActivePowerKw =
                pcsSummaryProvider.getPcsActivePowerKw(pcsId);

        /* =========================
         * 3️⃣ DTO 조립 (표현만)
         * ========================= */
        PcsSummaryViewDto dto = new PcsSummaryViewDto();

        dto.setPcsStatus(
                toPcsStatusLabel(snapshot.getOverallStatus())
        );

        dto.setChargeDischargeStatus(
                toChargeDischargeLabel(snapshot.getChargeDischargeStatus())
        );

        dto.setOperable(snapshot.isOperable());

        dto.setPvActivePowerKw(ValueFormatter.f2(pvActivePowerKw));
        dto.setChargeDischargeReferenceKw(ValueFormatter.f2(referenceKw));
        dto.setPcsActivePowerKw(ValueFormatter.f2(pcsActivePowerKw));

        return dto;
    }

    /**
     * PCS 목록용 Summary (PCS 번호별)
     * - 전체 테이블 / 목록 화면용
     */
    public List<PcsSummaryViewDto> getSummaries(int pcsQuantity) {
        return IntStream.rangeClosed(1, pcsQuantity)
                .mapToObj(i -> getSummary((long) i))
                .toList();
    }

    /**
     * PCS 전체 요약
     * ⚠ STEP 2 (PCS 전체 요약 화면)에서 재구현 예정
     */
    public PcsSummaryViewDto getTotalSummary() {
        // 임시 유지 (호환성 목적)
        return getSummary(1L);
    }

    /* =========================
     * 표현 전용 변환
     * ========================= */

    private String toPcsStatusLabel(PcsOverallStatusType status) {
        return switch (status) {
            case NORMAL     -> "정상";
            case FAULT      -> "고장";
            case STOPPED    -> "정지";
            case COMM_ERROR -> "통신 이상";
        };
    }

    private String toChargeDischargeLabel(PcsChargeDischargeStatusType status) {
        return switch (status) {
            case CHARGING    -> "충전 중";
            case DISCHARGING -> "방전 중";
            case IDLE        -> "대기 중";
            case STOP        -> "정지";
        };
    }
}
