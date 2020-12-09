package club.rigox.cherry.listeners;

import club.rigox.cherry.Cherry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static club.rigox.cherry.utils.ConsoleUtils.debug;

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
            debug(String.format("Adding %s to the database with uuid %s", player, player.getUniqueId()));
            cherry.getMongoDB().storePlayer(player.getUniqueId(), 100);
        }
    }
}
