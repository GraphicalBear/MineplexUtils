package dev.graphic.commands;

import java.util.ArrayList;
import java.util.List;

import dev.graphic.network.Locator;
import dev.graphic.store.StateStore;
import dev.graphic.store.StateStore.LoginCode;
import dev.graphic.utils.Chatter;
import dev.graphic.utils.Chatter.S;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class DisableLocator implements ICommand {

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getName() {
		return "disablelocator";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/disablelocator";
	}

	@Override
	public List getAliases() {
		List aliases = new ArrayList();
		aliases.add("dl");
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {

		if (StateStore.locate == false)
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "Passive player location already disabled").send();
		else {
			StateStore.locate = false;
			Locator.getLocator().stopLocation();
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "Passive player location is now disabled").send();
		}

	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		if (StateStore.loginCode.equals(LoginCode.NONE))
			return false;
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
