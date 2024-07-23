package ru.shved255;

import java.util.HashMap;
import java.util.Random;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Listeners implements Listener {
	
  private final HashMap<Player, String> codeMap = new HashMap<>();
  private final HashMap<Player, Integer> var = new HashMap<>();
  private final HashMap<Player, Integer> var2 = new HashMap<>();
  private final HashMap<Player, Integer> onProverka = new HashMap<>();
  private final HashMap<Player, Integer> noProverka = new HashMap<>();
  private Main plugin;
	
	public Listeners(Main plugin) {
		this.plugin = plugin;
	}
	
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    String nick = player.getName();
    onProverka.put(player, 1);
    int timer2 = plugin.config().getDelay();
    plugin.getTimer().put(nick, timer2);
    String code = generateCode();
    String title = plugin.config().getStartTitle(nick);
    String subtitle = plugin.config().getStartSubtitle(nick);  
	Bukkit.getScheduler().runTaskLater(plugin, () -> { 
	player.kickPlayer(plugin.config().getKickMessage(nick));
	}, timer2 * 20);
    player.sendTitle(title, subtitle, 10, 70, 20);
    var2.put(player, 1);    
    Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
    if(onProverka.containsKey(player)) {
    plugin.getTimer().put(nick, plugin.getTimer().get(nick) - 1);
    if(var2.containsKey(player)) {
        String Shift = plugin.config().getShift(nick);
        player.sendMessage(Shift);	
    }

    if(var.containsKey(player)) {
    this.codeMap.put(player, code);
    String messageText = plugin.config().getMessage(nick);
    TextComponent message = new TextComponent(messageText);
    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/confirm " + code));
    (new BukkitRunnable() {
        public void run() {
          if (Listeners.this.codeMap.containsKey(player))
            player.spigot().sendMessage((BaseComponent)message); 
        }
      }).runTaskTimer(plugin, 0L, 60L);
    }
    }
   }, 5, 20 * 1);
    (new BukkitRunnable() {
        public void run() {
        if(var.containsKey(player)) {
          if (Listeners.this.codeMap.containsKey(player)) {
            String kickMessage = plugin.config().getKickMessage(nick);
            player.kickPlayer(kickMessage);
            codeMap.remove(player);
            var.remove(player);
            onProverka.remove(player);
            var2.remove(player);
            noProverka.put(player, 1);
          			} 
        		}
            }
      }).runTaskLater(plugin, 20L * timer2);
  }
  public void onPlayerConfirm(Player player, String code) {
	Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
    if (this.onProverka.containsKey(player))
      if (((String)this.codeMap.get(player)).equals(code)) {
    	String nick = player.getName();
        String successMessage = plugin.config().getSuccessMessage(nick);
        player.sendMessage(successMessage);
        this.codeMap.remove(player);
        this.var.remove(player);
        this.onProverka.remove(player);
        this.var2.remove(player);
        noProverka.put(player, 1);
        String title = plugin.config().getSuccesssTitle(nick);
        String subtitle = plugin.config().getSuccessSubtitle(nick);
        player.sendTitle(title, subtitle, 10, 70, 20);
      } else {
    	String nick = player.getName();
        String wrongCodeMessage = plugin.config().getWrongCodeMessage(nick);
        player.kickPlayer(wrongCodeMessage);
        this.codeMap.remove(player);
        this.var.remove(player);
        this.onProverka.remove(player);
        this.var2.remove(player);
        noProverka.put(player, 1);
      }
    }, 5, 20 * 1);
    Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
    if(noProverka.containsKey(player)) {
    String nick = player.getName();	
    noProverka.remove(player);
    plugin.getTimer().remove(nick);
    Bukkit.getScheduler().cancelTasks(plugin);
    	}
    }, 5, 20 * 1);   
  }
  private String generateCode() {
    int code = (new Random()).nextInt(999999);
    return String.format("%06d", new Object[] { Integer.valueOf(code) });
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
	  Player player = event.getPlayer();
      codeMap.remove(player);
      var.remove(player);
      onProverka.remove(player);
      var2.remove(player);
      noProverka.put(player, 1);
  }
  
  @EventHandler
  public void onInventoryOpen(InventoryOpenEvent event) {
    Player player = (Player)event.getPlayer();
	if(onProverka.containsKey(player)) {
      event.setCancelled(true); 
	}
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
	    Player player = (Player)event.getPlayer();
		if(onProverka.containsKey(player)) {
      event.setCancelled(true); 
		}
  }
  
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
	Player player = (Player)event.getPlayer();
	if(onProverka.containsKey(player)) {
      event.setCancelled(true); 
	}
  }
  
  @EventHandler
  public void onPlayerChat(AsyncPlayerChatEvent event) {
	Player player = (Player)event.getPlayer();
	if(onProverka.containsKey(player)) {
      event.setCancelled(true); 
	}
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (event.getWhoClicked() instanceof Player) {
      Player player = (Player)event.getWhoClicked();
  	if(onProverka.containsKey(player)) {
        event.setCancelled(true); 
  		}
    } 
  }
  @EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent event) {
  	Player player = event.getPlayer();
  	var.put(player, 60);
  	var2.remove(player);
  	}
}
