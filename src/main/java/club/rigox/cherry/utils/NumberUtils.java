package club.rigox.cherry.utils;

import club.rigox.cherry.Cherry;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberUtils {
    private final Cherry cherry;

    public NumberUtils (Cherry plugin) {
        this.cherry = plugin;
    }

    public static String formatValue(double value) {
        if (value < 1000) {
            boolean isWholeNumber = value == Math.round(value);
            DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
            formatSymbols.setDecimalSeparator('.');
            String pattern = isWholeNumber ? "###" : "###.00";
            DecimalFormat df = new DecimalFormat(pattern, formatSymbols);
            return df.format(value);
        }

        DecimalFormat df = new DecimalFormat("###,###");

        if (value >= 1_000_000_000_000_000_000d) {
            return "Quintillons not allowed.";
        }

        if (value >= 1_000_000_000_000_000d) {
            value = value / 1_000_000_000_000_000d;
            return df.format(value) + "Q";
        }

        if (value >= 1_000_000_000_000d) {
            value = value / 1_000_000_000_000d;
            return df.format(value) + "T";
        }

        if (value >= 1_000_000_000d) {
            value = value / 1_000_000_000d;
            return df.format(value) + "B";
        }

        if (value >= 1_000_000d) {
            value = value / 1_000_000d;
            return df.format(value) + "M";
        }

        if (value >= 1_000d) {
            value = value / 1_000d;
            return df.format(value) + "k";
        }

        return "Something weird has just happened... (NumberUtils)";
    }
}
