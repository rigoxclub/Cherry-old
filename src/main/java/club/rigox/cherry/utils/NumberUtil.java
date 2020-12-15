package club.rigox.cherry.utils;

import club.rigox.cherry.Cherry;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static club.rigox.cherry.utils.ConsoleUtils.color;
import static club.rigox.cherry.utils.ConsoleUtils.debug;

public class NumberUtil {
    private final Cherry cherry;

    public NumberUtil(Cherry plugin) {
        this.cherry = plugin;
    }

    public String formatValue(double value) {
        if (value < 1000) {
            boolean isWholeNumber = value == Math.round(value);
            DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
            formatSymbols.setDecimalSeparator('.');
            String pattern = isWholeNumber ? "###" : "###.00";
            DecimalFormat df = new DecimalFormat(pattern, formatSymbols);
            return df.format(value);
        }

        DecimalFormat df = new DecimalFormat("###.###");

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

    public boolean checkNumber(String value, Player player, Player target) {
        double playerBalance = cherry.getPlayerCredits().get(target);

        if (!(NumberUtils.isNumber(value))) {
            sendErrorMsg(player);
            return false;
        }

        if (NumberUtils.isNumber(value)) {
            if (Double.parseDouble(value) >= 1_000_000_000d) {
                sendErrorMsg(player);
                return false;
            }

            if (Integer.parseInt(value) + playerBalance >= 1_000_000_000_000_000_000d) {
                sendErrorMsg(player);
                return false;
            }
            return true;
        }
        return true;
    }

    public void sendErrorMsg(Player player) {
        player.sendMessage(color("&7&m------------------------------------------------"));
        player.sendMessage(color("&c&lSorry, you did something wrong."));
        player.sendMessage(color("&7&oPlease review the following notes:"));
        player.sendMessage(color("&7&m------------------------------------------------"));
        player.sendMessage(color("&8&l* &fYou cannot set a value higher than 1 billion per cmd"));
        player.sendMessage(color("&8&l* &fPlayers can't have more than 1 Quintillon"));
        player.sendMessage(color("&8&l* &fYou didn't provided a number"));
        player.sendMessage(color("&7&m------------------------------------------------"));
    }
}
