package com.example.demo.model.format;

import java.text.DecimalFormat;

/**
 * EMS 화면 표시 전용 숫자 포맷 유틸
 * - View DTO에서만 사용
 * - HTML / Controller / Domain 사용 금지
 */
public final class ValueFormatter {

    private ValueFormatter() {}

    /** 전력, 전압, 전류, 에너지 */
    private static final DecimalFormat DF_2 = new DecimalFormat("0.00");

    /** SOC, 효율, 주파수 */
    private static final DecimalFormat DF_1 = new DecimalFormat("0.0");

    /** 정수 */
    private static final DecimalFormat DF_0 = new DecimalFormat("0");

    public static String f2(Double v) {
        return v == null ? "-" : DF_2.format(v);
    }

    public static String f1(Double v) {
        return v == null ? "-" : DF_1.format(v);
    }

    public static String f0(Integer v) {
        return v == null ? "-" : DF_0.format(v);
    }
}

