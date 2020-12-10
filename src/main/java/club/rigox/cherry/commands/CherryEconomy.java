package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.ConsoleUtils.cmdUsage;
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
            if (cherry.getPlayerUtils().isOffline(player, target)) return;

            cmdUsage(player, String.format("/cherry take %s &7(credits)", target.getName()));
            return;
        }

        if (args.length == 2) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(player, target)) return;

            String credits = args[1];
            if (!NumberUtils.isNumber(credits)) {
                player.sendMessage(color("&cPlease provide numbers!"));
                return;
            }

            cherry.getPlayerUtils().takeCredits(target, Integer.parseInt(credits), player);
            return;
        }
        cmdUsage(player, "/cherry take &7(player) (credits)");
    }

    @Subcommand("give")
    @CommandPermission("cherry.give")
    public void giveCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(player, target)) return;

            cmdUsage(player, String.format("/cherry give %s &7(credits)", target.getName()));
            return;
        }

        if (args.length == 2) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(player, target)) return;

            String credits = args[1];
            if (!NumberUtils.isNumber(credits)) {
                player.sendMessage(color("&cPlease provide numbers!"));
                return;
            }

            cherry.getPlayerUtils().giveCredits(target, Integer.parseInt(credits), player);
            return;
        }
        cmdUsage(player, "/cherry give &7(player) (credits)");
    }

    @Subcommand("set")
    @CommandPermission("cherry.set")
    public void setCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(player, target)) return;

            cmdUsage(player, String.format("/cherry set %s &7(credits)", target.getName()));
            return;
        }

        if (args.length == 2) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(player, target)) return;

            String credits = args[1];
            if (!NumberUtils.isNumber(credits)) {
                player.sendMessage(color("&cPlease provide numbers!"));
                return;
            }

            cherry.getPlayerUtils().setCredits(target, Integer.parseInt(credits), player);
            return;
        }
        cmdUsage(player, "/cherry set &7(player) (credits)");
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
