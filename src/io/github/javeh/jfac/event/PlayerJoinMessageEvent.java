package io.github.javeh.jfac.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinMessageEvent implements Listener{

	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(ChatColor.YELLOW+ e.getPlayer().getPlayerListName()+ " is here!"+ChatColor.GOLD+" It's a great day to be a Raider!");
		ChunkManager.updateLastChunk(e.getPlayer());
	
	}
	
}
