package io.github.javeh.jfac.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AxeCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {

		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (sender.getName().equals("MrJaveh")) {
			ItemStack axe = new ItemStack(Material.DIAMOND, 1);
			axe.addEnchantment(Enchantment.DAMAGE_ALL, 2);
			player.getInventory().addItem(axe);
			player.sendMessage("Use it wisely...");

		}
		return true;

	}
}
