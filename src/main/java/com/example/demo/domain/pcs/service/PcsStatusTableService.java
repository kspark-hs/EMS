package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.view.PcsStatusTableRowViewDto;
import com.example.demo.model.format.ValueFormatter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * PCS 상태 테이블 Row 생성 전용 Service
 * - Raw → View DTO 변환
 * - 소수점 포맷 책임 여기서 종료
 */
@Service
public class PcsStatusTableService {

    /**
     * PCS 상태 테이블 Row 목록
     */
    public List<PcsStatusTableRowViewDto> getStatusRows() {

        List<PcsStatusTableRowViewDto> rows = new ArrayList<>();

        /* =========================
         * PCS #1
         * ========================= */
        PcsStatusTableRowViewDto pcs1 = new PcsStatusTableRowViewDto();
        pcs1.setPcsNo(1);
        pcs1.setPcsName("PCS #1");
        pcs1.setStatus("NORMAL");

        pcs1.setDcVoltage(ValueFormatter.f1(821.6));
        pcs1.setDcCurrent(ValueFormatter.f1(42.1));
        pcs1.setDcPower(ValueFormatter.f1(0.3));

        pcs1.setAcLineVoltage(ValueFormatter.f1(462.5));
        pcs1.setAcCurrent(ValueFormatter.f1(24.0));

        pcs1.setActivePower(ValueFormatter.f1(0.0));
        pcs1.setFrequency(ValueFormatter.f0(60));

        rows.add(pcs1);

        /* =========================
         * PCS #2
         * ========================= */
        PcsStatusTableRowViewDto pcs2 = new PcsStatusTableRowViewDto();
        pcs2.setPcsNo(2);
        pcs2.setPcsName("PCS #2");
        pcs2.setStatus("FAULT");

        pcs2.setDcVoltage(ValueFormatter.f1(0.0));
        pcs2.setDcCurrent(ValueFormatter.f1(0.0));
        pcs2.setDcPower(ValueFormatter.f1(0.0));

        pcs2.setAcLineVoltage(ValueFormatter.f1(0.0));
        pcs2.setAcCurrent(ValueFormatter.f1(0.0));

        pcs2.setActivePower(ValueFormatter.f1(0.0));
        pcs2.setFrequency(ValueFormatter.f0(0));

        rows.add(pcs2);

        return rows;
    }

    /**
     * 화면에 표시할 PCS 개수
     * - 상태 테이블 Row 기준
     * - 향후 DB 연결 시에도 의미 유지
     */
    public int getRunningCount() {
        return getStatusRows().size();
    }
}
