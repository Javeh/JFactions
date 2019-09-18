package io.github.javeh.jfac.event;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ChunkMoveEvent implements Listener {
	private static HashMap<Player, Long> timeMap = new HashMap<Player, Long>();
	private static HashMap<Player, Block[]> blockMap = new HashMap<Player, Block[]>();

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(PlayerMoveEvent e) {
		//e.getPlayer().sendMessage(ChunkManager.getPlayerTerritory(e.getPlayer()).getName());
		if (ChunkManager.isNewChunk(e.getPlayer())) {
			// e.getPlayer().sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"You moved into a
			// new chunk");
			if (ChunkManager.isNewTerritory(e.getPlayer())) {
				e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Now entering " + ChatColor.RED + "" + ChatColor.BOLD
						+ ChunkManager.getPlayerTerritory(e.getPlayer()).getName());
				//e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, (float) 1, (float) 2);
			//	e.getPlayer().playSound(player.getLocation(),Sound.ENTITY_PLAYER_HURT,12,6);
				//e.getPlayer().playNote(e.getPlayer().getLocation(), Instrument.CHIME, Note.natural(4, Tone.A));
				//placeGlassBorder(player);
			}
		
			//removeGlassIfNeeded(player);
		}
		ChunkManager.updateLastChunk(e.getPlayer());
		ChunkManager.updatePlayerTerritory(e.getPlayer());
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Deprecated
	public static void placeGlassBorder(Player player) {
		timeMap.put(player, System.currentTimeMillis());
		Location[] chunkBorder = new Location[64];
		Block[] originalBlocks = new Block[64];
		for (int i = 0; i < 16; i++) { // bottom x axis
			chunkBorder[i] = new Location(player.getWorld(), (player.getLocation().getChunk().getX() * 16) +i,
					player.getLocation().getY(), (player.getLocation().getChunk().getZ()*16));
			originalBlocks[i] = chunkBorder[i].getBlock();

			}
		for (int i = 16; i < 32; i++) { // bottom x axis
			chunkBorder[i] = new Location(player.getWorld(), (player.getLocation().getChunk().getX() * 16),
					player.getLocation().getY(), (player.getLocation().getChunk().getZ()*16)-16 +i);
			originalBlocks[i] = chunkBorder[i].getBlock();

			} //working ^
		for (int i = 0; i < 32; i++) { // bottom x axis
			chunkBorder[i] = new Location(player.getWorld(), (player.getLocation().getChunk().getX() * 16) +16 -i,
					player.getLocation().getY(), (player.getLocation().getChunk().getZ()*16));
			originalBlocks[i] = chunkBorder[i].getBlock();

			}
		for (int i = 16; i < 64; i++) { // bottom x axis
			chunkBorder[i] = new Location(player.getWorld(), (player.getLocation().getChunk().getX() * 16),
					player.getLocation().getY(), (player.getLocation().getChunk().getZ()*16) -i);
			originalBlocks[i] = chunkBorder[i].getBlock();

			}
		
		
		/*
		for (int i = 0; i < 16; i++) { // top x axis
			chunkBorder[i + 16] = new Location(player.getWorld(), (player.getLocation().getChunk().getX() * 16) + i,
					player.getLocation().getY(), player.getLocation().getZ()*16 -i);
			originalBlocks[i + 16] = chunkBorder[i + 16].getBlock();
		} */

		for (Location l : chunkBorder) {
			player.sendBlockChange(l, Material.DIAMOND_BLOCK, (byte) 0);
		}
		blockMap.put(player, originalBlocks);
		timeMap.put(player, System.currentTimeMillis());

		

	}
	@Deprecated
	public static void removeGlassIfNeeded(Player player) {
		if (timeMap.get(player) > 5000) {
			for (Block b : blockMap.get(player)) {
				player.sendBlockChange(b.getLocation(), b.getType(), (byte) 0);
			}
		}
	}
}
