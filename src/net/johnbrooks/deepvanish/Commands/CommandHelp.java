package net.johnbrooks.deepvanish.Commands;

import net.johnbrooks.deepvanish.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by John on 10/21/2016.
 */
public class CommandHelp implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.hasPermission("DeepVanish.Use"))
        {
            sender.sendMessage(Settings.PREFIX + "You do not have permission to do this!");
            return true;
        }

        sender.sendMessage(Settings.PREFIX + "List of commands - ");
        sender.sendMessage("    '/Van' - Vanish from others. ");
        sender.sendMessage("    '/Rea' - Reappear to others. ");
        sender.sendMessage("    '/VanAdd' - Add yourself to SilentJoin list. ");
        sender.sendMessage("    '/VanRemove' - Remove yourself from SilentJoin list. ");
        sender.sendMessage("    '/VanJoin' - Broadcast a join message about yourself. ");
        sender.sendMessage("    '/VanLeave' - Broadcast a quit message about yourself. ");
        sender.sendMessage("    '/VanPickup' - Toggle item pickup while vanished. ");
        sender.sendMessage("    '/VanReload' - Reload the configuration file. ");
        sender.sendMessage("    '/VanWho' - List all vanished players. ");
        sender.sendMessage("    '/VanHelp' - Show a list of commands. ");

        return true;
    }
}
