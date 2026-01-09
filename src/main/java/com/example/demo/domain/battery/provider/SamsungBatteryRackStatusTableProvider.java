package com.example.demo.domain.battery.provider;

import com.example.demo.domain.battery.status.BatteryRackHealthStatusType;
import com.example.demo.domain.battery.view.BatteryRackStatusTableRowViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SamsungBatteryRackStatusTableProvider
        implements BatteryRackStatusTableProvider {

    @Override
    public List<BatteryRackStatusTableRowViewDto> getRackStatusRows(Long batteryId) {

        List<BatteryRackStatusTableRowViewDto> rows = new ArrayList<>();

        /* =========================
         * RACK #1-1 (정상)
         * ========================= */
        BatteryRackStatusTableRowViewDto rack1 = new BatteryRackStatusTableRowViewDto();
        rack1.setRackNo(1);
        rack1.setRackName("RACK #1-1");
        rack1.setStatus(BatteryRackHealthStatusType.NORMAL);
        rack1.setInternalCommStatus("정상");
        rack1.setExternalCommStatus("정상");
        rack1.setRackVoltage(810.50);
        rack1.setDcCurrent(4.20);
        rack1.setSoc(90.0);
        rack1.setMaxTemp(22.4);
        rack1.setMinTemp(18.1);

        rows.add(rack1);

        /* =========================
         * RACK #2-1 (고장)
         * ========================= */
        BatteryRackStatusTableRowViewDto rack2 = new BatteryRackStatusTableRowViewDto();
        rack2.setRackNo(2);
        rack2.setRackName("RACK #2-1");
        rack2.setStatus(BatteryRackHealthStatusType.FAULT);
        rack2.setInternalCommStatus("정상");
        rack2.setExternalCommStatus("정상");
        // 계측값 없음

        rows.add(rack2);

        /* =========================
         * RACK #3-1 (정상)
         * ========================= */
        BatteryRackStatusTableRowViewDto rack3 = new BatteryRackStatusTableRowViewDto();
        rack3.setRackNo(3);
        rack3.setRackName("RACK #3-1");
        rack3.setStatus(BatteryRackHealthStatusType.NORMAL);
        rack3.setInternalCommStatus("정상");
        rack3.setExternalCommStatus("정상");
        rack3.setRackVoltage(809.80);
        rack3.setDcCurrent(3.90);
        rack3.setSoc(88.5);
        rack3.setMaxTemp(21.9);
        rack3.setMinTemp(17.8);

        rows.add(rack3);

        return rows;
    }
}
