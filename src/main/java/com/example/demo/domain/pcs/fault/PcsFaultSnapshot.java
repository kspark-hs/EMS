package com.example.demo.domain.pcs.fault;

import com.example.demo.domain.pcs.status.PcsFaultType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

/**
 * PCS Fault Snapshot
 *
 * - 특정 시점의 PCS 고장 상태 결과물
 * - 판단 로직 없음 (결과만 보유)
 * - UI / Service / DB 공통 기준 객체
 */
@Getter
public class PcsFaultSnapshot {

    /** PCS 식별자 */
    private final Long pcsId;

    /** 고장 비트 상태 맵 (Raw 결과) */
    private final Map<PcsFaultType, Boolean> faultMap;

    /** PCS 내부 고장 존재 여부 (INTERLOCK 제외) */
    private final boolean hasFault;

    /** 인터록 발생 여부
     *  true  = 인터록 발생 (운전 차단)
     *  false = 인터록 해제
     */
    private final boolean interlockActive;

    /** 스냅샷 생성 시각 */
    private final LocalDateTime snapshotAt;

    public PcsFaultSnapshot(
            Long pcsId,
            Map<PcsFaultType, Boolean> faultMap
    ) {
        this.pcsId = pcsId;
        this.faultMap = new EnumMap<>(faultMap);

        // ✅ 인터록 여부 단독 계산
        this.interlockActive =
                Boolean.TRUE.equals(faultMap.get(PcsFaultType.INTERLOCK));

        // ✅ PCS 내부 Fault만으로 고장 여부 판단
        this.hasFault = faultMap.entrySet().stream()
                .filter(e -> e.getKey() != PcsFaultType.INTERLOCK)
                .anyMatch(e -> Boolean.TRUE.equals(e.getValue()));

        this.snapshotAt = LocalDateTime.now();
    }
}
