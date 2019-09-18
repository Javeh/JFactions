package io.github.javeh.jfac.faction;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.JLogger;
import io.github.javeh.jfac.world.JChunk;

public class Faction {
	private String name;
	private ArrayList<String> players = new ArrayList<String>();
	private ArrayList<String> deputies = new ArrayList<String>();
	private ArrayList<JChunk> chunks = new ArrayList<JChunk>();
	private String leaderID;
	private Faction enemy;
	private int id;
	private boolean pvpEnabled = false;
	private double storedPower = 0;
	private String color = ChatColor.WHITE + "";
	private String colorCode = "white";

	public Faction(String dbName, String dbLeaderID, int dbID) {
		dbName = dbName.replace("_", " ");
		name = dbName;
		leaderID = dbLeaderID;
		addPlayer(leaderID);
		id = dbID;
		FactionManager.updateIDMap(id, this);
	}

	/**
	 * 
	 * @param dbName       Faction's name, with no color.
	 * @param dbLeaderID   Faction leader UUID.
	 * @param dbID         Database ID for faction.
	 * @param factionColor Color string ie, "red".
	 */
	public Faction(String dbName, String dbLeaderID, int dbID, String factionColor) {
		this(dbName, dbLeaderID, dbID);
		factionColor = factionColor.toLowerCase();
		switch (factionColor) {
		case "dark_red":
			color = ChatColor.DARK_RED + "";
			colorCode = factionColor;
			break;
		case "red":
			color = ChatColor.RED + "";
			colorCode = factionColor;
			break;
		case "gold":
			color = ChatColor.GOLD + "";
			colorCode = factionColor;
			break;
		case "yellow":
			color = ChatColor.YELLOW + "";
			colorCode = factionColor;
			break;
		case "green":
			color = ChatColor.DARK_GREEN + "";
			colorCode = factionColor;
			break;
		case "dark_green":
			color = ChatColor.DARK_GREEN + "";
			colorCode = factionColor;
			break;
		case "aqua":
			color = ChatColor.AQUA + "";
			colorCode = factionColor;
			break;
		case "dark_aqua":
			color = ChatColor.DARK_AQUA + "";
			colorCode = factionColor;
			break;
		case "dark_blue":
			color = ChatColor.DARK_BLUE + "";
			colorCode = factionColor;
			break;
		case "blue":
			color = ChatColor.BLUE + "";
			colorCode = factionColor;
			break;
		case "light_purple":
			color = ChatColor.LIGHT_PURPLE + "";
			colorCode = factionColor;
			break;
		case "dark_purple":
			color = ChatColor.DARK_PURPLE + "";
			colorCode = factionColor;
			break;
		case "white":
			color = ChatColor.WHITE + "";
			colorCode = factionColor;
			break;
		case "gray": // Both spellings
			color = ChatColor.GRAY + "";
			colorCode = factionColor;
			break;
		case "grey":
			color = ChatColor.GRAY + "";
			colorCode = factionColor;
			break;
		case "dark_grey":
			color = ChatColor.DARK_GRAY + "";
			colorCode = factionColor;
			break;
		case "dark_gray":
			color = ChatColor.DARK_GRAY + "";
			colorCode = factionColor;
			break;
		case "black":
			color = ChatColor.BLACK + "";
			colorCode = factionColor;
			break;
		}

	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Faction fac = (Faction) obj;
		return fac.getID() == id && fac.getLeaderID().equals(leaderID);
	}

	public int hashCode() {
		return id;
	}

	public String getLeaderID() {
		return leaderID;
	}

	public void addPlayer(String playerUUID) {
		players.add(playerUUID);
		JLogger.info("Added " + playerUUID + " to " + getName());
		FactionManager.updatePlayerFaction(playerUUID, this);
	}

	public void addChunk(JChunk chunk) {
		chunks.add(chunk);
		FactionManager.updateChunkList(chunk, this);
		JLogger.info("added " + chunk.toString() + ", " + chunk.getWorldName() + " for " + getName());
	}

	public void removeChunk(JChunk chunk) {
		chunks.remove(chunk);
		FactionManager.updateChunkList(chunk, null);
	}

	public String getName() {
		return name;
	}

	public String getColoredName() {
		return color + name;
	}

	public Faction getEnemy() {
		return enemy;
	}

	public void declareWar(Faction state) {
		enemy = state;
	}

