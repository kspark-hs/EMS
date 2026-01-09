package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.status.PcsAggregateStatusSnapshot;
import com.example.demo.domain.pcs.status.PcsOverallStatusType;
import com.example.demo.domain.pcs.view.PcsStatusTableRowViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PcsStatusTableService {

    private final PcsAggregateStatusService aggregateStatusService;

    public List<PcsStatusTableRowViewDto> getRows(int pcsCount) {

        List<PcsStatusTableRowViewDto> rows = new ArrayList<>();

        for (int pcsNo = 1; pcsNo <= pcsCount; pcsNo++) {
            rows.add(buildRow(pcsNo));
        }

        return rows;
    }

    private PcsStatusTableRowViewDto buildRow(int pcsNo) {

        Long pcsId = (long) pcsNo;

        PcsAggregateStatusSnapshot s =
                aggregateStatusService.getSnapshot(pcsId);

        PcsStatusTableRowViewDto r = new PcsStatusTableRowViewDto();
        r.setPcsNo(pcsNo);
        r.setPcsName("PCS #" + pcsNo);

        /* =========================
         * 상태 / 통신 (Service 한글화)
         * ========================= */
        r.setStatus(
                switch (s.getOverallStatus()) {
                    case FAULT      -> "고장";
                    case COMM_ERROR -> "통신 이상";
                    default         -> "정상";
                }
        );

        // 내부 통신 컬럼
        r.setInternalCommStatus(
                s.isInternalCommOk() ? "정상" : "두절"
        );

        // 외부 통신 컬럼
        r.setExternalCommStatus(
                s.isExternalCommOk() ? "정상" : "두절"
        );

        /* =========================
         * 수치 컬럼 (샘플값)
         * ========================= */
        r.setDcVoltage(821.6);
        r.setDcCurrent(42.1);
        r.setDcPower(0.3);

        r.setAcLineVoltage(462.5);
        r.setAcCurrent(24.0);
        r.setActivePower(0.0);
        r.setFrequency(60.0);

        return r;
    }
}
