package io.github.javeh.jfac.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.javeh.jfac.JFac;
import io.github.javeh.jfac.JLogger;
import io.github.javeh.jfac.faction.PowerManager;

public class PlayerLeaveUpdateEvent implements Listener{
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerLeave(PlayerQuitEvent e) {
		JFac.getDB().uploadPlayerPower(e.getPlayer(), PowerManager.getPower(e.getPlayer()));
		JLogger.info(e.getPlayer()+ "Left! Updating Database with information.");
	}

}
