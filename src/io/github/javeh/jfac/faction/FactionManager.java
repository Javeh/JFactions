package io.github.javeh.jfac.faction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.JFac;
import io.github.javeh.jfac.JLogger;
import io.github.javeh.jfac.world.JChunk;

public class FactionManager {
	static HashMap<String, Faction> uuidMap = new HashMap<String, Faction>();
	static HashMap<JChunk, Faction> chunkMap = new HashMap<JChunk, Faction>();
	static ArrayList<Faction> factions = new ArrayList<Faction>();
	static HashMap<Integer, Faction> factionIDMap = new HashMap<Integer, Faction>();
	static Faction neutral = new Faction("The Neutral Zone", "The People", -1);

	public static void loadData() {
		ResultSet result;
		try {
			result = JFac.getDB().getResult("factions");
			int count = 0;
			while (result.next()) {

				factions.add(new Faction(result.getString("name"), result.getString("leader"), result.getInt("id"), result.getString("color")));
				// factionIDMap.put(new Integer(factions.get(count).getID()),
				// factions.get(count));
				if (!isFieldEmpty(result.getString("power"))) {
					factions.get(count).addPower(Integer.parseInt(result.getString("power")));
				}
				String[] playerList = null;

				if (!isFieldEmpty(result.getString("players"))) { // Need to make sure these fields are not null
					playerList = result.getString("players").split(",");
					for (String s : playerList) {
						// uuidMap.put(s, factions.get(count)); //Add each faction to the UUID map
						factions.get(count).addPlayer(s); // Add the player to the faction's list
					}
				}
				downloadFactions(result, "chunks", factions.get(count));

				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateChunkList(JChunk chunk, Faction faction) {
		chunkMap.remove(chunk);
		chunkMap.put(chunk, faction);

	}
	public static void clearChunk(JChunk chunk) {
		chunkMap.remove(chunk);
	}
	public static void createFaction(String name,String color,Player founder) {
		factions.add(new Faction(name, founder.getUniqueId().toString(), factions.size()+1, color));
		founder.sendMessage(ChatColor.GREEN+"Founded faction " + factions.get(factions.size()).getColoredName());
	}

	public static Faction getFaction(int index) {
		return factions.get(index);
	}

	public static Faction getFactionFromID(int id) {
		return factionIDMap.get(id);
	}

	public static Faction getChunkFaction(JChunk chunk) {
		if (chunkMap.get(chunk) == null) {
			return neutral;
		} else {
			return chunkMap.get(chunk);
		}
	}

	public static Faction getPlayerFaction(Player player) {
		return uuidMap.get(player.getUniqueId().toString());
	}

	public static void updatePlayerFaction(String uuid, Faction f) {
		if (uuidMap.get(uuid) != null) {
			uuidMap.get(uuid).removePlayer(uuid);
		}
		uuidMap.put(uuid, f);
	}
	public static void clearPlayerFaction(String uuid) {
		uuidMap.remove(uuid);
	}
	public static void clearPlayerFaction(Player p) {
		clearPlayerFaction(p.getUniqueId().toString());
	}

	public static void updatePlayerFaction(Player p, Faction f) {
		updatePlayerFaction(p.getUniqueId().toString(), f);
	}

	public static Faction getNeutral() {
		return neutral;
	}

	public static ArrayList<Faction> getFactions() {
		return factions;
	}

	public static void updateIDMap(int id, Faction f) {
		factionIDMap.put(id, f);
	}

	@Deprecated
	private static boolean checkNullString(String s) {
		boolean cond = s != null && !s.equalsIgnoreCase("null") && !s.equals("");
		if (!cond) {
			JLogger.info("ERROR! Invalid string!");
			JLogger.info(s);
		}
		return cond;
	}

	private static boolean isFieldEmpty(String s) {
		if (s.equals("EMPTY")) {
			return true;
		} else {
			return false;
		}
	}

	public static void downloadFactions(ResultSet result, String world, Faction f)
			throws NumberFormatException, SQLException {
		String[] chunkList = null;
		if (!isFieldEmpty(result.getString("chunks"))) {
			// Integer tempX = null;
			// Integer tempY = null;
			chunkList = result.getString("chunks").split("~"); // load chunks into memory
			// We need to split this up into x and y
			for (String s : chunkList) {// Add chunks to the factions and factions to the chunk map
				String[] chunkPair = s.split(",");
				JLogger.info("Found Chunk: " + chunkPair[0] + "," + chunkPair[1]);
				if (chunkPair.length > 0 && chunkPair[0].contains("-")) {
					// chunkPair[0] = chunkPair[0].substring(1);
				}
				if (chunkPair.length > 1 && chunkPair[1].contains("-")) {
					// chunkPair[1] = chunkPair[1].substring(1);
				}
				if (chunkPair.length > 2 && !chunkPair[0].equals("") && !chunkPair[1].equals("")) {

					JChunk chunk = new JChunk(Integer.parseInt(chunkPair[0]), Integer.parseInt(chunkPair[1]),
							chunkPair[2]);
					// factions.get(count).addChunk(chunk);
					f.addChunk(chunk);
				}

			}

		}
	}
	public static boolean isPlayerInFaction(String uuid) {
		if(uuidMap.containsKey(uuid)) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean isPlayerInFaction(Player p) {
		return isPlayerInFaction(p.getUniqueId().toString());
	}

	public static void removeFaction(Faction faction) {
		factions.remove(faction);
	}
}