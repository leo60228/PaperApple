package space.leo60228.paperapple;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SyncTask extends BukkitRunnable {

    public void run() {
        for (Player p : PaperApple.getInstance().getServer().getOnlinePlayers()) {
            SyncHandler.onPlayerUpdate(p);
        }
    }
}
