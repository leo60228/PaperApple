package space.leo60228.paperapple;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class PaperApple extends JavaPlugin {
    private BukkitTask syncTask = null;
    private static PaperApple instance;

    public static NamespacedKey IS_ENABLED;

    public static PaperApple getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        IS_ENABLED = new NamespacedKey(this, "is_enabled");
    }

    @Override
    public void onEnable() {
        SyncTask task = new SyncTask();
        syncTask = task.runTaskTimer(this, 0, 1);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getCommand("appleskin").setExecutor(new CommandAppleSkin());
    }

    @Override
    public void onDisable() {
        if (syncTask != null) {
            syncTask.cancel();
            syncTask = null;
        }
    }
}
