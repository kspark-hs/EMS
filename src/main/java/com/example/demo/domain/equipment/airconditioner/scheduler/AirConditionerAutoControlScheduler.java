package com.example.demo.domain.equipment.airconditioner.scheduler;

import com.example.demo.domain.equipment.airconditioner.service.AirConditionerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirConditionerAutoControlScheduler {

    private final AirConditionerService airConditionerService;

    /**
     * 에어컨 AUTO 제어 주기 실행
     *
     * - 10초 주기
     * - 향후 발전소별 / 에어컨별 확장 가능
     */
    @Scheduled(fixedDelay = 10_000)
    public void autoControl() {
        airConditionerService.autoControlIfNeeded("AC-01");
    }
}

