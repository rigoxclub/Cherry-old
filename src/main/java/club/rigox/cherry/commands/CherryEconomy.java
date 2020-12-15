package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
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
//        Player player = (Player) sender;

        if (args.length == 1) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(sender, target)) return;

            cmdUsage(sender, String.format("/cherry take %s &7(credits)", target.getName()));
            return;
        }

        if (args.length == 2) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(sender, target)) return;

            String credits = args[1];
            if (!cherry.getNumberUtils().checkNumber(credits, sender, target)) return;
            cherry.getPlayerUtils().takeCredits(target, Double.parseDouble(credits), sender);
            return;
        }
        cmdUsage(sender, "/cherry take &7(player) (credits)");
    }

    @Subcommand("give")
    @CommandPermission("cherry.give")
    public void giveCommand(CommandSender sender, String[] args) {
//        Player player = (Player) sender;

        if (args.length == 1) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(sender, target)) return;
            cmdUsage(sender, String.format("/cherry give %s &7(credits)", target.getName()));
            return;
        }

        if (args.length == 2) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(sender, target)) return;

            String credits = args[1];
            if (!cherry.getNumberUtils().checkNumber(credits, sender, target)) return;
            cherry.getPlayerUtils().giveCredits(target, Double.parseDouble(credits), sender);
            return;
        }
        cmdUsage(sender, "/cherry give &7(player) (credits)");
    }

    @Subcommand("set")
    @CommandPermission("cherry.set")
    public void setCommand(CommandSender sender, String[] args) {
//        Player player = (Player) sender;

        if (args.length == 1) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(sender, target)) return;

            cmdUsage(sender, String.format("/cherry set %s &7(credits)", target.getName()));
            return;
        }

        if (args.length == 2) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(sender, target)) return;

            String credits = args[1];
            if (!cherry.getNumberUtils().checkNumber(credits, sender, target)) return;
            cherry.getPlayerUtils().setCredits(target, Double.parseDouble(credits), sender);
            return;
        }
        cmdUsage(sender, "/cherry set &7(player) (credits)");
    }

    @Subcommand("reset")
    @CommandPermission("cherry.reset")
    public void resetCommand(CommandSender sender, String[] args) {
//        Player player = (Player) sender;

        if (args.length == 1) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (cherry.getPlayerUtils().isOffline(sender, target)) return;

            cherry.getPlayerUtils().resetCredits(target, sender);
            return;
        }
        cmdUsage(sender, "/cherry set &7(player)");
    }

    @Subcommand("admin")
    public void adminCommand(CommandSender sender) {
        sender.sendMessage(color("&7&m------------------------------------------------"));
        sender.sendMessage(color("&b&lCherry Economy"));
        sender.sendMessage(color("&7&oCommand Help"));
        sender.sendMessage(color("&7&m------------------------------------------------"));
        sender.sendMessage(color("&8&l* &b/cherry give (player) (credits) &f- Give player credits"));
        sender.sendMessage(color("&8&l* &b/cherry take (player) (credits) &f- Take player credits"));
        sender.sendMessage(color("&8&l* &b/cherry set (player) (credits) &f- Set player credits"));
        sender.sendMessage(color("&8&l* &c/cherry reset (player) &f- Reset player credits"));
        sender.sendMessage(color("&7&m------------------------------------------------"));
    }

    @HelpCommand
    public void helpCommand(CommandSender sender) {
        sender.sendMessage(color("&7&m------------------------------------------------"));
        sender.sendMessage(color("&b&lCherry Economy"));
        sender.sendMessage(color("&7&oCommand Help"));
        sender.sendMessage(color("&7&m------------------------------------------------"));
        sender.sendMessage(color("&8&l* &b/credits &f- View your credits"));
        sender.sendMessage(color("&8&l* &c/cherry admin &f- View admin command help"));
        sender.sendMessage(color("&7&m------------------------------------------------"));
    }
}
