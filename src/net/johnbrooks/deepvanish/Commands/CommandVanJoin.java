package net.johnbrooks.deepvanish.Commands;

import net.johnbrooks.deepvanish.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/21/2016.
 */
public class CommandVanJoin implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(Settings.PREFIX + "You must be logged in to do this!");
            return true;
        }
        if (!sender.hasPermission("DeepVanish.Use"))
        {
            sender.sendMessage(Settings.PREFIX + "You do not have permission to do this!");
            return true;
        }
        Player player = (Player) sender;
        Bukkit.getServer().broadcastMessage(Settings.JoinMessage.replaceAll("%p", player.getName()));

        return true;
    }
}
