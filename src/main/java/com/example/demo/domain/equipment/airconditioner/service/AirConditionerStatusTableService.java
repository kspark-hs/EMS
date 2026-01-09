package com.example.demo.domain.equipment.airconditioner.service;

import com.example.demo.domain.equipment.airconditioner.view.AirConditionerStatusTableViewDto;

/**
 * AirConditioner Status Table Service
 *
 * - StatusTable UI 전용 Service
 * - Provider 결과를 ViewDto로 변환
 */
public interface AirConditionerStatusTableService {

    /**
     * 에어컨 상태 테이블 정보 조회 (온습도 미연결)
     *
     * @param airConditionerId 에어컨 식별자
     */
    AirConditionerStatusTableViewDto getStatusTable(
            Long airConditionerId
    );

    /**
     * 에어컨 상태 테이블 정보 조회 (온습도 대표값 연동)
     *
     * @param airConditionerId 에어컨 식별자
     * @param temperatureMax  대표 온도
     * @param humidityMax     대표 습도
     */
    AirConditionerStatusTableViewDto getStatusTable(
            Long airConditionerId,
            Double temperatureMax,
            Double humidityMax
    );
}
