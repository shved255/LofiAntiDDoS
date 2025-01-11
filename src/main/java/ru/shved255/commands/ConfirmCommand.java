package ru.shved255.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.shved255.Listeners;
import ru.shved255.Main;

public class ConfirmCommand implements CommandExecutor {

	private Main plugin;
	
	public ConfirmCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Listeners listeners = plugin.getListener();
        if(!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        if(!(args.length > 0)) {
            return true;
        }
        if(listeners == null) return true;
        listeners.onPlayerConfirm(player, args[0]); 	
        return true;
    };
	
}
