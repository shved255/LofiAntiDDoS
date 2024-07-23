package ru.shved255;

import java.io.File;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {	  
	private Main plugin;
	private FileConfiguration cfg;
	private String message;
	private String wrongCodeMessage;
	private String successMessage;
	private int delay;
	private String successSubtitle;
	private String successsTitle;
	private String kickMessage;
	private String startTitle;
	private String startSubtitle;
	private String shift;
	
	public Config(Main plugin) {
		this.setPlugin(plugin);
		File file = new File(plugin.getDataFolder() + File.separator, "config.yml"); 
	    this.cfg = YamlConfiguration.loadConfiguration(file);
	    delay = cfg.getInt("Delay");
	    message = cfg.getString("Message");
        wrongCodeMessage = cfg.getString("WrongCodeMessage");
        successsTitle = cfg.getString("Titles.SuccessTitle");
        successSubtitle = cfg.getString("Titles.SuccessSubTitle");
        successMessage = cfg.getString("SuccessMessage");
        kickMessage = cfg.getString("KickMessage");
        startTitle = cfg.getString("Titles.StartTitle");
        startSubtitle = cfg.getString("Titles.StartSubTitle");
        shift = cfg.getString("ShiftMessage");
	}
	
	public Map<String, Integer> getTimer() {
		return plugin.getTimer();
	}
	
	public FileConfiguration getConfig() {
		return this.cfg;
	}
	
	public static String ChatColor(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public String getMessage(String nick) {
		return ChatColor(this.message.replace("{TIME}", String.valueOf(plugin.getTimer1(nick))));
	}
	
	public String getWrongCodeMessage(String nick) {
		return ChatColor(this.wrongCodeMessage);
	}
	
	public int getDelay() {
		return this.delay;
	}
	
	public String getSuccesssTitle(String nick) {
		return ChatColor(this.successsTitle);
	}
	
	public String getSuccessSubtitle(String nick) {
		return ChatColor(this.successSubtitle.replace("{TIME}", String.valueOf(plugin.getTimer1(nick))));
	}
	
	public String getKickMessage(String nick) {
		return ChatColor(this.kickMessage);
	}
	
	public String getStartTitle(String nick) {
		return ChatColor(this.startTitle);
	}
	
	public String getStartSubtitle(String nick) {
		return ChatColor(this.startSubtitle.replace("{TIME}", String.valueOf(plugin.getTimer1(nick))));
	}
	
	public String getSuccessMessage(String nick) {
		return ChatColor(this.successMessage);
	}
	
	public String getShift(String nick) {
		return ChatColor(this.shift.replace("{TIME}", String.valueOf(plugin.getTimer1(nick))));
	}

	public Main getPlugin() {
		return plugin;
	}

	public void setPlugin(Main plugin) {
		this.plugin = plugin;
	}
}
