package io.github.javeh.jfac.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NeutralZoneCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		Player player = (Player) sender;
		if(player.isOp()) {
		for(Player everyone: Bukkit.getOnlinePlayers()) {
			everyone.playSound(everyone.getLocation(),Sound.ENTITY_PLAYER_HURT,12,12);
		}
		Bukkit.broadcastMessage((ChatColor.LIGHT_PURPLE + "Now entering " +ChatColor.RED+""+ChatColor.BOLD+"The Neutral Zone"));
		}
		return true;
	}

}
