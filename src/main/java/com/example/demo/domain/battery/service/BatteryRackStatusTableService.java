package com.example.demo.domain.battery.service;

import com.example.demo.domain.battery.provider.BatteryRackStatusProvider;
import com.example.demo.domain.battery.view.BatteryRackStatusTableRowViewDto;
import com.example.demo.domain.battery.view.BatteryRackStatusTableViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Battery Rack 상태 테이블 Service
 *
 * 책임:
 * 1. rackId 목록 기반 상태 조회
 * 2. UI 표시용 Row ViewDto 생성
 * 3. Table ViewDto 조립
 *
 * ※ PCS StatusTableService와 1:1 구조
 * ※ 운전 상태 판단 / 정책 로직 ❌
 */
@Service
@RequiredArgsConstructor
public class BatteryRackStatusTableService {

    private final BatteryRackStatusProvider rackStatusProvider;

    /**
     * Rack 상태 테이블 조회 (UI 전용)
     */
    public BatteryRackStatusTableViewDto getRackStatusTable(List<Long> rackIds) {

        List<BatteryRackStatusTableRowViewDto> rows = new ArrayList<>();

        for (Long rackId : rackIds) {

            rows.add(
                    BatteryRackStatusTableRowViewDto.from(
                            rackId,
                            rackStatusProvider.getHealthStatus(rackId),
                            rackStatusProvider.isInternalCommOk(rackId)
                    )
            );
        }

        return new BatteryRackStatusTableViewDto(rows);
    }
}
