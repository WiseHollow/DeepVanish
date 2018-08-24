package net.johnbrooks.deepvanish;

import net.johnbrooks.deepvanish.Commands.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin
{
    public static Main plugin;
    public static Logger logger;

    @Override
    public void onEnable()
    {
        plugin = this;
        logger = this.getLogger();

        saveDefaultConfig();

        CommandVan vanishCommand = new CommandVan();

        this.getCommand("Van").setExecutor(vanishCommand);
        this.getCommand("Vanish").setExecutor(vanishCommand);
        this.getCommand("Rea").setExecutor(new CommandRea());
        this.getCommand("VanJoin").setExecutor(new CommandVanJoin());
        this.getCommand("VanLeave").setExecutor(new CommandVanLeave());
        this.getCommand("VanAdd").setExecutor(new CommandVanAdd());
        this.getCommand("VanRemove").setExecutor(new CommandVanRemove());
        this.getCommand("VanPickup").setExecutor(new CommandVanPickup());
        this.getCommand("VanReload").setExecutor(new CommandVanReload());
        this.getCommand("VanWho").setExecutor(new CommandVanWho());
        this.getCommand("VanHelp").setExecutor(new CommandHelp());

        getServer().getPluginManager().registerEvents(new Events(), this);
        Settings.Load();

        setupMetrics();

        logger.info(plugin.getName() + " is now enabled!");
    }
    @Override
    public void onDisable()
    {
        logger.info(plugin.getName() + " is now disabled!");
    }

    private boolean setupMetrics()
    {
        if (!Settings.AllowMetrics)
            return false;
        try
        {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }
}
