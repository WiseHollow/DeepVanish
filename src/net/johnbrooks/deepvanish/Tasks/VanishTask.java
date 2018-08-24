package net.johnbrooks.deepvanish.Tasks;

import net.johnbrooks.deepvanish.Main;
import net.johnbrooks.deepvanish.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/21/2016.
 */
public class VanishTask implements Listener
{
    public static List<VanishTask> tasks = new ArrayList<>();
    public static VanishTask GetTask(Player player)
    {
        for (VanishTask task : tasks)
            if (task.player.equals(player))
                return task;
        return null;
    }
    public static void RefreshAll()
    {
        tasks.forEach(VanishTask::RefreshVisible);
    }
    public static void DisableAll()
    {
        tasks.forEach(VanishTask::Disable);
    }
    public static String GetVanishedPlayers()
    {
        String s = "";
        for (VanishTask t : tasks)
            if (t.player != null)
                s += t.player.getName() + " ";
        return s;
    }

    private Player player;
    private boolean pickup;
    private boolean chestInspecting;

    public VanishTask(Player player)
    {
        this.player = player;
        this.pickup = false;
        this.chestInspecting = false;
    }

    public void Run()
    {
        if (player == null)
            return;
        player.setMetadata("vanished", new FixedMetadataValue(Main.plugin, true));
        player.sendMessage(Settings.PREFIX + "You are now vanished!");
        Bukkit.getPluginManager().registerEvents(this, Main.plugin);
        RefreshVisible();
        tasks.add(this);
    }

    public void Disable()
    {
        player.removeMetadata("vanished", Main.plugin);
        player.sendMessage(Settings.PREFIX + "You are no longer vanished!");
        EntityPickupItemEvent.getHandlerList().unregister(this);
        EntityTargetLivingEntityEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
        PlayerJoinEvent.getHandlerList().unregister(this);
        PlayerInteractEvent.getHandlerList().unregister(this);
        InventoryCloseEvent.getHandlerList().unregister(this);
        InventoryDragEvent.getHandlerList().unregister(this);
        InventoryClickEvent.getHandlerList().unregister(this);

        for(Player p : Bukkit.getOnlinePlayers())
        {
            if (!p.equals(player))
                p.showPlayer(Main.plugin, player);
        }

        tasks.remove(this);
    }

    private void RefreshVisible()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if (p.equals(player))
                continue;
            if (!p.hasPermission("DeepVanish.See"))
                p.hidePlayer(Main.plugin, player);
            else
                p.showPlayer(Main.plugin, player);
        }
    }

    public boolean TogglePickup()
    {
        pickup = !pickup;
        return pickup;
    }

    @EventHandler
    public void DisablePickup(EntityPickupItemEvent event)
    {
        if (!event.isCancelled() && event.getEntity() instanceof Player && event.getEntity().equals(player) && !pickup) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PreventTarget(EntityTargetLivingEntityEvent event)
    {
        if (event.isCancelled() || event.getTarget() == null || !event.getTarget().equals(player))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void RemoveQuitMessage(PlayerQuitEvent event)
    {
        if (!event.getPlayer().equals(player))
            return;

        event.setQuitMessage("");
        Disable();
    }

    @EventHandler
    public void RemoveJoinMessage(PlayerJoinEvent event)
    {
        if (!event.getPlayer().equals(player))
            return;

        event.setJoinMessage("");
    }

    @EventHandler
    public void SilentOpenChest(PlayerInteractEvent event)
    {
        if (event.isCancelled() || !event.getPlayer().equals(player) || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (event.getClickedBlock().getType() != Material.CHEST)
            return;

        event.setCancelled(true);
        Chest chest = (Chest) event.getClickedBlock().getState();
        Inventory inv = Bukkit.getServer().createInventory(player, chest.getInventory().getSize());
        if (chest.getInventory().getContents() != null) {
            inv.setContents(chest.getInventory().getContents());
        }
        chest.getInventory().setContents(inv.getContents());
        chestInspecting = true;
        player.openInventory(inv);
    }

    @EventHandler
    public void CloseChest(InventoryCloseEvent event)
    {
        if (!event.getPlayer().equals(player))
            return;

        chestInspecting = false;
    }

    @EventHandler
    public void PreventInvDrag(InventoryDragEvent event)
    {
        if (!event.getWhoClicked().equals(player) || !chestInspecting)
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void PreventInvDrag(InventoryClickEvent event)
    {
        if (!event.getWhoClicked().equals(player) || !chestInspecting)
            return;

        event.setCancelled(true);
    }
}
