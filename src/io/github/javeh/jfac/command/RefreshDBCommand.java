package io.github.javeh.jfac.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.event.PlayerJoinToFactionEvent;

public class RefreshDBCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		PlayerJoinToFactionEvent.assignPlayerData(player);
		return true;
	}

}
