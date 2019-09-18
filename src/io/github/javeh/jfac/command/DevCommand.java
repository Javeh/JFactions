package io.github.javeh.jfac.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.event.ChunkMoveEvent;

public class DevCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if(!sender.isOp()) {
			return false;
		}
		player.sendMessage(""+Runtime.getRuntime().totalMemory()/10000000+"MB");
		ChunkMoveEvent.placeGlassBorder(player);
		return true;
	}
}
