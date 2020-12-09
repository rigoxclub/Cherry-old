package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.ConsoleUtils.color;

public class Credits implements CommandExecutor {
    private final Cherry cherry;

    public Credits (Cherry plugin) {
        this.cherry = plugin;
        cherry.getServer().getPluginCommand("credits").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&cThis can only execute a player."));
            return true;
        }

        Player player = (Player) sender;
        int credits = cherry.getMongoDB().getPlayerCredits(player.getUniqueId());

        player.sendMessage(color(String.format("&8&l* &fYou have &b%s &fcredits", credits)));
        return false;
    }
}
