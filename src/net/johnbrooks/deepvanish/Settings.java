package net.johnbrooks.deepvanish;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/21/2016.
 */
public class Settings
{
    public static final String PREFIX = ChatColor.BLUE + "[DeepVanish] " + ChatColor.RESET;
    public static String JoinMessage = "";
    public static String QuitMessage = "";
    public static Material VanishTool = Material.COMPASS;
    public static List<String> SilentJoin = new ArrayList<>();
    public static boolean AllowMetrics = true;

    public static void Load()
    {
        JoinMessage = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Join_Message"));
        QuitMessage = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Quit_Message"));
        SilentJoin = Main.plugin.getConfig().getStringList("Silent_Join");
        VanishTool = Material.getMaterial(Main.plugin.getConfig().getString("Vanish_Tool").toUpperCase());
        AllowMetrics = Main.plugin.getConfig().getBoolean("Allow_Metrics");
    }
    public static void Save()
    {
        Main.plugin.getConfig().set("Silent_Join", SilentJoin);
        Main.plugin.saveConfig();
    }
}
