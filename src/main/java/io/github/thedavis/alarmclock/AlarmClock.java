package io.github.thedavis.alarmclock;

import java.util.Collection;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AlarmClock extends JavaPlugin {
	
	private static final int DURATION_ARG = 0;
	private static final int TICKS_FOR_ONE_MINUTE = 20;

	@Override
	public void onEnable(){
		getLogger().info("AlarmClock has been enabled!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("AlarmClock has been disabled, all alarms will be canceled.");
		this.getServer().getScheduler().cancelTasks(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("setalarm")){
			return setAlarm(sender, cmd, label, args);
		} else if(cmd.getName().equalsIgnoreCase("setkickalarm")){
			return setKickAlarm(sender, cmd, label, args);
		}
		
		return false;
	}
	
	private boolean setAlarm(CommandSender sender, Command cmd, String label, String[] args){
		boolean result = false;
		if(!(sender instanceof Player)){
			sender.sendMessage("This command is for players only.");
		} else if (args.length != 2){
			sender.sendMessage("Incorrect number of arguments. Usage: /setalarm [duration in minutes] [true|false]");
		} else {
			try{
				long duration = getDuration(args);
				result = runAlarm(duration, false, this.getServer().getOnlinePlayers());
				if(result){
					this.getServer().broadcastMessage("Alarm set for "+duration+" minutes");
				} else {
					this.getServer().broadcastMessage("Failed to set alarm for "+duration+" minutes");
				}
			} catch (NumberFormatException nfe){
				sender.sendMessage("Invalid duration. Usage: /setalarm [duration in minutes] [true|false]");
				result = false;
			}
		}
		
		return result;
	}
	
	private boolean setKickAlarm(CommandSender sender, Command cmd, String label, String[] args){
		boolean result = false;
		if(!(sender instanceof Player)){
			sender.sendMessage("This command is for players only");
		} else if (args.length != 1){
			sender.sendMessage("Incorrect number of arguments. Usage: /setKickAlarm [duration in minutes]");
		} else {
			try{
				long duration = getDuration(args);
				result = runAlarm(duration, true, this.getServer().getOnlinePlayers());
				if(result){
					this.getServer().broadcastMessage("All players will be kicked in "+duration+" minutes");
				} else {
					this.getServer().broadcastMessage("Failed to set kick alarm for "+duration+" minutes");
				}
			} catch (Exception ex){
				sender.sendMessage("Failed to set kick alarm due to exception: "+ex.getMessage());
				this.getLogger().log(Level.SEVERE, "Failed to set kick alarm", ex);
			}
		}
		return result;
	}
	
	private long getDuration(String[] args){
		return Long.parseLong(args[DURATION_ARG]);
	}
	
	private boolean runAlarm(long duration, boolean kickPlayers, Collection<? extends Player> players){
		AlarmClockTask alarmClock = new AlarmClockTask(this, duration, kickPlayers, getServer().getOnlinePlayers()); 
		alarmClock.runTaskTimer(this, 0, TICKS_FOR_ONE_MINUTE);
		return true;
	}
}
