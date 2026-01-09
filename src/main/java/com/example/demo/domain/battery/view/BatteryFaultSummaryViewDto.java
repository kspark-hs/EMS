package com.example.demo.domain.battery.view;

import lombok.Data;

import java.util.List;

/**
 * 배터리 고장 요약 View DTO
 * - 메모리맵 Raw를 직접 노출하지 않고
 * - 사람이 읽는 요약 메시지만 전달
 */
@Data
public class BatteryFaultSummaryViewDto {

    /** 고장 존재 여부 */
    private boolean hasFault;

    /**
     * 고장 요약 메시지 목록
     * 예)
     * - Additional Protection Fail
     * - Fan Operation Fail
     */
    private List<String> faultMessages;
}

