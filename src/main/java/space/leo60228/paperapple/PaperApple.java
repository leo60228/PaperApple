package space.leo60228.paperapple;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class PaperApple extends JavaPlugin {
    private BukkitTask syncTask = null;

    private static PaperApple instance;

    public static PaperApple getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        SyncTask task = new SyncTask();
        syncTask = task.runTaskTimer(this, 0, 1);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    @Override
    public void onDisable() {
        if (syncTask != null) {
            syncTask.cancel();
            syncTask = null;
        }
    }
}
