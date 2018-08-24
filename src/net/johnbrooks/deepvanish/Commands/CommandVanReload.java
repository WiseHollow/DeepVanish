package net.johnbrooks.deepvanish.Commands;

import net.johnbrooks.deepvanish.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by John on 10/21/2016.
 */
public class CommandVanReload implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.hasPermission("DeepVanish.Use"))
        {
            sender.sendMessage(Settings.PREFIX + "You do not have permission to do this!");
            return true;
        }

        Settings.Load();
        sender.sendMessage(Settings.PREFIX + "Configuration reloaded!");
        return true;
    }
}
