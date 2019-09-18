package io.github.javeh.jfac.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.JFac;
import io.github.javeh.jfac.event.power.PowerConstants;
import io.github.javeh.jfac.faction.Faction;
import io.github.javeh.jfac.faction.FactionManager;
import io.github.javeh.jfac.faction.PowerManager;
import io.github.javeh.jfac.world.JChunk;

public class FCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (!JFac.isConnected()) {
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "JFactions could not connect to database!");
		} else {
			if (arg3.length > 0 && arg3[0].equalsIgnoreCase("claim")) {
				claimChunk(player);
			} else if (arg3.length > 0 && arg3[0].equalsIgnoreCase("list")) {
				sendList(player);
			} else if (arg3.length > 2 && arg3[0].equalsIgnoreCase("place")) {
				FactionManager.getFactionFromID(Integer.parseInt(arg3[2]))
						.addPlayer(Bukkit.getPlayer(arg3[1]).getUniqueId().toString());
				player.sendMessage(
						FactionManager.getPlayerFaction(Bukkit.getPlayer(arg3[1])).toString() + " contains " + arg3[1]);
			}
			// /f found <name> <color>
			else if (arg3.length > 0 && arg3[0].equalsIgnoreCase("found")) {
				foundCommand(player, arg3);
			} else {
				baseCommand(player);
			}
		}
		return true;
	}

	public void claimChunk(Player player) {
		JChunk chunk = new JChunk(player.getLocation());
		if (FactionManager.getPlayerFaction(player) == null) {
			player.sendMessage(ChatColor.RED + "You're not in a faction!");
		} else if (!FactionManager.getPlayerFaction(player).isTouching(chunk)) {
			player.sendMessage(
					ChatColor.RED + "That's an illegal claim! You need to be bordering on your own faction's chunks.");
		} else if (PowerManager.getPower(player) < PowerConstants.CLAIM_COST) {
			player.sendMessage(ChatColor.RED + "You need " + (PowerConstants.CLAIM_COST - PowerManager.getPower(player))
					+ " more power!");
		} else if (FactionManager.getChunkFaction(chunk).equals(FactionManager.getNeutral())
				&& PowerManager.getPower(player) >= PowerConstants.CLAIM_COST) {

			FactionManager.getPlayerFaction(player).addChunk(chunk);
			player.sendMessage(ChatColor.GREEN + "Claimed Chunk " + chunk.toString() + ".");
			PowerManager.addPower(player, -PowerConstants.CLAIM_COST);
		} else {
			player.sendMessage(ChatColor.RED + "Can't claim here, it belongs to "
					+ FactionManager.getChunkFaction(chunk).getName());

		}
	}

	public void sendList(Player player) {
		for (Faction f : FactionManager.getFactions()) {
			player.sendMessage(f.getColoredName()	 + ChatColor.GREEN+" - " + ChatColor.BLUE + f.getID());
		}
	}

	public void baseCommand(Player player) {
		player.sendMessage(ChatColor.GREEN + "You are currently in:");
		player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
				+ FactionManager.getChunkFaction(new JChunk(player.getLocation())).getName());// ChunkManager.getPlayerTerritory(player).getName());
		player.sendMessage(ChatColor.GREEN + "You are a member of:");
		if (FactionManager.getPlayerFaction(player) == null) {
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "No Faction");
		} else {
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + FactionManager.getPlayerFaction(player).getName());
		}
		player.sendMessage(ChatColor.GREEN + "Your power level is:");
		String power = PowerManager.getPower(player).toString();
		String powerLevel = power.substring(0, 2 + power.indexOf("."));
		player.sendMessage(ChatColor.RED + "" + powerLevel);
	}

	public void foundCommand(Player p, String[] arg) {
		boolean validColor = true;
		if (arg.length == 1) {
			p.sendMessage(ChatColor.GREEN + "The syntax works like this:");
			p.sendMessage(ChatColor.GREEN + "/f found" + ChatColor.AQUA + " NAME " + ChatColor.GOLD + "Color");
			p.sendMessage(ChatColor.BOLD + "OR");
			p.sendMessage(ChatColor.GREEN + "/f found" + ChatColor.AQUA + " SPACED_NAME " + ChatColor.GOLD + "Color");
			p.sendMessage(ChatColor.UNDERLINE + "The colors are as follows:");
			p.sendMessage(ChatColor.AQUA + "Aqua " + ChatColor.BLACK + "Black " + ChatColor.BLUE + "Blue "
					+ ChatColor.DARK_AQUA + "Dark_Aqua " + ChatColor.DARK_BLUE + "Dark_Blue " + ChatColor.DARK_GRAY
					+ "Dark_GRAY " + ChatColor.DARK_GREEN + "Dark_Green " + ChatColor.DARK_PURPLE + "Dark_Purple "
					+ ChatColor.DARK_RED + "Dark_Red " + ChatColor.GOLD + "Gold " + ChatColor.GRAY + "Gray "
					+ ChatColor.GREEN + "Green " + ChatColor.LIGHT_PURPLE + "Light_Purple " + ChatColor.RED + "Red "
					+ ChatColor.WHITE + "White " + ChatColor.YELLOW + "Yellow");
		} else if (arg.length == 3) {
			arg[2] = arg[2].toLowerCase();
			switch (arg[2]) {
			case "dark_red":
				break;
			case "red":
				break;
			case "gold":
				break;
			case "yellow":
				break;
			case "green":
				break;
			case "dark_green":
				break;
			case "aqua":
				break;
			case "dark_aqua":
				break;
			case "dark_blue":
				break;
			case "blue":
				break;
			case "light_purple":
				break;
			case "dark_purple":
				break;
			case "white":
				break;
			case "gray": // Both spellings
				break;
			case "grey":
				break;
			case "dark_grey":
				break;
			case "dark_gray":
				break;
			case "black":
				break;
			default:
				p.sendMessage(ChatColor.RED+"Invalid color!");
				validColor = false;
			}
			if(validColor) {
			if(PowerManager.getPower(p) < PowerConstants.FOUNDING_COST) {
				double diff = PowerConstants.FOUNDING_COST - PowerManager.getPower(p);
				p.sendMessage(ChatColor.RED+"You don't have enough for this! You need "+diff+" more!");
			}
			else {
				FactionManager.createFaction(arg[1], arg[2], p);
			}
			}
		}
	}
	
	public void leaveCommand(Player p) {
		if(FactionManager.getPlayerFaction(p) != null) {
			Faction f = FactionManager.getPlayerFaction(p);
			if(f.isPlayerMember(p)) {
				if(!f.isPlayerLeader(p)) {
					p.sendMessage(ChatColor.GREEN+ "Leaving "+FactionManager.getPlayerFaction(p).getColoredName());
					
				}
				else {
					p.sendMessage(ChatColor.RED+"You're the leader! You have to use "+ChatColor.GREEN+"/f "+ChatColor.GOLD+"disband");
				}
		}
	}
}
	public void disbandCommand(Player p) {
		if(FactionManager.getPlayerFaction(p) != null) {
			Faction f = FactionManager.getPlayerFaction(p);
			if(f.isPlayerLeader(p)) {
			f.disband();
		}else {
			p.sendMessage("You aren't the leader! You can't do that");
		}
			}
	}
}
