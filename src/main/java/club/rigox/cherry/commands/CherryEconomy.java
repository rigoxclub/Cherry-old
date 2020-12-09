package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.Console.color;

public class CherryEconomy implements CommandExecutor {
    private final Cherry cherry;

    public CherryEconomy (Cherry plugin) {
        this.cherry = plugin;
        cherry.getServer().getPluginCommand("cherry").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(color("&7&m------------------------------------------------"));
            player.sendMessage(color("&b&lCherry Economy"));
            player.sendMessage(color("&7&oCommand Help"));
            player.sendMessage(color("&7&m------------------------------------------------"));
            player.sendMessage(color("&8&l* &b/credits &f- View your credits"));
            player.sendMessage(color("&7&m------------------------------------------------"));
            return true;
        }

        if (args[0].equalsIgnoreCase("take")) {
            if (args.length == 2) {
                player.sendMessage(color("&cPlease provide credits to take."));
                return true;
            }

            if (args.length == 3) {
                Player target = cherry.getServer().getPlayer(args[1]);

                if (target == null) {
                    player.sendMessage(color("&cPlayer is offline."));
                    return true;
                }

                int credits = Integer.parseInt(args[2]);
                int playerCredits = cherry.getMongoDB().getPlayerCredits(target.getUniqueId());
                int newPlayerCredits = playerCredits - credits;

                if (target.equals(player)) {
                    if (newPlayerCredits <= 0) {
                        target.sendMessage(color(String.format("&cYou have been removed %s credits.", credits)));
                        cherry.getMongoDB().updatePlayerCredits(target.getUniqueId(), 0);
                        return true;
                    }
                    target.sendMessage(color(String.format("&cYou have been removed %s credits.", credits)));
                    cherry.getMongoDB().updatePlayerCredits(target.getUniqueId(), newPlayerCredits);
                    return true;
                }

                if (newPlayerCredits <= 0) {
                    player.sendMessage(color(String.format("&aYou successfully removed %s credits to %s", credits, target.getName())));
                    target.sendMessage(color(String.format("&cYou have been removed %s credits.", credits)));
                    cherry.getMongoDB().updatePlayerCredits(target.getUniqueId(), 0);
                    return true;
                }
                player.sendMessage(color(String.format("&aYou successfully removed %s credits to %s", credits, target.getName())));
                target.sendMessage(color(String.format("&cYou have been removed %s credits.", credits)));
                cherry.getMongoDB().updatePlayerCredits(target.getUniqueId(), newPlayerCredits);
                return true;
            }

            player.sendMessage(color("&8&l* &fCommand usage: &b/cherry take (Player) (Credits)"));
            return true;
        }
        return false;
    }
}
