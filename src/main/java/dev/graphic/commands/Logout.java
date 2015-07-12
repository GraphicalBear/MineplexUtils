package dev.graphic.commands;

import java.util.ArrayList;
import java.util.List;

import dev.graphic.network.Locator;
import dev.graphic.store.NameStore;
import dev.graphic.store.StateStore.LoginCode;
import dev.graphic.store.StateStore;
import dev.graphic.utils.Chatter;
import dev.graphic.utils.Chatter.S;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Logout implements ICommand {

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getName() {
		return "logout";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/logout";
	}

	@Override
	public List getAliases() {
		List aliases = new ArrayList();
		aliases.add("ll");
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException {
		
		if (StateStore.loginCode.equals(LoginCode.NONE)) {
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "You are not logged in").send();
			return;
		} else {
			StateStore.loginCode = LoginCode.NONE;
			StateStore.translate = false;
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "You have been logged out").send();
			if (StateStore.timeTimer != null)
				StateStore.timeTimer.cancel();
			if (StateStore.locate)
				Locator.getLocator().stopLocation();
			StateStore.locate = false;
		}
		
		
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return true;
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

}
