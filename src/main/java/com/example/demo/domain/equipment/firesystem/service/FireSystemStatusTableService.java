package com.example.demo.domain.equipment.firesystem.service;

import com.example.demo.domain.equipment.firesystem.view.FireSystemStatusTableViewDto;

/**
 * FireSystem Status Table Service
 *
 * - Controller 전용 진입점
 * - 내부 구현(Provider)을 숨긴다
 */
public interface FireSystemStatusTableService {

    /**
     * 소방설비 상태 테이블 조회
     */
    FireSystemStatusTableViewDto getStatusTable();
}

