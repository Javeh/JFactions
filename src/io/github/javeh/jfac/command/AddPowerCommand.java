package io.github.javeh.jfac.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.faction.PowerManager;

public class AddPowerCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if(arg3[0].length() > 0 && arg3[1].length() > 0) {
			PowerManager.addPower(Bukkit.getServer().getPlayer(arg3[0]), Double.parseDouble(arg3[1]));

		}
		else {
			PowerManager.addPower(player, Double.parseDouble(arg3[1]));
		}
		return true;
	}

}
