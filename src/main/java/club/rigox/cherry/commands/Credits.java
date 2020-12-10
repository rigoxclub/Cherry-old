package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.ConsoleUtils.cmdUsage;
import static club.rigox.cherry.utils.ConsoleUtils.color;

@CommandAlias("money|balance|credits")
public class Credits extends BaseCommand {
    private Cherry cherry;

    public Credits(Cherry plugin) {
        this.cherry = plugin;
    }

    @Default
    public void onDefault(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&cThis command can be only executed by a player."));
            return;
        }

        Player player = (Player) sender;
        if (args.length > 1) {
            cmdUsage(player, "/credits (player)");
            return;
        }
        if (args.length == 1) {
            Player target = cherry.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(color(String.format("&c%s is offline right now!", player.getName())));
                return;
            }

            if (!sender.hasPermission("cherry.credits.other")) {
                sender.sendMessage(color("&cYou need the &acherry.credits.other &cto view other player credits!"));
                return;
            }

            int credits = cherry.getMongoDB().getPlayerCredits(target.getUniqueId());
            player.sendMessage(color(String.format("&8&l* &f%s have &b%s &fcredits", target.getName(), credits)));
            return;
        }

        int credits = cherry.getMongoDB().getPlayerCredits(player.getUniqueId());
        player.sendMessage(color(String.format("&8&l* &fYou have &b%s &fcredits", credits)));
    }
}
