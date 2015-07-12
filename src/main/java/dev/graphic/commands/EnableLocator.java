package dev.graphic.commands;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class EnableLocator implements ICommand {

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getName() {
		return "enablelocator";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/enablelocator";
	}

	@Override
	public List getAliases() {
		List aliases = new ArrayList();
		aliases.add("el");
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {

		if (StateStore.locate == true)
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "Passive player location already enabled").send();
		else {
			String tempName = Minecraft.getMinecraft().thePlayer.getName().toLowerCase();
			if (StateStore.loginCode.equals(LoginCode.MODCOORD)) {
				if (!NameStore.getCoordList().has(tempName)) {
					new Chatter(sender).add(S.AQUA).add(S.BOLD, "You have no entry in the database").send();
					return;
				}
			}
			StateStore.locate = true;
			Locator.getLocator().startLocation();
			new Chatter(sender).add(S.AQUA).add(S.BOLD, "Passive player location is now enabled").send();
		}

	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		if (!StateStore.loginCode.equals(LoginCode.NONE))
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
