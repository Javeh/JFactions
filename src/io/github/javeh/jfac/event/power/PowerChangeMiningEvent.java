package io.github.javeh.jfac.event.power;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.javeh.jfac.faction.PowerManager;

public class PowerChangeMiningEvent implements Listener{
	@EventHandler(priority = EventPriority.LOW)
	public void onBlockBreak(BlockBreakEvent e) {
		Block block = e.getBlock();
		double power = 0;
		power+= PowerConstants.BLOCK_BREAK;
		if(e.getExpToDrop() > 0) {
			if(e.getPlayer().getItemInHand().containsEnchantment(Enchantment.DIG_SPEED)) {
				power-= e.getExpToDrop()/2;
			}
			power+= e.getExpToDrop();
			PowerManager.addPower(e.getPlayer(), power);
		}
		else {
			PowerManager.addPower(e.getPlayer(), power, false);
		}

	}
}
