package com.example.demo.domain.pvmeter.view;

import lombok.Data;

@Data
public class PvMeterDetailViewDto {

    private VoltageSection voltage;
    private CurrentSection current;
    private PowerSection power;
    private EtcSection etc;

    @Data
    public static class VoltageSection {
        private Double avg;
        private Double phaseA;
        private Double phaseB;
        private Double phaseC;
        private Double lineAB;
        private Double lineBC;
        private Double lineCA;
    }

    @Data
    public static class CurrentSection {
        private Double avg;
        private Double phaseA;
        private Double phaseB;
        private Double phaseC;
    }

    @Data
    public static class PowerSection {
        private Double active;
        private Double reactive;
        private Double apparent;
        private Double totalActiveEnergy;
        private Double totalReactiveEnergy;
        private Double totalApparentEnergy;
    }

    @Data
    public static class EtcSection {
        private Double powerFactor;
        private Double frequency;
        private Double loadRateA;
        private Double loadRateB;
        private Double loadRateC;
    }
}


