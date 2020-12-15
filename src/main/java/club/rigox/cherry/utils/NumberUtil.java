package club.rigox.cherry.utils;

import club.rigox.cherry.Cherry;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static club.rigox.cherry.utils.ConsoleUtils.color;

public class NumberUtil {
    private final Cherry cherry;

    public NumberUtil(Cherry plugin) {
        this.cherry = plugin;
    }

    public String formatValue(double credits) {
        if (credits < 1000) {
            boolean isWholeNumber = credits == Math.round(credits);
            DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
            formatSymbols.setDecimalSeparator('.');
            String pattern = isWholeNumber ? "###" : "###.00";
            DecimalFormat df = new DecimalFormat(pattern, formatSymbols);
            return df.format(credits);
        }

        DecimalFormat df = new DecimalFormat("###.###");

        if (credits >= 1_000_000_000_000_000_000d) {
            return "Quintillons not allowed.";
        }

        if (credits >= 1_000_000_000_000_000d) {
            credits = credits / 1_000_000_000_000_000d;
            return df.format(credits) + "Q";
        }

        if (credits >= 1_000_000_000_000d) {
            credits = credits / 1_000_000_000_000d;
            return df.format(credits) + "T";
        }

        if (credits >= 1_000_000_000d) {
            credits = credits / 1_000_000_000d;
            return df.format(credits) + "B";
        }

        if (credits >= 1_000_000d) {
            credits = credits / 1_000_000d;
            return df.format(credits) + "M";
        }

        if (credits >= 1_000d) {
            credits = credits / 1_000d;
            return df.format(credits) + "k";
        }

        return "Something weird has just happened... (NumberUtils)";
    }

    public boolean checkNumber(String credits, CommandSender sender, Player target) {
        double playerBalance = cherry.getPlayerCredits().get(target);

        if (!(NumberUtils.isNumber(credits))) {
            sendErrorMsg(sender);
            return false;
        }

        if (NumberUtils.isNumber(credits)) {
            if (Double.parseDouble(credits) >= 1_000_000_000d) {
                sendErrorMsg(sender);
                return false;
            }

            if (Integer.parseInt(credits) + playerBalance >= 1_000_000_000_000_000_000d) {
                sendErrorMsg(sender);
                return false;
            }
            return true;
        }
        return true;
    }

    public void sendErrorMsg(CommandSender sender) {
        sender.sendMessage(color("&7&m------------------------------------------------"));
        sender.sendMessage(color("&c&lSorry, you did something wrong."));
        sender.sendMessage(color("&7&oPlease review the following notes:"));
        sender.sendMessage(color("&7&m------------------------------------------------"));
        sender.sendMessage(color("&8&l* &fYou cannot set a value higher than 1 billion per cmd"));
        sender.sendMessage(color("&8&l* &fPlayers can't have more than 1 Quintillon"));
        sender.sendMessage(color("&8&l* &fYou didn't provided a number"));
        sender.sendMessage(color("&7&m------------------------------------------------"));
    }
}
