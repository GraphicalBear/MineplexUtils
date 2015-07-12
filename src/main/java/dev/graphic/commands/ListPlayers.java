package dev.graphic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dev.graphic.network.Locator;
import dev.graphic.store.NameStore;
import dev.graphic.store.StateStore;
import dev.graphic.store.StateStore.LoginCode;
import dev.graphic.utils.Chatter;
import dev.graphic.utils.Chatter.S;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

public class ListPlayers implements ICommand {
	
	private static int counter = 0;
	
	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getName() {
		return "listplayers";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/listplayers";
	}

	@Override
	public List getAliases() {
		List aliases = new ArrayList();
		aliases.add("lp");
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException {
		String tempName = Minecraft.getMinecraft().thePlayer.getName().toLowerCase();
	
		if (StateStore.loginCode.equals(LoginCode.MODCOORD) && !NameStore.getCoordList().has(tempName)) {
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "You have no entry in the database").send();
			return;
		}
		
		if (StateStore.executing == false) {
			if (StateStore.locate)
				Locator.getLocator().stopLocation();
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "________Now listing player locations________").send();
			StateStore.executing = true;
			StateStore.timeTimer = new Timer();
			StateStore.timeTimer.scheduleAtFixedRate(new CounterTask(), 500, 2500);
		} else {
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "A task is already running").send();
		}
		
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		if (StateStore.loginCode.equals(LoginCode.APPS) || StateStore.loginCode.equals(LoginCode.MODCOORD))
			return true;
		return false;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,
			BlockPos pos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
	
	private class CounterTask extends TimerTask {
		@Override
		public void run () {
			String name = "";
			
			if (StateStore.loginCode.equals(LoginCode.APPS)) {
				name = NameStore.getAppsList().get(counter);
				counter++;
				if (counter >= NameStore.getAppsList().size()) {
					counter = 0;
					StateStore.timeTimer.cancel();
					StateStore.executing = false;
					Thread thread = new Thread() {
						@Override
						public void run() {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							new Chatter(StateStore.sender).add(S.AQUA).add(S.BOLD, "________________________________________").send();
						}
					};
					thread.start();
					if (StateStore.locate)
						Locator.getLocator().startLocation();
				}
			}
			if (StateStore.loginCode.equals(LoginCode.MODCOORD)) {
				String user_name = Minecraft.getMinecraft().thePlayer.getName().toLowerCase();
				if (!NameStore.getCoordList().has(user_name)) {
					new Chatter(StateStore.sender).add(S.AQUA).add(S.BOLD, "You have no entry in the database").send();
					return;
				}
				name = NameStore.getCoordList().getAsJsonObject().get(user_name).getAsJsonArray().get(counter).getAsString();
				counter++;
				if (counter >= NameStore.getCoordList().getAsJsonObject().get(user_name).getAsJsonArray().size()) {
					counter = 0;
					StateStore.timeTimer.cancel();
					StateStore.executing = false;
					Thread thread = new Thread() {
						@Override
						public void run() {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							new Chatter(StateStore.sender).add(S.AQUA).add(S.BOLD, "________________________________________").send();
						}
					};
					thread.start();
					if (StateStore.locate)
						Locator.getLocator().startLocation();
				}
			}
			
			Minecraft.getMinecraft().thePlayer.sendChatMessage("/locate " + name);
		}
	}
	
	

}
