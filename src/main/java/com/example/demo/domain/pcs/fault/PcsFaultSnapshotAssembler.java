package com.example.demo.domain.pcs.fault;

import com.example.demo.domain.pcs.provider.PcsFaultProvider;
import com.example.demo.domain.pcs.status.PcsFaultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * PCS Fault Snapshot Assembler
 *
 * - Provider의 Raw Fault 데이터를
 * - "판단 완료된 Snapshot"으로 변환
 *
 * ⚠️ 고장 판단은 여기서 종료
 */
@Component
@RequiredArgsConstructor
public class PcsFaultSnapshotAssembler {

    private final PcsFaultProvider pcsFaultProvider;

    public PcsFaultSnapshot assemble(Long pcsId) {

        Map<PcsFaultType, Boolean> rawFaultMap =
                pcsFaultProvider.getRawFaultMap(pcsId);

        return new PcsFaultSnapshot(pcsId, rawFaultMap);
    }
}

