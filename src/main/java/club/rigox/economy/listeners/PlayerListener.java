package club.rigox.economy.listeners;

import club.rigox.economy.Cherry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private Cherry cherry;

    public PlayerListener (Cherry plugin) {
        this.cherry = plugin;
        cherry.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!(player.hasPlayedBefore())) {

        }
    }
}
