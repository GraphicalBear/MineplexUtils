package dev.graphic.commands;

import java.util.ArrayList;
import java.util.List;

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

//DisableTranslation is Currently un-used, and is in the works

public class DisableTranslation implements ICommand {

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getName() {
		return "disabletranslation";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/disabletranslation";
	}

	@Override
	public List getAliases() {
		List aliases = new ArrayList();
		aliases.add("dt");
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException {
		
		if (StateStore.translate == false)
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "Passive translation already disabled").send();
		else {
			StateStore.translate = false;
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "Passive translation is now disabled").send();
		}
		
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		if (StateStore.loginCode.equals(LoginCode.STREAMS))
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

}
