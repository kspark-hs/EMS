package com.example.demo.domain.pcs.fault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * PCS Fault Snapshot Service
 *
 * - 화면 / 상위 도메인에서 사용하는 유일한 진입점
 * - 판단 로직 없음
 */
@Service
@RequiredArgsConstructor
public class PcsFaultSnapshotService {

    private final PcsFaultSnapshotAssembler assembler;

    public PcsFaultSnapshot getSnapshot(Long pcsId) {
        return assembler.assemble(pcsId);
    }
}

