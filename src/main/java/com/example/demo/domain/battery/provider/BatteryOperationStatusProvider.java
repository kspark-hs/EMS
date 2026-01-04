package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.dto.BatteryOperationStatusRawDto;

/**
 * 배터리 운전 상태 Provider (원본 데이터 전용)
 *
 * - 장비 / DB에서 원본 값만 제공
 * - 판단 / 정책 / enum 변환 금지
 */
public interface BatteryOperationStatusProvider {

    BatteryOperationStatusRawDto getOperationStatus(Long batteryId);
}


