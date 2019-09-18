package io.github.javeh.jfac.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.faction.FactionManager;

public class WipePlayersCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(arg3.length > 0){
			FactionManager.getFactionFromID(Integer.parseInt(arg3[0])).wipePlayers();
			return true;
			}
		return false;
}
	
}
