package com.example.demo.domain.equipment.firesystem.service;

import com.example.demo.domain.equipment.firesystem.provider.FireSystemStatusTableProvider;
import com.example.demo.domain.equipment.firesystem.view.FireSystemStatusTableViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * FireSystem Status Table Service 구현체
 *
 * - 로직 없음
 * - Provider 위임만 수행
 */
@Service
@RequiredArgsConstructor
public class FireSystemStatusTableServiceImpl
        implements FireSystemStatusTableService {

    private final FireSystemStatusTableProvider statusTableProvider;

    @Override
    public FireSystemStatusTableViewDto getStatusTable() {
        return statusTableProvider.getStatusTable();
    }
}

