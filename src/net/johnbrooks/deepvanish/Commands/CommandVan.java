package net.johnbrooks.deepvanish.Commands;

import net.johnbrooks.deepvanish.Settings;
import net.johnbrooks.deepvanish.Tasks.VanishTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by John on 10/21/2016.
 */
public class CommandVan implements CommandExecutor
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
        VanishTask task = VanishTask.GetTask(player);
        if (task != null)
        {
            sender.sendMessage(Settings.PREFIX + "You are already vanished!");
            return true;
        }
        task = new VanishTask(player);
        task.Run();

        return true;
    }
}
