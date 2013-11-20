package me.mikufr.minehead;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin 
{
	static final Logger logger = Logger.getLogger("Minecraft");
	
	/* Colors */
	static ChatColor reset = ChatColor.RESET;
	static ChatColor white = ChatColor.WHITE;
	static ChatColor red = ChatColor.RED;
	static ChatColor green = ChatColor.GREEN;
	static ChatColor aqua = ChatColor.AQUA;
	
	/* Prefix */
	static String chatPrefix = aqua + "[MineHead] ";
	static String errorPrefix = chatPrefix + red;
	static String correctPrefix = chatPrefix + green;
	
	/* Error Messages */
	static String usage = errorPrefix + "Usage: ";
	static String tooManyArgs = errorPrefix + "Too many args!";
	static String notEnoughArgs = errorPrefix + "Not enough args!";
	static String playerOnly = errorPrefix + "This command is only for players!";
	static String noPermission = errorPrefix + "You don't have permission!";
	
	public void log(String msg) 
	{
		Logger.getLogger("Minecraft").info("[" + getDescription().getName() + "] " + msg);
	}
	
	public void onEnable() 
	{
		saveDefaultConfig();
		log(green + "Plugin Enabled!");
	}
	
	public void onDisable() 
	{
		log(red + "Plugin Disabled!");
	}
	
	@SuppressWarnings("deprecation")
	void givePlayerSkull(Player player, String skullOwner) 
	{
		ItemStack CustomSkull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta CustomSkullMeta = (SkullMeta) CustomSkull.getItemMeta();
		CustomSkullMeta.setOwner(skullOwner);
		CustomSkull.setItemMeta(CustomSkullMeta);
		player.getInventory().addItem(CustomSkull);
		player.updateInventory();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player player = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("head")) 
		{
			if (sender instanceof Player) 
			{
				if (player.hasPermission("minehead.head"))
				{
					if (args.length == 0)
					{
						player.sendMessage(notEnoughArgs);
						player.sendMessage(usage + "/head <headname>");
						return true;
					}
				
					else if (args.length > 1)
					{
						player.sendMessage(tooManyArgs);
						player.sendMessage(usage + "/head <headname>");
						return true;
					}
				
					else if (args.length == 1)
					{
						givePlayerSkull(player, args[0]);
						player.sendMessage(correctPrefix + "Successfully spawned the head of " + reset + args[0]);
						return true;
					}
				}
				
				else
				{
					player.sendMessage(noPermission);
					return true;
				}
			}
			
			else
			{
				sender.sendMessage(playerOnly);
				return true;
			}
		}
		
		else if (cmd.getName().equalsIgnoreCase("givehead")) 
		{
			if (sender instanceof Player) 
			{
				if (player.hasPermission("minehead.give"))
				{
					if (args.length < 2)
					{
						player.sendMessage(notEnoughArgs);
						player.sendMessage(usage + "/givehead <headname> <playername>");
						return true;
					}
				
					else if (args.length > 2)
					{
						player.sendMessage(tooManyArgs);
						player.sendMessage(usage + "/givehead <headname> <playername>");
						return true;
					}
				
					else if (args.length == 2)
					{
						Player skullReceiver = Bukkit.getServer().getPlayerExact(args[1]);
						givePlayerSkull(skullReceiver, args[0]);
						player.sendMessage(correctPrefix + "Successfully gived the head of " + reset + args[0] + green + " to " + reset + args[1]);
						return true;
					}
				}
				
				else
				{
					player.sendMessage(noPermission);
					return true;
				}
			}
			
			else
			{
				sender.sendMessage(playerOnly);
				return true;
			}
		}
		
		else if (cmd.getName().equalsIgnoreCase("minehead")) 
		{
				if (player.hasPermission("minehead.info"))
				{
					if (args.length == 0)
					{
						sender.sendMessage(aqua + "***************");
						sender.sendMessage(white + "Plugin: " + green + "MineHead");
						sender.sendMessage(white + "Version: " + green + getDescription().getVersion());
						sender.sendMessage(white + "Author: " + green + "MikuFR");
						sender.sendMessage(green + "Use" + white + " /minehead reload " + green + "to reload the configuration");
						sender.sendMessage(aqua + "***************");
						return true;
					}
				
					else if (args.length > 1)
					{
						player.sendMessage(tooManyArgs);
						player.sendMessage(usage + "/minehead [reload]");
						return true;
					}
				
					else if (args.length == 1)
					{
						reloadConfig();
						sender.sendMessage(correctPrefix + "Configuration reloaded!");
						return true;
					}
				}
				
				else
				{
					player.sendMessage(noPermission);
					return true;
				}
		}
		
		return true;
	}
	
}