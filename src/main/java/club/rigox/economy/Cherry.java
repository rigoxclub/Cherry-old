package club.rigox.economy;

import club.rigox.economy.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Cherry extends JavaPlugin {

    @Override
    public void onEnable() {
        new PlayerListener(this);
    }

    @Override
    public void onDisable() {
    }
}
