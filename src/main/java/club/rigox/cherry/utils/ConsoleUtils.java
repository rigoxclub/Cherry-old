package club.rigox.cherry.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConsoleUtils {
    public static String color (String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void debug (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&9DEBUG&f] %s", str)));
    }

    public static void warn (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&eWARN&f] %s", str)));
    }

    public static void error (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&cERROR&f] %s", str)));
    }

    public static void info (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&aINFO&f] %s", str)));
    }

    public static void cmdUsage (CommandSender sender, String str) {
        sender.sendMessage(color(String.format("&8&l* &fCommand usage: &b%s", str)));
    }
}
