package club.rigox.cherry;

import club.rigox.cherry.commands.CherryEconomy;
import club.rigox.cherry.commands.Credits;
import club.rigox.cherry.commands.LittleTest;
import club.rigox.cherry.database.MongoDB;
import club.rigox.cherry.hooks.Placeholders;
import club.rigox.cherry.listeners.PlayerListener;
import club.rigox.cherry.utils.ConfigUtils;
import club.rigox.cherry.utils.PlayerUtils;
import club.rigox.vanillacore.VanillaCore;
import club.rigox.vanillacore.player.scoreboard.ScoreBoardAPI;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import static club.rigox.cherry.utils.ConsoleUtils.error;

public final class Cherry extends JavaPlugin {
    public static Cherry instance;
    public static VanillaCore vanillaCore;

    private MongoDB mongoDB;
    private FileConfiguration database;
    private PlayerUtils playerUtils;
    private ScoreBoardAPI scoreBoardAPI;

    @Override
    public void onEnable() {
        instance = this;

        ConfigUtils configUtils = new ConfigUtils(this);
        this.database = configUtils.createConfig("database");
        registerCommands();
        loadHooks();
        new PlayerListener(this);
        this.playerUtils = new PlayerUtils(this);
        this.mongoDB = new MongoDB(this);
        getMongoDB().connect();

        new LittleTest(this);
//        scoreBoardAPI = new ScoreBoardAPI(vanillaCore);
        this.scoreBoardAPI = new ScoreBoardAPI(vanillaCore);
    }

    @Override
    public void onDisable() {
        getMongoDB().close();
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

        if (getServer().getPluginManager().getPlugin("VanillaCore") == null) {
            error("Could not find VanillaCore! This plugin is required.");
            getServer().getPluginManager().disablePlugin(this);
        }
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

    public ScoreBoardAPI getScoreBoardAPI() {
        return scoreBoardAPI;
    }

    public static VanillaCore getVanillaCore() {
        return vanillaCore;
    }
}
