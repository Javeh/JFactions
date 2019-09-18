package io.github.javeh.jfac.world;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.faction.Faction;
import io.github.javeh.jfac.faction.FactionManager;

public class JChunk {
	private int x;
	private int z;
	private World world;
	private ArrayList<Tag> tagList = new ArrayList<Tag>();

//TODO implement worlds
	public JChunk(int xCoord, int zCoord, World w) {
		this.x = xCoord;
		this.z = zCoord;
		this.world = w;
	}
	public JChunk(int xCoord, int zCoord, String uid) { //Just like the last constructor but it takes a string
		this.x = xCoord;
		this.z = zCoord;
		this.world = Bukkit.getWorld(UUID.fromString(uid));
	}

	public JChunk(Chunk c) { //Chunk Object
		x = c.getX();
		z = c.getZ();
		world = c.getWorld();
	}

	public JChunk(Location l) { //Location object
		x = l.getChunk().getX();
		z = l.getChunk().getZ();
		world = l.getWorld();
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public void setX(int xCoord) {
		x = xCoord;
	}

	public void setY(int zCoord) {
		z = zCoord;
	}

	public String toString() {
		// return x + "," + z + " , "+world.getName();
		return x + "," + z;
	}

	public ArrayList<JChunk> getTouching() {
		ArrayList<JChunk> touching = new ArrayList<JChunk>();
		touching.add(new JChunk(x + 1, z, world));
		touching.add(new JChunk(x - 1, z, world));
		touching.add(new JChunk(x, z + 1, world));
		touching.add(new JChunk(x, z - 1, world)); // cardinal directions

		touching.add(new JChunk(x - 1, z - 1, world));
		touching.add(new JChunk(x - 1, z + 1, world));
		touching.add(new JChunk(x + 1, z + 1, world));
		touching.add(new JChunk(x + 1, z - 1, world)); // Diagonals

		return touching;
	}

	@Override
	public int hashCode() {
		// return x + z + world.hashCode();
		return x + z;
	}

	@Override
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
		JChunk compareChunk = (JChunk) obj;
		return compareChunk.getX() == x && compareChunk.getZ() == z && compareChunk.getWorld().equals(world);
	}

	public Faction getFaction() {
		return FactionManager.getChunkFaction(this);
	}

	// Tag Management
	public void addTag(Tag tag) {
		tagList.add(tag);
	}

	public void removeTag(Tag tag) {
		tagList.remove(tag);
	}

	public boolean isPVPEnabled() {
		return getFaction().isPVPEnabled();
	}
	public String getWorldUIDString() {
		return world.getUID().toString();
	}
	public UUID getWorldUID(){
		return world.getUID();
	}
	public String getWorldName() {
		return world.getName();
	}
	public World getWorld() {
		return world;
	}
	public void setClaimant(Player p) { //TODO
		
	}
}
