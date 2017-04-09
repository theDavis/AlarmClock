package io.github.thedavis.alarmclock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AlarmClock extends JavaPlugin {
	
	private static final int DURATION_ARG = 0;
	private static final int KICK_PLAYERS_ARG = 1;
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
			return handleSetAlarmCommand(sender, cmd, label, args);
		}
		
		return false;
	}
	
	private boolean handleSetAlarmCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("This command is for players only.");
		} else if (args.length != 2){
			sender.sendMessage("Incorrect number of arguments. Usage: /setalarm [duration in minutes] [true|false]");
			return false;
		} else {
			long duration = -1;
			boolean kickPlayers = false;
			try{
				duration = getDuration(args);
				kickPlayers = getKickPlayers(args);
				AlarmClockTask alarmClock = new AlarmClockTask(this, duration, kickPlayers, getServer().getOnlinePlayers()); 
				alarmClock.runTaskTimer(this, 0, TICKS_FOR_ONE_MINUTE);
				
				return true;
			} catch (NumberFormatException nfe){
				sender.sendMessage("Invalid duration. Usage: /setalarm [duration in minutes] [true|false]");
				return false;
			}
		}
		
		return false;
	}
	
	private long getDuration(String[] args){
		return Long.parseLong(args[DURATION_ARG]);
	}
	
	private boolean getKickPlayers(String[] args){
		return Boolean.parseBoolean(args[KICK_PLAYERS_ARG]);
	}
}
