package com.example.demo.domain.equipment.firesystem.provider;

import com.example.demo.domain.equipment.firesystem.view.FireSystemStatusTableViewDto;

/**
 * FireSystem Status Table Provider
 *
 * - UI 카드 1장을 구성하는 Provider
 * - Service는 이 인터페이스만 의존
 */
public interface FireSystemStatusTableProvider {

    FireSystemStatusTableViewDto getStatusTable();
}

