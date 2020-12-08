package club.rigox.economy.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Console {
    public static String color (String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }


    public static void debug (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&9DEBUG&f] %s", str)));
    }
}
