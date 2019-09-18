package io.github.javeh.jfac;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.javeh.jfac.command.AddPowerCommand;
import io.github.javeh.jfac.command.DevCommand;
import io.github.javeh.jfac.command.FCommand;
import io.github.javeh.jfac.command.NeutralZoneCommand;
import io.github.javeh.jfac.command.WipeChunksCommand;
import io.github.javeh.jfac.database.Database;
import io.github.javeh.jfac.event.ChunkMoveEvent;
import io.github.javeh.jfac.event.PlayerJoinMessageEvent;
import io.github.javeh.jfac.event.PlayerJoinToFactionEvent;
import io.github.javeh.jfac.event.PlayerLeaveUpdateEvent;
import io.github.javeh.jfac.event.power.PowerChangeMiningEvent;
import io.github.javeh.jfac.event.power.PowerChangeMobEvent;
import io.github.javeh.jfac.faction.Faction;
import io.github.javeh.jfac.faction.FactionManager;
import io.github.javeh.jfac.faction.PowerManager;


public class JFac extends JavaPlugin {
	static Database db = new Database();
	public void onEnable() {
	try {
		db.connect();
		System.out.println("Successfully connected to " + "SQL DB");
		if(connected) {
		db.createTables();
		FactionManager.loadData();
		PowerManager.downloadPower();
		}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		System.out.println("Connection failed");
		connected = false;
		e.printStackTrace();
		
		
	}
	for (Player p: Bukkit.getServer().getOnlinePlayers()) {
		PlayerJoinToFactionEvent.assignPlayerData(p);
	}
	this.getCommand("dev").setExecutor(new DevCommand());
	this.getCommand("f").setExecutor(new FCommand());
	this.getCommand("faction").setExecutor(new FCommand());
	this.getCommand("addpower").setExecutor(new AddPowerCommand());
	this.getCommand("neutralzone").setExecutor(new NeutralZoneCommand());
	this.getCommand("wipechunks").setExecutor(new WipeChunksCommand());
	//this.getCommand("refresh").setExecutor(new RefreshDBCommand());
	//this.getCommand("axe").setExecutor(new AxeCommand());
    this.getServer().getPluginManager().registerEvents(new ChunkMoveEvent(), this);
    this.getServer().getPluginManager().registerEvents(new PlayerJoinMessageEvent(), this);
    this.getServer().getPluginManager().registerEvents(new PlayerJoinToFactionEvent(), this);
    this.getServer().getPluginManager().registerEvents(new PlayerLeaveUpdateEvent(), this);
    this.getServer().getPluginManager().registerEvents(new PowerChangeMobEvent(), this);
    this.getServer().getPluginManager().registerEvents(new PowerChangeMiningEvent(), this);
	
	
	}
    @Override
    public void onDisable() {
    	if(connected) {
    	db.uploadAllPlayerData();
    	db.uploadAllFactionData();
    	}
    }
    public static Database getDB() {
    	return db;
    }
    private static boolean connected = true;
    public static boolean isConnected() {
    	return connected;
    }
}
