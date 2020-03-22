package space.leo60228.paperapple;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAppleSkin implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 1) return false;
            boolean enabled;
            if (args[0].equals("on")) {
                enabled = true;
            } else if (args[0].equals("off")) {
                enabled = false;
            } else {
                return false;
            }
            SyncHandler.setEnabled(player, enabled);
        } else {
            sender.sendMessage("This command can only be used by a player!");
        }
        return true;
    }
}
