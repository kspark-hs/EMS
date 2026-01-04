package com.example.demo.domain.equipment.airconditioner.service;

import com.example.demo.domain.equipment.airconditioner.view.AirConditionerViewDto;

import java.util.List;

public interface AirConditionerService {

    /**
     * 에어컨 상태 조회 (UI용)
     *
     * - AUTO / MANUAL 포함
     * - 운전 모드, 설정 온도, 풍량 등
     * - 충방전 판단과는 직접 관계 없음
     */
    AirConditionerViewDto getView(String airConditionerId);

    /**
     * 에어컨 목록 조회 (select용)
     *
     * - 다중 에어컨 지원
     * - UI select와 1:1 대응
     */
    List<AirConditionerViewDto> getAirConditionerList();

    /**
     * 자동 제어 판단 및 실행
     *
     * - AUTO 모드일 때만 동작
     * - 온습도계 대표값을 참조
     * - 제어 명령은 Provider를 통해 수행
     *
     * ※ UI 요청과 분리된 주기성 로직
     */
    void autoControlIfNeeded(String airConditionerId);
}

