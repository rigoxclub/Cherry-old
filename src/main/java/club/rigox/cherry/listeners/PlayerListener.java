package club.rigox.cherry.listeners;

import club.rigox.cherry.Cherry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static club.rigox.cherry.utils.ConsoleUtils.debug;

public class PlayerListener implements Listener {
    private final Cherry cherry;

    public PlayerListener (Cherry plugin) {
        this.cherry = plugin;
        cherry.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // TODO Move this on the VanillaCore so it loads before the Scoreboard.
        cherry.getPlayerCredits().put(player, cherry.getMongoDB().getPlayerCredits(player.getUniqueId()));
        debug(String.format("Added %s to getPlayerCredits Map", player.getName()));
//        if (!(player.hasPlayedBefore())) {
//            debug(String.format("Adding %s to the database with uuid %s", player, player.getUniqueId()));
//            cherry.getMongoDB().storePlayer(player.getUniqueId(), 100);
//        }
    }

    @EventHandler
    public void onPlayerLeave (PlayerQuitEvent event) {
        Player player = event.getPlayer();

        cherry.getMongoDB().updatePlayerCredits(player.getUniqueId(), cherry.getPlayerCredits().get(player));
        cherry.getPlayerCredits().remove(player);
    }
}
