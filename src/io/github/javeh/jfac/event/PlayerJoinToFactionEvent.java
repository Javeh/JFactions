package io.github.javeh.jfac.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.javeh.jfac.faction.FactionManager;
import io.github.javeh.jfac.faction.PowerManager;

public class PlayerJoinToFactionEvent implements Listener {
	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent e) {
	assignPlayerData(e.getPlayer());
	}
	public static void assignPlayerData(Player p) {
	if(FactionManager.getPlayerFaction(p) == null) {
			
		}
		else {
			FactionManager.updatePlayerFaction(p, FactionManager.getPlayerFaction(p));
		}
		PowerManager.convertUUIDToPlayer(p);
	}
	}

