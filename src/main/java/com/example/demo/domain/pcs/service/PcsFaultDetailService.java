package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.fault.PcsFaultSnapshot;
import com.example.demo.domain.pcs.fault.PcsFaultSnapshotService;
import com.example.demo.domain.pcs.status.PcsFaultType;
import com.example.demo.domain.pcs.view.PcsFaultDetailViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PCS Fault Detail Service
 *
 * 역할:
 * - PCS 고장 상세 화면용 DTO 생성
 *
 * 원칙:
 * - ❌ 고장 판단 로직 금지
 * - ❌ Provider 직접 호출 금지
 * - ✅ Snapshot 결과만 View로 변환
 */
@Service
@RequiredArgsConstructor
public class PcsFaultDetailService {

    private final PcsFaultSnapshotService snapshotService;

    public List<PcsFaultDetailViewDto> getFaultItems(Long pcsId) {

        PcsFaultSnapshot snapshot =
                snapshotService.getSnapshot(pcsId);

        return snapshot.getFaultMap()
                .entrySet()
                .stream()
                .map(entry -> toDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Snapshot 결과 → 화면 DTO 변환
     */
    private PcsFaultDetailViewDto toDto(PcsFaultType faultType, Boolean fault) {

        return new PcsFaultDetailViewDto(
                faultType.getLabel(),          // 화면 표시용 이름
                Boolean.TRUE.equals(fault),    // 고장 여부
                faultType.name()               // 그룹키 (통합 기준)
        );
    }
}
