package club.rigox.cherry.utils;

import club.rigox.cherry.Cherry;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.ConsoleUtils.color;

public class PlayerUtils {
    private final Cherry cherry;

    public PlayerUtils (Cherry plugin) {
        this.cherry = plugin;
    }

    public boolean isOffline (Player sender, Player target) {
        if (target == null) {
            sender.sendMessage(color("&cThe player you provided is offline."));
            return true;
        }
        return false;
    }

    public void takeCredits(Player target, int credits, Player sender) {
        int playerCredits = cherry.getMongoDB().getPlayerCredits(target.getUniqueId());

        if (playerCredits - credits <= 0) {
            cherry.getMongoDB().updatePlayerCredits(target.getUniqueId(), 0);
            sender.sendMessage(color(String.format("&aBalance of %s has been reset. &7(The value you provided is higher than player's balance)", target.getName())));
            if (!target.equals(sender)) {
                target.sendMessage(color(String.format("&cYour balance has been cleared by %s", sender.getName())));
            }
            cherry.getScoreBoardAPI().setScoreBoard(target, "general", true);
//            Cherry.getVanillaCore().getScoreBoardAPI().setScoreBoard(target, "general", true);
            return;
        }
        sender.sendMessage(color(String.format("&a%s credits has been taken of %s balance.", credits, target.getName())));
        cherry.getMongoDB().updatePlayerCredits(target.getUniqueId(), playerCredits - credits);
        if (!target.equals(sender)) {
            target.sendMessage(color(String.format("&c%s credits has been taken from your account.", credits)));
        }
        cherry.getScoreBoardAPI().setScoreBoard(target, "general", true);
//        Cherry.getVanillaCore().getScoreBoardAPI().setScoreBoard(target, "general", true);

    }

    public void giveCredits(Player target, int credits, Player sender) {
        int playerCredits = cherry.getMongoDB().getPlayerCredits(target.getUniqueId());
        if (credits <= 0) {
            sender.sendMessage(color(String.format("&cYou can't set a negative number! &7(Value provided: %s)", credits)));
            return;
        }

        sender.sendMessage(color(String.format("&a%s credits has been given to %s balance.", credits, target.getName())));
        cherry.getMongoDB().updatePlayerCredits(target.getUniqueId(), playerCredits + credits);
        if (!target.equals(sender)) {
            target.sendMessage(color(String.format("&c%s credits has been given to your account.", credits)));
        }
    }

    public void setCredits(Player target, int credits, Player sender) {
        if (credits < 0) {
            sender.sendMessage(color(String.format("&cYou can't set a negative number! &7(Value provided: %s)", credits)));
            return;
        }

        sender.sendMessage(color(String.format("&a%s balance has been set to %s credits.", target.getName(), credits)));
        cherry.getMongoDB().updatePlayerCredits(target.getUniqueId(), credits);
        if (!target.equals(sender)) {
            target.sendMessage(color(String.format("&cYour balance has been set to %s credits.", credits)));
        }
    }

    public void resetCredits(Player target, Player sender) {
        sender.sendMessage(color(String.format("&a%s balance has been reset.", target.getName())));
        cherry.getMongoDB().updatePlayerCredits(target.getUniqueId(), 0);
        if (!target.equals(sender)) {
            target.sendMessage(color("&cYour balance has been reset."));
        }
    }
}
