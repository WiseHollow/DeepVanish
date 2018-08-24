package net.johnbrooks.deepvanish;

import net.johnbrooks.deepvanish.Tasks.VanishTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginDisableEvent;

/**
 * Created by John on 10/21/2016.
 */
public class Events implements Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    public void AutoVanish(PlayerJoinEvent event)
    {
        VanishTask.RefreshAll();

        if (!Settings.SilentJoin.contains(event.getPlayer().getUniqueId().toString()))
            return;

        event.setJoinMessage("");
        if (VanishTask.GetTask(event.getPlayer()) != null)
            return;

        VanishTask task = new VanishTask(event.getPlayer());
        task.Run();
    }

    @EventHandler
    public void VanishTool(PlayerInteractEvent event)
    {
        if (event.isCancelled() || !event.getPlayer().hasPermission("DeepVanish.Use") || event.getPlayer().getInventory().getItemInHand().getType() != Settings.VanishTool)
            return;

        event.setCancelled(true);
        VanishTask task = VanishTask.GetTask(event.getPlayer());
        if (task == null)
        {
            task = new VanishTask(event.getPlayer());
            task.Run();
        }
        else
        {
            task.Disable();
        }
    }

    @EventHandler
    public void DisableOnShutdown(PluginDisableEvent event)
    {
        if (!event.getPlugin().equals(Main.plugin) || Bukkit.getServer().getOnlinePlayers().isEmpty())
            return;

        for (Player p : Bukkit.getServer().getOnlinePlayers())
        {
            for (Player p2 : Bukkit.getServer().getOnlinePlayers())
            {
                p.showPlayer(p2);
            }
        }
    }
}
