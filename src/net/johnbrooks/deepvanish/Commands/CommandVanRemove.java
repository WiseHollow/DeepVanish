package net.johnbrooks.deepvanish.Commands;

import net.johnbrooks.deepvanish.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/21/2016.
 */
public class CommandVanRemove implements CommandExecutor
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
        if (Settings.SilentJoin.contains(player.getUniqueId().toString()))
            Settings.SilentJoin.remove(player.getUniqueId().toString());
        sender.sendMessage(Settings.PREFIX + "Removed from the Silent Join list.");
        Settings.Save();

        return true;
    }
}
