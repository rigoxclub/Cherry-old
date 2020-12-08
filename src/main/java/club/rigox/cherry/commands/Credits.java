package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.Console.color;

public class Credits implements CommandExecutor {
    private Cherry cherry;

    public Credits (Cherry plugin) {
        this.cherry = plugin;
        cherry.getServer().getPluginCommand("credits").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&cThis can only execute a player."));
        }

        Player player = (Player) sender;

        player.sendMessage();
        return false;
    }
}
