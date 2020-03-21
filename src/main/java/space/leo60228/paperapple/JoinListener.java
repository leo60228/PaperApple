package space.leo60228.paperapple;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinListener implements Listener
{
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        SyncHandler.onPlayerLoggedIn(event.getPlayer());
    }
}