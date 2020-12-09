package club.rigox.cherry;

import club.rigox.cherry.commands.CherryEconomy;
import club.rigox.cherry.commands.Credits;
import club.rigox.cherry.database.MongoDB;
import club.rigox.cherry.listeners.PlayerListener;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static club.rigox.cherry.utils.ConsoleUtils.warn;

public final class Cherry extends JavaPlugin {
    public static Cherry instance;

    private MongoDB mongoDB;
    private FileConfiguration database;
    private PlayerUtils playerUtils;

    @Override
    public void onEnable() {
        instance = this;

        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        this.database = createConfig("database");

        this.mongoDB = new MongoDB(this);
        getMongoDB().connect();

        new Credits(this);
        new CherryEconomy(this);

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

    public PlayerUtils getPlayerUtils() {
        return playerUtils;
    }

    public FileConfiguration createConfig(String configName) {
        File configFile = new File(getDataFolder(), configName + ".yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource(configName + ".yml", false);
        }

        FileConfiguration cfg = new YamlConfiguration();
        try {
            cfg.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            warn(String.format("A error occurred while copying the config %s.yml to the plugin data folder. Error: %s", configName, e));
            e.printStackTrace();
        }
        return cfg;
    }
}
