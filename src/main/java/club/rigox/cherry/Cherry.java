package club.rigox.cherry;

import club.rigox.cherry.commands.CherryEconomy;
import club.rigox.cherry.commands.Credits;
import club.rigox.cherry.database.MongoDB;
import club.rigox.cherry.listeners.PlayerListener;
import club.rigox.cherry.utils.PlayerUtils;
import co.aikar.commands.PaperCommandManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static club.rigox.cherry.utils.ConsoleUtils.*;

public final class Cherry extends JavaPlugin {
    public static Cherry instance;

    private MongoDB mongoDB;
    private FileConfiguration database;
    private PlayerUtils playerUtils;
    private Economy economy = null;

    @Override
    public void onEnable() {
        instance = this;

        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        this.database = createConfig("database");

        this.playerUtils = new PlayerUtils(this);

        this.mongoDB = new MongoDB(this);
        getMongoDB().connect();

        registerCommands();

        if (!setupEconomy()) {
            error("Vault is required for this plugin to work.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

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

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            debug("Plugin Vault not found, returning false.");
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            debug("RegisteredServiceProvider is null, returning false.");
            return false;
        }

        economy = rsp.getProvider();
        debug("Ok, everything is fine, returning true.");
        return true;
    }

    public Economy getEconomy() {
        return economy;
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
