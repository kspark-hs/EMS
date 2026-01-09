package com.example.demo.domain.battery.view;

import com.example.demo.domain.battery.status.BatteryRackHealthStatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Battery Rack 상태 테이블 Row ViewDto
 *
 * - UI 표시 전용
 * - Service → View 변환 규칙 보유
 */
@Data
@NoArgsConstructor
public class BatteryRackStatusTableRowViewDto {

    /* 기본 정보 */
    private Long rackId;
    private Integer rackNo;
    private String rackName;

    /* 상태 */
    private BatteryRackHealthStatusType status;
    private String internalCommStatus;   // 정상 / 두절
    private String externalCommStatus;   // 정상 / 두절 (현재 고정)

    /* 계측 값 (향후 확장) */
    private Double rackVoltage;
    private Double dcCurrent;
    private Double soc;
    private Double maxTemp;
    private Double minTemp;

    /**
     * 정적 팩토리 메서드 (Service 전용)
     */
    public static BatteryRackStatusTableRowViewDto from(
            Long rackId,
            BatteryRackHealthStatusType status,
            boolean internalCommOk
    ) {
        BatteryRackStatusTableRowViewDto dto =
                new BatteryRackStatusTableRowViewDto();

        dto.rackId = rackId;
        dto.rackNo = rackId.intValue();
        dto.rackName = "Rack #" + rackId;

        dto.status = status;
        dto.internalCommStatus = internalCommOk ? "정상" : "두절";
        dto.externalCommStatus = "정상"; // 현재 고정 (Raw 연동 시 변경)

        // 계측 값은 아직 없음
        dto.rackVoltage = null;
        dto.dcCurrent = null;
        dto.soc = null;
        dto.maxTemp = null;
        dto.minTemp = null;

        return dto;
    }
}
