package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlantCardDto {

    private Long id;
    private String name;
    private String siteCode;
    private String address;

    private double soc;
    private double pvKw;
    private double essKw;

    private String lastSeen;

    /* ======================
       상태 판단 필드 (카드용)
       P / M / B / C / F
    ====================== */

    // P : PCS 상태 (Fault / Stop)
    private boolean pcsFault;

    // M : PV 전력량계 actorPower = 0
    private boolean pvMeterZero;

    // B : 배터리 Alarm
    private boolean batteryAlarm;

    // C : 통신 끊김
    private boolean commDown;

    // F : 에너지 플로우 위반 (ESS > PV)
    private boolean flowViolation;

    /* ======================
       파생 필드
    ====================== */

    // 위 5개 중 하나라도 true
    private boolean anyIssue;

    // 카드 하단 요약 문구
    private String issueSummary;
}
