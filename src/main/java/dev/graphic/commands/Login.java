package dev.graphic.commands;

import java.util.ArrayList;
import java.util.List;

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

public class Login implements ICommand {

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getName() {
		return "login";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/login <password>";
	}

	@Override
	public List getAliases() {
		List aliases = new ArrayList();
		aliases.add("l");
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {

		if (!StateStore.loginCode.equals(LoginCode.NONE)) {
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "Already logged in").send();
			return;
		}

		if (args.length == 0) {
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "Please supply a password").send();
			return;
		}

		if (StateStore.loginCode.equals(LoginCode.NONE)) {
			StateStore.sender = sender;
			if (args[0].equals(StateStore.streamsPassword)) {
				NameStore.loadStreams();
				StateStore.loginCode = LoginCode.STREAMS;
				new Chatter(sender).add(S.AQUA).add(S.BOLD, "Logged in as a Stream Moderator").send();
			} else if (args[0].equals(StateStore.appsPassword)) {
				NameStore.loadApps();
				StateStore.loginCode = LoginCode.APPS;
				new Chatter(sender).add(S.AQUA).add(S.BOLD, "Logged in as an Apps Member").send();
			} else if (args[0].equals(StateStore.coordPassword)) {
				NameStore.loadCoord();
				StateStore.loginCode = LoginCode.MODCOORD;
				new Chatter(sender).add(S.AQUA).add(S.BOLD, "Logged in as a ModCoord Member").send();
			} else {
				new Chatter(sender).add(S.AQUA).add(S.BOLD, "Invalid Login").send();
			}
		}
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
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
