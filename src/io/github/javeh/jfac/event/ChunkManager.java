package io.github.javeh.jfac.event;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.faction.Faction;
import io.github.javeh.jfac.faction.FactionManager;
import io.github.javeh.jfac.world.JChunk;

public class ChunkManager {
static private HashMap<Player, JChunk> chunkList = new HashMap<Player,JChunk>();
static private HashMap<Player, Faction> playerTerritory = new HashMap<Player,Faction>();
public static boolean isNewChunk(Player p) {
	if(chunkList.get(p) != null) {
		return !chunkList.get(p).equals(new JChunk(p.getLocation()));

	}
	else {
		if(p.getName().equalsIgnoreCase("MrJaveh")) {
			p.sendMessage("Chunk is null!");
		}
		return false;
	}
}
public static boolean isNewTerritory(Player p) {
	
	//return playerTerritory.get(p) != getPlayerTerritory(p);
	//return !playerTerritory.get(p).equals(getPlayerTerritory(p));
	if(playerTerritory.get(p) != null) {
	return playerTerritory.get(p).getID() != getPlayerTerritory(p).getID();
}
	else {
		if(p.getName().equalsIgnoreCase("MrJaveh")) {
			p.sendMessage("Territory is null!");
		}
		return false;
	}
	}
public static JChunk getLast(Player p) {
//	return new JChunk(p.getLocation().getChunk().getX(),p.getLocation().getChunk().getZ());
	return new JChunk(p.getLocation());
}

public static void updateLastChunk(Player player) {
	chunkList.put(player, new JChunk(player.getLocation().getChunk()));
}
public static void updatePlayerTerritory(Player player) {
	playerTerritory.put(player, FactionManager.getChunkFaction(new JChunk(player.getLocation())));
}
public static Faction getPlayerTerritory(Player player) {
	return FactionManager.getChunkFaction(new JChunk(player.getLocation()));
}
}
	