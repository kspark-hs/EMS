package com.example.demo.domain.equipment.firesystem.provider;

import org.springframework.stereotype.Component;

/**
 * FirePro 소화 시스템 Provider
 */
@Component
public class FireProFireSystemProvider
        implements FireSystemProvider {

    @Override
    public Boolean isExtinguishingReleased() {
        // TODO FirePro FUNCTION 2 / ADDRESS 0 연동
        return null; // 현재는 정상 더미
    }
}

