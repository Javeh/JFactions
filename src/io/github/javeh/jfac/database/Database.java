package io.github.javeh.jfac.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.JFac;
import io.github.javeh.jfac.JLogger;
import io.github.javeh.jfac.faction.Faction;
import io.github.javeh.jfac.faction.FactionManager;

public class Database {
	public Connection connection;
	/*
	public String host = "67.20.55.73";
	public String database = "apexMC88763";
	public String username = "apexMC88763";
	public String password = "matei";//"cba4d7c6b1";
	*/
	private String host = Config.HOST;
	private int port = Config.PORT;
	private String username = Config.USERNAME;
	private String password = Config.PASSWORD;
	private String database = Config.DATABASE;

	public static Statement stmt = null;

	public void connect() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
				this.username, this.password);
		stmt = connection.createStatement();
		if (connection != null && !connection.isClosed()) {
			return;
		}

		synchronized (this) {
			if (connection != null && !connection.isClosed()) {

				return;

			}

		}
	}

	public void createTables() {
		String factions = "CREATE TABLE IF NOT EXISTS factions("
				+ "name LONGTEXT, id INT, color TEXT, players LONGTEXT, chunks LONGTEXT, power DOUBLE, leader LONGTEXT )";
		String players = "CREATE TABLE IF NOT EXISTS  players("
				+ "uuid LONGTEXT, name LONGTEXT, in_faction LONGTEXT, faction LONGTEXT, power DOUBLE )";
		try {
			stmt.executeUpdate(factions);
			stmt.executeUpdate(players);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	public String getString(String table, String field) {
		return "";
	}

	public ResultSet getResult(String table) throws SQLException {
		return stmt.executeQuery("SELECT * FROM "+ table);
	}
	public static void uploadPlayerPower(Player p, Double power) {
		if(JFac.isConnected()) {
		String uuid = p.getPlayer().getUniqueId().toString();
		String name = p.getPlayer().getName();
		String deleteCommand = "DELETE FROM players \n WHERE uuid = '" +uuid+"';";
		String command = "INSERT INTO players (uuid, name, power) VALUES ( '" +uuid+"', '"+name+"' , '"+ power+"');";
		if(!(power == 0)) {
		try {
			printSQLCommand(command);
			stmt.executeUpdate(deleteCommand);
			stmt.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}	}
		}
		
	}
	public void uploadAllFactionData() {
		createTables();
		for(Faction fac: FactionManager.getFactions()) {
			uploadFactionData(fac);
		}
	}
	public static void uploadFactionData(Faction f) {
		if(JFac.isConnected()) {
		int id = f.getID();
		String name = f.getName();
		String players = "";
		String chunks = "";
		String leader = f.getLeaderID();
		double power = f.getPower();
		String color = f.getColorCode();
		JLogger.printLogger(("Updating data for faction: "+name));
		JLogger.info("chunks: " + f.getChunks().size());
		JLogger.info("players: " + f.getOnlyPlayers().size());
		for(int i = 0; i < f.getChunks().size(); i++) { //populate the chunks string
			chunks = chunks+f.getChunks().get(i).getX()+","+f.getChunks().get(i).getZ()+","+f.getChunks().get(i).getWorldUIDString();
			if(i != f.getChunks().size()-1) {
				chunks = chunks + "~";
			}
		}
		for(int i = 0; i < f.getOnlyPlayers().size(); i++) {
			players = players + f.getOnlyPlayers().get(i);
			if(i!= f.getOnlyPlayers().size() -1) {
				players = players + ",";
			}
		}
		if(players == null || players.equals(",") || players.contains("null") || players.equals("")) {
			players = "EMPTY";
		}
		if(chunks == null || chunks.equals("~") || chunks.contains("null") || chunks.equals("")) {
			chunks = "EMPTY";
		}
		
		JLogger.info(f.getName() + " uploaded these chunks: "+chunks);
		String deleteCommand = "DELETE FROM factions \n WHERE id = '" +id+"';";
	
		String command = "INSERT INTO factions (id, name, color, players, chunks, power, leader) VALUES ( '" +id+"', '"+ name+"' ,  " +"'"+color+"', '"+players+"' , '"+chunks+"' , '"+power+"' , '"+leader+"');";
		printSQLCommand(command);
		try {
				stmt.executeUpdate(deleteCommand);
				stmt.executeUpdate(command);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	} }
	private static void printSQLCommand(String s) {
		JLogger.info("[JFactions] running SQL command: "+s);
	}

	public void uploadAllPlayerData() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
    		JLogger.info("Updating Power Level for: "+p.getName());
    		//db.syncPlayerPower(p, PowerManager.getPower(p));
    	}		
	}
}
