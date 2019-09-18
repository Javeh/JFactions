package io.github.javeh.jfac.faction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.javeh.jfac.JFac;

public class PowerManager {
	static private HashMap<Player, Double> powerMap = new HashMap<Player,Double>(); 
	static private HashMap<String, Double> uuidPowerMap = new HashMap<String, Double>();
	public static void addPower(Player p, double diff, boolean sendMessage) {
		double power;
		if(powerMap.get(p) == null) {
			power = 0;
		}
		else {
			power = powerMap.get(p);
		}
		powerMap.put(p, power+diff);
		uuidPowerMap.put(p.getUniqueId().toString(), power+diff);
		if(sendMessage) {
		sendPowerMessage(p,diff);
		}
		}
	public static void addPower(Player p, double diff) {
		addPower(p,diff, true);
	}
	public static void setLevel(Player p, double power) {
		powerMap.put(p, power);
		uuidPowerMap.put(p.getUniqueId().toString(), power);
		
	}
	public static Double getPower(Player p) {
		if(powerMap.get(p) == null) {
			return 0.0;
		}
		else {
			return powerMap.get(p);
		}
		
	}
	
	@Deprecated
	public static void convertStringToPlayer(String uuid) {
		powerMap.put(Bukkit.getPlayer(uuid), uuidPowerMap.get(uuid));
	}
	public static void convertUUIDToPlayer(Player p) {
		powerMap.put(p,uuidPowerMap.get(p.getUniqueId().toString()));
	}
	public static void downloadPower() {
		try {
			ResultSet result = JFac.getDB().getResult("players");
			while(result.next()) {
				uuidPowerMap.put(result.getString("uuid"), Double.parseDouble(result.getString("power")));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void sendPowerMessage(Player p, double diff) {
		String sign = "";
		if(diff < 0) {
			sign = ChatColor.RED+"";
		}
		else {
			sign = ChatColor.GREEN+"+";
		}
		String power = PowerManager.getPower(p).toString();
		String powerLevel = power.substring(0, 2+ power.indexOf("."));
		p.sendMessage(sign+ChatColor.BOLD+diff+ " ("+powerLevel + ")");
	}
	
	@Deprecated
	public static double sumPower(Player[] players) {
		return 0;
	}
}
