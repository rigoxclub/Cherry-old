package club.rigox.cherry.utils;

import club.rigox.cherry.Cherry;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class NumberUtils {
    private final Cherry cherry;

    public NumberUtils (Cherry plugin) {
        this.cherry = plugin;
    }

    private static final NavigableMap<Double, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000d, "k");
        suffixes.put(1_000_000d, "M");
        suffixes.put(1_000_000_000d, "G");
        suffixes.put(1_000_000_000_000d, "T");
        suffixes.put(1_000_000_000_000_000d, "P");
        suffixes.put(1_000_000_000_000_000_000d, "E");
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

        Map.Entry<Double, String> e = suffixes.floorEntry(value);
        String suffix = e.getValue();

        DecimalFormat df = new DecimalFormat("###,###");

        return df.format(value) + suffix;
    }
}
