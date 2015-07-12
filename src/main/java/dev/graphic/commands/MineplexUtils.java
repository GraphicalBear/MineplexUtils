package dev.graphic.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import dev.graphic.GraphiTranslate;
import dev.graphic.store.NameStore;
import dev.graphic.store.StateStore;
import dev.graphic.store.StateStore.LoginCode;
import dev.graphic.utils.Alerts;
import dev.graphic.utils.Chatter;
import dev.graphic.utils.Chatter.S;

public class MineplexUtils implements ICommand {

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getName() {
		return "mineplexutils";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/mineplexutils";
	}

	@Override
	public List getAliases() {
		List aliases = new ArrayList();
		aliases.add("mu");
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {

		if (args.length == 0) {

			new Chatter(sender).add(S.AQUA, "Mineplex").add(S.BLUE, "Utils").add().add(S.D_AQUA).add(S.ITALIC, "v").add(GraphiTranslate.VERSION).add().add(S.AQUA, "sucessfully loaded!").send();
			new Chatter(sender).add(S.GRAY).add(S.ITALIC, "- Notes:").send();
			new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - maintained by @GraphicalBear on twitter").send();
			new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - made to assist the Mineplex staff teams ").send();
			new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - please do not share this mod with others").send();
			new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - please type '/mu help' in chat get started").send();
		} else {
			new Chatter(sender).add(S.GRAY).add(S.ITALIC, "- Help:").send();
			new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - Aliases include /mineplexutils, /mu").send();
			new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /login <password> to authenticate").send();
			new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /logout to deauthenticate").send();
			if (StateStore.loginCode.equals(LoginCode.APPS) || StateStore.loginCode.equals(LoginCode.MODCOORD)) {
				new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /enablelocator to begin tracking players").send();
				new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /disablelocator to stop tracking players").send();
				new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /listtimes to display times of players").send();
				new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /listplayers to display player locations").send();
			}
			if (StateStore.loginCode.equals(LoginCode.STREAMS)) {
				new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /enablelocator to begin tracking players").send();
				new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /disablelocator to stop tracking players").send();

				new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /translate <message> to get a translation").send();
				new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /enabletranslation for auto-translation").send();
				new Chatter(sender).add(S.GRAY).add(S.ITALIC, "   - /disabletranslation to stop translation").send();
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
