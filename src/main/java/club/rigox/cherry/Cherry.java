package club.rigox.cherry;

import club.rigox.cherry.commands.CherryEconomy;
import club.rigox.cherry.commands.Credits;
import club.rigox.cherry.database.MongoDB;
import club.rigox.cherry.listeners.PlayerListener;
import club.rigox.cherry.utils.ConfigUtils;
import club.rigox.cherry.utils.PlayerUtils;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Cherry extends JavaPlugin {
    public static Cherry instance;

    private MongoDB mongoDB;
    private FileConfiguration database;
    private ConfigUtils configUtils;
    private PlayerUtils playerUtils;

    @Override
    public void onEnable() {
        instance = this;

        configUtils = new ConfigUtils(this);
        this.database = configUtils.createConfig("database");

        this.playerUtils = new PlayerUtils(this);

        this.mongoDB = new MongoDB(this);
        getMongoDB().connect();

        registerCommands();

        new PlayerListener(this);
    }

    @Override
    public void onDisable() {
        getMongoDB().close();
    }

    public MongoDB getMongoDB() {
        return mongoDB;
    }

    public FileConfiguration getDatabase() {
        return database;
    }

    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new CherryEconomy(this));
        manager.registerCommand(new Credits(this));

    }

    public PlayerUtils getPlayerUtils() {
        return playerUtils;
    }
}
