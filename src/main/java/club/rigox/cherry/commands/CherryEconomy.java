package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.ConsoleUtils.color;

@CommandAlias("cherry")
public class CherryEconomy extends BaseCommand {
    private Cherry cherry;

    public CherryEconomy (Cherry plugin) {
        this.cherry = plugin;
    }

    @Default
    public void cherryCommand(CommandSender sender) {
        helpCommand(sender);
    }

    @Subcommand("take")
    @CommandPermission("cherry.take")
    public void takeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            Player target = cherry.getServer().getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(color(String.format("&cThe player you provided is offline.")));
                return;
            }

            player.sendMessage(color(String.format("&8&l* &fCommand usage: &b/cherry take %s &7(credits)", target.getName())));
            return;
        }

        if (args.length == 2) {
            Player target = cherry.getServer().getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(color(String.format("&cThe player you provided is offline.")));
                return;
            }

            int credits = Integer.parseInt(args[1]);
            int playerCredits = cherry.getMongoDB().getPlayerCredits(target.getUniqueId());
            int newCredits = playerCredits - credits;

            if (newCredits <= 0) {
                cherry.updatePlayerCredits(target, 0);
                player.sendMessage(color(String.format("&a%s credits has been taken of %s balance.", newCredits, target.getName())));

                if (!target.equals(player)) {
                    target.sendMessage(color(String.format("&c%s credits has been taken from your account.", newCredits)));
                    return;
                }
                return;
            }
            cherry.updatePlayerCredits(target, newCredits);
            player.sendMessage(color(String.format("&a%s credits has been taken of %s balance.", newCredits, target.getName())));
            if (!target.equals(player)) {
                target.sendMessage(color(String.format("&c%s credits has been taken from your account.", newCredits)));
                return;
            }
            return;
        }
        player.sendMessage(color("&8&l* &fCommand usage: &b/cherry take &7(player) (credits)"));
    }

    @HelpCommand
    public void helpCommand(CommandSender sender) {
        sender.sendMessage(color("&7&m------------------------------------------------"));
        sender.sendMessage(color("&b&lCherry Economy"));
        sender.sendMessage(color("&7&oCommand Help"));
        sender.sendMessage(color("&7&m------------------------------------------------"));
        sender.sendMessage(color("&8&l* &b/credits &f- View your credits"));
        sender.sendMessage(color("&7&m------------------------------------------------"));
    }
}
