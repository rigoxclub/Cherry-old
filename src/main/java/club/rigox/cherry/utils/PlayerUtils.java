package club.rigox.cherry.utils;

import club.rigox.cherry.Cherry;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.ConsoleUtils.color;

public class PlayerUtils {
    private Cherry cherry;

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
            return;
        }
        sender.sendMessage(color(String.format("&a%s credits has been taken of %s balance.", playerCredits - credits, target.getName())));
        cherry.getMongoDB().updatePlayerCredits(target.getUniqueId(), playerCredits - credits);
        if (!target.equals(sender)) {
            target.sendMessage(color(String.format("&c%s credits has been taken from your account.", playerCredits - credits)));
        }
    }
}
