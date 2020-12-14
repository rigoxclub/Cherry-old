package club.rigox.cherry.hooks;

import club.rigox.cherry.Cherry;
import club.rigox.cherry.utils.NumberUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {
    private Cherry cherry;

    public Placeholders(Cherry plugin) {
        this.cherry = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String getAuthor() {
        return cherry.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "cherry";
    }

    @Override
    public String getVersion() {
        return cherry.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("credits")) {
            return cherry.getNumberUtils().formatValue(cherry.getPlayerCredits().get(player));
        }

        return null;
    }
}