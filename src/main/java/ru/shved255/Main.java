package ru.shved255;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ru.shved255.commands.ConfirmCommand;

public class Main extends JavaPlugin {
	
	private final Map<String, Integer> timer = new ConcurrentHashMap<>();
	
	@SuppressWarnings("unused")
	private static Main inst;
	private Listeners listeners;
	private Config config;
	
	@Override
	public void onLoad() {
    	(inst = this).saveDefaultConfig();
	}
    
    public Config config() {
    	return this.config;
    }
    
    public Listeners listener() {
    	return this.listeners;
    }
	
    @Override
	public void onEnable() {
		config = new Config(this);
	    File cfg = new File(getDataFolder() + File.separator + "config.yml"); {
	    if(!cfg.exists()) {
	    	saveDefaultConfig();
	    	}
	    }
	    listeners = new Listeners(this);
	    Bukkit.getPluginManager().registerEvents(listeners, this);
	    getCommand("confirm").setExecutor(new ConfirmCommand(this));
	  }
    
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}
	
	public Listeners getListener() {
		return this.listeners;
	}
	    
	public Map<String, Integer> getTimer() {
		return this.timer;
	}
	
	public Integer getTimer1(String nick) {
		if(timer.containsKey(nick)) {
			return timer.get(nick);
		} else throw new IllegalStateException("nick not in map.");
	}
}

