package club.rigox.cherry;

import club.rigox.cherry.database.MongoDB;
import club.rigox.cherry.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Cherry extends JavaPlugin {
    public static Cherry instance;

    private MongoDB mongoDB;

    @Override
    public void onEnable() {
        instance = this;

        this.mongoDB = new MongoDB();
        getMongoDB().connect();

        new PlayerListener(this);
    }

    @Override
    public void onDisable() {
    }

    public MongoDB getMongoDB() {
        return mongoDB;
    }
}
