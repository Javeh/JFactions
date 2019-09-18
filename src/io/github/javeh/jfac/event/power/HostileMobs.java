package io.github.javeh.jfac.event.power;

import java.util.ArrayList;

import org.bukkit.entity.EntityType;

public class HostileMobs {
	private static ArrayList<EntityType> hostileMobs = new ArrayList<EntityType>();
	public static void init() { //Unfinished
		hostileMobs.add(EntityType.CREEPER);
		hostileMobs.add(EntityType.BLAZE);
		hostileMobs.add(EntityType.DROWNED);
		hostileMobs.add(EntityType.ELDER_GUARDIAN);
		hostileMobs.add(EntityType.ENDERMITE);
		hostileMobs.add(EntityType.ENDERMAN);
		hostileMobs.add(EntityType.PHANTOM);
		hostileMobs.add(EntityType.EVOKER);
		

	}
}
