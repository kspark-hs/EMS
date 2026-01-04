package com.example.demo.domain.battery.view;

import com.example.demo.domain.battery.status.BatteryRackHealthStatusType;
import lombok.Getter;

import java.util.List;

@Getter
public class BatteryRackStatusTableViewDto {

    /** 전체 Rack 수 */
    private final int totalRackCount;

    /** 운전 Rack 수 */
    private final int activeRackCount;

    /** Rack 상태 목록 */
    private final List<BatteryRackStatusTableRowViewDto> racks;

    public BatteryRackStatusTableViewDto(List<BatteryRackStatusTableRowViewDto> racks) {
        this.racks = racks;
        this.totalRackCount = (racks == null) ? 0 : racks.size();

        this.activeRackCount = (racks == null) ? 0
                : (int) racks.stream()
                .filter(r -> r.getStatus() != null)
                .filter(r -> r.getStatus().isOperable())
                .count();
    }

    /** 고장 Rack 개수 */
    public int getFaultRackCount() {
        if (racks == null) {
            return 0;
        }

        return (int) racks.stream()
                .filter(r ->
                        r.getStatus() == BatteryRackHealthStatusType.FAULT
                                || r.getStatus() == BatteryRackHealthStatusType.DISCONNECTED
                )
                .count();
    }
}
