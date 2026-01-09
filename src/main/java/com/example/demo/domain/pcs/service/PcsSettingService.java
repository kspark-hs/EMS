package com.example.demo.domain.pcs.service;

import com.example.demo.domain.pcs.dto.PcsSettingDto;
import org.springframework.stereotype.Service;

@Service
public class PcsSettingService {

    /**
     * PCS 설정 조회
     * - 현재: 더미
     * - 향후: 관리자 설정 / DB 연동
     */
    public PcsSettingDto getSetting() {
        PcsSettingDto dto = new PcsSettingDto();
        dto.setPcsCount(2); // TODO 관리자 화면 값으로 대체
        return dto;
    }
}

