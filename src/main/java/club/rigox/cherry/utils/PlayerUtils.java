package club.rigox.cherry.utils;

import club.rigox.cherry.Cherry;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.ConsoleUtils.color;
import static club.rigox.cherry.utils.ConsoleUtils.debug;

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

    public double playerCredits(Player player) {
        return cherry.getPlayerCredits().get(player);
    }

    public void updateScoreboard(Player player) {
        Cherry.vanillaCore.getScoreBoardAPI().setScoreBoard(player, "general", true);
    }

    public void takeCredits(Player target, double credits, Player sender) {
        if (playerCredits(target) - credits <= 0) {
            cherry.getPlayerCredits().put(target, 0.0);
            sender.sendMessage(color(String.format("&aBalance of %s has been reset. &7(The value you provided is higher than player's balance)", target.getName())));

            if (!target.equals(sender)) {
                target.sendMessage(color(String.format("&cYour balance has been cleared by %s", sender.getName())));
            }

            updateScoreboard(target);
            return;
        }

        sender.sendMessage(color(String.format("&a%s credits has been taken of %s balance.", credits, target.getName())));
        cherry.getPlayerCredits().put(target, playerCredits(target) - credits);

        if (!target.equals(sender)) {
            target.sendMessage(color(String.format("&c%s credits has been taken from your account.", credits)));
        }

        updateScoreboard(target);
    }

    public void giveCredits(Player target, double credits, Player sender) {
        if (credits <= 0) {
            sender.sendMessage(color(String.format("&cYou can't set a negative number! &7(Value provided: %s)", credits)));
            return;
        }

        sender.sendMessage(color(String.format("&a%s credits has been given to %s balance.", credits, target.getName())));
        cherry.getPlayerCredits().put(target, playerCredits(target) + credits);
        updateScoreboard(target);

        if (!target.equals(sender)) {
            target.sendMessage(color(String.format("&c%s credits has been given to your account.", credits)));
        }
    }

    public void setCredits(Player target, double credits, Player sender) {
        if (credits < 0) {
            sender.sendMessage(color(String.format("&cYou can't set a negative number! &7(Value provided: %s)", credits)));
            return;
        }

        sender.sendMessage(color(String.format("&a%s balance has been set to %s credits.", target.getName(), credits)));
        cherry.getPlayerCredits().put(target, credits);
        updateScoreboard(target);

        if (!target.equals(sender)) {
            target.sendMessage(color(String.format("&cYour balance has been set to %s credits.", credits)));
        }
    }

    public void resetCredits(Player target, Player sender) {
        sender.sendMessage(color(String.format("&a%s balance has been reset.", target.getName())));
        cherry.getPlayerCredits().put(target, 0.0);
        updateScoreboard(target);

        if (!target.equals(sender)) {
            target.sendMessage(color("&cYour balance has been reset."));
        }
    }

    public void saveCreditsOnStop() {
        for (Player player : cherry.getServer().getOnlinePlayers()) {
            cherry.getMongoDB().updatePlayerCredits(player.getUniqueId(), cherry.getPlayerCredits().get(player));
            debug(String.format("%s has %s credits, they have been saved on server stop.", player.getName(), cherry.getPlayerCredits().get(player)));
            cherry.getPlayerCredits().remove(player);
        }
    }
}
