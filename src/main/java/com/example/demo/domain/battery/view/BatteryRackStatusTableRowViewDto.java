package com.example.demo.domain.battery.view;

import com.example.demo.domain.battery.status.BatteryRackHealthStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatteryRackStatusTableRowViewDto {

    private Integer rackNo;
    private String rackName;
    private BatteryRackHealthStatusType status;
    private Double rackVoltage;
    private Double dcCurrent;
    private Double soc;
    private Double maxTemp;
    private Double minTemp;
}