	public void makePeace(Faction state) {
		enemy = null;
	}

	public int getID() {
		return id;
	}

	public ArrayList<JChunk> getChunks() {
		return chunks;
	}

	public ArrayList<String> getPlayers() {
		return players;
	}

	public ArrayList<String> getOnlyPlayers() {
		ArrayList<String> onlyPlayers = new ArrayList<String>();
		for (String player : players) {
			onlyPlayers.add(player);
		}

		for (String deputy : deputies) {
			onlyPlayers.remove(deputy);
		}
		onlyPlayers.remove(leaderID);
		return onlyPlayers;
	}

	public void removePlayer(String uuid) {
		players.remove(uuid);
		FactionManager.updatePlayerFaction(uuid, null);
	}

	public void addDeputy(String uuid) {
		deputies.add(uuid);
	}

	public void addDeputy(Player p) {
		addDeputy(p.getUniqueId().toString());
	}

	public void removeDeputy(String uuid) {
		deputies.remove(uuid);
	}

	public void removeDeputy(Player p) {
		removeDeputy(p.getUniqueId().toString());
	}

	public void depositPower(Player p, double diff) {
		if (!(diff > 0)) {
			p.sendMessage(ChatColor.RED + "This needs to be positive!");
		} else {
			PowerManager.addPower(p, -diff);
			if (PowerManager.getPower(p) < 0) {
				storedPower += diff;
			} else {
				PowerManager.addPower(p, diff); // If we overdraft the power, cancel the transaction
				p.sendMessage(ChatColor.RED + "Overdrafted!");
			}
		}
	}

	public void withdrawPower(Player p, double diff) {
		if (storedPower < diff) {
			p.sendMessage(ChatColor.RED + "Not enough in the faction's storage!");
		} else {
			storedPower -= diff;
			PowerManager.addPower(p, diff);
		}
	}

	public void addPower(double diff) {
		storedPower += diff;
	}

	public void subtractPower(double diff) {
		storedPower -= diff;
	}

	/*
	 * @param chunk check to make sure this chunks is touching the border
	 */
	public boolean isTouching(JChunk chunk) {

		for (JChunk currentChunk : chunks) {
			if (currentChunk.getTouching().contains(chunk)) {
				return true;
			}
		}
		if (chunks.size() < 1) {
			return true; // return true if somehow there are no chunks in the faction
		}

		return false;

	}

	public boolean isPVPEnabled() {
		return pvpEnabled;
	}

	public void wipePlayers() {
		while (players.size() > 0) {
			FactionManager.clearPlayerFaction(players.get(0));
			players.remove(0);
		}
	}

	public void wipeChunks() {
		while (chunks.size() > 0) {
			FactionManager.clearChunk(chunks.get(0));
			chunks.remove(0);
		}
	}

	public double getPower() {
		return storedPower;
	}

	public void setLeader(String uuid) {
		leaderID = uuid;
	}

	public String getColorCode() {
		return colorCode;
	}

	public String getColor() {
		return color;
	}

	public void addPlayer(Player p) {
		addPlayer(p.getUniqueId().toString());
	}

	public boolean isPlayerLeader(String uuid) {
		return uuid.equals(leaderID);

	}

	public boolean isPlayerLeader(Player p) {
		return isPlayerLeader(p.getUniqueId().toString());
	}

	public boolean isPlayerMember(String uuid) {
		return players.contains(uuid);
	}

	public boolean isPlayerMember(Player p) {
		return isPlayerMember(p.getUniqueId().toString());
	}
	public boolean isPlayerDeputy(Player p) {
		return isPlayerDeputy(p.getUniqueId().toString());
	}
	public boolean isPlayerDeputy(String uuid) {
		return deputies.contains(uuid);
	}

	public boolean checkPermission(Player p, FactionPermission perm) {
	if(isPlayerLeader(p)) {
		return true;
	}
	if(isPlayerDeputy(p)) {
		if(perm == FactionPermission.INVITE) {
			return true;
		}
		if(perm == FactionPermission.KICK) {
			return true;
		}
		if(perm == FactionPermission.DECLAREWAR) {
			return true; //TODO make this toggleable
		}
		if(perm == FactionPermission.MAKEPEACE) {
			return true; //TODO make this toggleable
		}
	}
	return false;	
	}

	public void disband() {
		wipeChunks();
		wipePlayers();
		FactionManager.removeFaction(this);
	}

}
