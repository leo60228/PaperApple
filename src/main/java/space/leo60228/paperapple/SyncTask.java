package space.leo60228.paperapple;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SyncTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : PaperApple.getInstance().getServer().getOnlinePlayers()) {
            SyncHandler.onPlayerUpdate(p);
        }
    }
}
