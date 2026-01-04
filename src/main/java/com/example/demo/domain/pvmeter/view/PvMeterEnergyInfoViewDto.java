package com.example.demo.domain.pvmeter.view;

import lombok.Data;

@Data
public class PvMeterEnergyInfoViewDto {

    private String powerKw;
    private String dailyEnergyKwh;
    private String monthEnergyMwh;
    private String lastMonthEnergyMwh;
    private String totalEnergyGwh;
}

