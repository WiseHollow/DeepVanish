package net.johnbrooks.deepvanish.Commands;

import net.johnbrooks.deepvanish.Settings;
import net.johnbrooks.deepvanish.Tasks.VanishTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by John on 10/21/2016.
 */
public class CommandVanWho implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.hasPermission("DeepVanish.See"))
        {
            sender.sendMessage(Settings.PREFIX + "You do not have permission to do this!");
            return true;
        }

        final String vanished = VanishTask.GetVanishedPlayers();
        if (!vanished.equalsIgnoreCase(""))
            sender.sendMessage(Settings.PREFIX + "Vanished players: " + vanished);
        else
            sender.sendMessage(Settings.PREFIX + "Vanished players: None");

        return true;
    }
}
