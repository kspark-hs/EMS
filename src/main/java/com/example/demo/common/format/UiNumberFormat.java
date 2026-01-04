package com.example.demo.common.format;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component("fmt")
public class UiNumberFormat {

    private final DecimalFormat temp1 = new DecimalFormat("0.0");
    private final DecimalFormat volt2 = new DecimalFormat("0.00");
    private final DecimalFormat amp2  = new DecimalFormat("0.00");
    private final DecimalFormat pct1  = new DecimalFormat("0.0");
    private final DecimalFormat hum1 = new DecimalFormat("0.0");

    public String temp(Double v) {
        if (v == null) return "-";
        return temp1.format(v);
    }

    public String volt(Double v) {
        if (v == null) return "-";
        return volt2.format(v);
    }

    public String amp(Double v) {
        if (v == null) return "-";
        return amp2.format(v);
    }

    public String pct(Double v) {
        if (v == null) return "-";
        return pct1.format(v);
    }

    public String hum(Double v) {
        if (v == null) return "-";
        return hum1.format(v);
    }
}
