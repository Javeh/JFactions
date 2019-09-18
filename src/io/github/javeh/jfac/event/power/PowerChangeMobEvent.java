package io.github.javeh.jfac.event.power;

import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import io.github.javeh.jfac.faction.PowerManager;

public class PowerChangeMobEvent implements Listener {
	/*
	@EventHandler(priority =EventPriority.LOW)
	public void onKill(PlayerDeathEvent e) {
		PowerManager.addPower(e.getEntity().getKiller(), 5.0);
		
	}*/

	@EventHandler(priority =EventPriority.LOW)
	public void onMobKill(EntityDeathEvent e) {
		if(e.getEntity().getKiller() != null) {
			double power = 0;
			if(e.getEntity() instanceof Monster) {
				power +=PowerConstants.HOSTILE;
			}
			else {
				power += PowerConstants.PEACEFUL;
			}
			//e.getEntity().getKiller().sendMessage(ChatColor.GREEN+"You got "+ChatColor.ITALIC+power+ChatColor.RESET+ChatColor.GREEN +" power!");
			power += e.getDroppedExp()/7;
			PowerManager.addPower(e.getEntity().getKiller(), power);
		//	PowerManager.sendPowerMessage(e.g	etEntity().getKiller(), power);
		}
	}
	
}
