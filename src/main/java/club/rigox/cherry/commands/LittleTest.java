package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static club.rigox.cherry.utils.ConsoleUtils.debug;

public class LittleTest implements CommandExecutor {
    private Cherry cherry;

    public LittleTest (Cherry plugin) {
        this.cherry = plugin;
        cherry.getServer().getPluginCommand("pajin").setExecutor(this);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            debug("Conchetumare.");
            return true;
        }

        cherry.getScoreBoardAPI().setScoreBoard((Player) sender, "general", true);
        return false;
    }
}
