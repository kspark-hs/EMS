package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.provider.PcsFaultDetailProvider;
import com.example.demo.domain.pcs.status.PcsFaultType;
import com.example.demo.domain.pcs.view.PcsFaultDetailViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PcsFaultDetailService {

    private final PcsFaultDetailProvider provider;

    public List<PcsFaultDetailViewDto> getFaultDetails(Long pcsId) {

        Map<PcsFaultType, Boolean> faultMap =
                provider.getFaultStatus(pcsId);

        List<PcsFaultDetailViewDto> result = new ArrayList<>();

        for (PcsFaultType type : PcsFaultType.values()) {
            boolean fault = faultMap.getOrDefault(type, false);

            result.add(
                    new PcsFaultDetailViewDto(
                            type.getLabel(),      // 화면 표시명
                            fault,                // 고장 여부
                            type.getGroupKey()    // IGBT 등 그룹 키 (없으면 null)
                    )
            );
        }

        return result;
    }
}

