package dev.graphic.commands;

import java.util.ArrayList;
import java.util.List;

import dev.graphic.network.TranslateConnection;
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

public class Translate implements ICommand {

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getName() {
		return "translate";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/translate <message>";
	}

	@Override
	public List getAliases() {
		List aliases = new ArrayList();
		aliases.add("t");
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {

		String temp = "";
		for (int i = 0; i < args.length; i++) {
			temp = temp.concat(args[i] + "+");
		}
		temp = temp.substring(0, temp.length() - 1);
		try {
			temp = TranslateConnection.translate(temp);
		} catch (Exception e) {
			temp = "Failed to Translate";
			e.printStackTrace();
		}
		new Chatter(sender).add(S.AQUA, temp).send();
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		if (StateStore.loginCode.equals(LoginCode.STREAMS))
			return true;
		return false;
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
