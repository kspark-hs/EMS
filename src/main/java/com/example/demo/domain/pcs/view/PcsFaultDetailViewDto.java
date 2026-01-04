package com.example.demo.domain.pcs.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PcsFaultDetailViewDto {

    /** 화면에 찍을 이름 (예: "IGBT Fault", "DC CB1 Trip") */
    private String label;

    /** 고장 여부 (true=고장, false=정상) */
    private boolean fault;

    /** 통합 그룹키 (예: "IGBT_FAULT") - 없으면 null 가능 */
    private String groupKey;
}
