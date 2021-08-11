package com.b2gsoft.sailorsexpress.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DecimalFormatter {

    private static NumberFormat fixed2Position = new DecimalFormat("#0.00");
    private static NumberFormat fixed1Position = new DecimalFormat("#0.0");

    public static String formatUpToTwoPosition(double value) {

        return fixed2Position.format(value);
    }

    public static String formatUpToOnePosition(double value) {

        return fixed1Position.format(value);
    }
}