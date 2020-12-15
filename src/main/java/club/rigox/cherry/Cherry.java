package club.rigox.cherry;

import club.rigox.cherry.commands.CherryEconomy;
import club.rigox.cherry.commands.Credits;
import club.rigox.cherry.database.MongoDB;
import club.rigox.cherry.hooks.Placeholders;
import club.rigox.cherry.listeners.PlayerListener;
import club.rigox.cherry.utils.ConfigUtils;
import club.rigox.cherry.utils.NumberUtil;
import club.rigox.cherry.utils.PlayerUtils;
import club.rigox.vanillacore.VanillaCore;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

import static club.rigox.cherry.utils.ConsoleUtils.error;
import static club.rigox.cherry.utils.ConsoleUtils.info;

public final class Cherry extends JavaPlugin {
    public static Cherry instance;
    public static VanillaCore vanillaCore;

    private Map<Player, Double> playerCredits = new LinkedHashMap<>();

    private MongoDB mongoDB;
    private FileConfiguration database;
    private PlayerUtils playerUtils;
    private NumberUtil numberUtil;

    @Override
    public void onEnable() {
        instance = this;
        vanillaCore = VanillaCore.instance;

        ConfigUtils configUtils = new ConfigUtils(this);

        this.database = configUtils.createConfig("database");
        this.playerUtils = new PlayerUtils(this);
        this.numberUtil = new NumberUtil(this);
        this.mongoDB = new MongoDB(this);

        new PlayerListener(this);

        registerCommands();
        loadHooks();
        getMongoDB().connect();
    }

    @Override
    public void onDisable() {
        getMongoDB().close();
        info("Cherry has been disabled!");
    }

    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new CherryEconomy(this));
        manager.registerCommand(new Credits(this));
    }

    public void loadHooks() {
        new Placeholders(this).register();
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            error("Could not find PlaceholderAPI! This plugin is required.");
            getServer().getPluginManager().disablePlugin(this);
        }
        info("Successfully hooked with PlaceholderAPI!");

        if (getServer().getPluginManager().getPlugin("VanillaCore") == null) {
            error("Could not find VanillaCore! This plugin is required.");
            getServer().getPluginManager().disablePlugin(this);
        }
        info("Successfully hooked with VanillaCore!");
    }

    public PlayerUtils getPlayerUtils() {
        return playerUtils;
    }

    public MongoDB getMongoDB() {
        return mongoDB;
    }

    public FileConfiguration getDatabase() {
        return database;
    }

    public Map<Player, Double> getPlayerCredits() {
        return playerCredits;
    }

    public NumberUtil getNumberUtils() {
        return numberUtil;
    }
}
