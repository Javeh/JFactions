package io.github.javeh.jfac;

import org.bukkit.Bukkit;

public class JLogger {
	public static void printLogger(String message) {
		Bukkit.getLogger().info("[JFactions] "+message);
	}
	public static void info(String msg) {
		printLogger(msg);
	}
}
