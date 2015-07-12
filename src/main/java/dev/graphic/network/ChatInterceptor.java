package dev.graphic.network;

import com.google.gson.JsonElement;

import dev.graphic.store.NameStore;
import dev.graphic.store.StateStore;
import dev.graphic.store.StateStore.LoginCode;
import dev.graphic.utils.Chatter;
import dev.graphic.utils.Chatter.S;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatInterceptor {

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void chatEvent(ClientChatReceivedEvent event) throws Exception {

		if (event.message instanceof ChatComponentTranslation
				|| event.message instanceof ChatComponentText) {
			String unformat = event.message.getUnformattedText();
			while (unformat.contains("§")) {
				unformat = unformat.substring(0, unformat.indexOf("§"))
						+ unformat.substring(unformat.indexOf("§") + 2,
								unformat.length());
			}
			if (unformat.contains("Locate>") && StateStore.locate) {

				if (unformat.contains("Failed to locate")) {
					event.setCanceled(true);
					return;
				}

				String name = unformat.substring(unformat.indexOf("[") + 1,
						unformat.indexOf("]", unformat.indexOf("[") + 1));
				String server = unformat.substring(unformat.lastIndexOf(" "),
						unformat.length());
				if (server.toLowerCase().contains("server")) {
					server = "THIS!";
				}
				String pref = "";

				if (StateStore.loginCode.equals(LoginCode.STREAMS)) {
					for (String item : NameStore.getStreamers()) {
						if (item.equalsIgnoreCase(name)) {
							pref = "§d§lSTREAMER";
							break;
						}
					}

					for (String item : NameStore.getYouTubers()) {
						if (item.equalsIgnoreCase(name)) {
							pref = "§c§lYOUTUBER";
							break;
						}
					}

					new Chatter(StateStore.sender).add(pref).add().add(name)
							.add().add(S.AQUA).add(S.BOLD, "is in server ")
							.add(S.YELLOW).add(S.BOLD, server.trim()).send();
					event.setCanceled(true);

				} else if (StateStore.loginCode.equals(LoginCode.APPS)) {

					for (String item : NameStore.getAppsList()) {
						System.out.println(">" + item + "<>" + name + "<");
						if (item.trim().equalsIgnoreCase(name.trim())) {
							new Chatter(StateStore.sender).add(S.YELLOW)
									.add(S.BOLD, name).add().add(S.AQUA)
									.add(S.BOLD, "is in server ").add(S.YELLOW)
									.add(S.BOLD, server.trim()).send();
							event.setCanceled(true);
							break;
						}
					}
				} else if (StateStore.loginCode.equals(LoginCode.MODCOORD)) {
					if (NameStore.getCoordList().has(
							Minecraft.getMinecraft().thePlayer.getName()
									.toLowerCase())) {
						for (JsonElement item : NameStore
								.getCoordList()
								.get(Minecraft.getMinecraft().thePlayer
										.getName().toLowerCase())
								.getAsJsonArray()) {
							if (name.trim().equalsIgnoreCase(
									item.getAsString().trim())) {
								new Chatter(StateStore.sender).add(S.YELLOW)
										.add(S.BOLD, name).add().add(S.AQUA)
										.add(S.BOLD, "is in server ")
										.add(S.YELLOW)
										.add(S.BOLD, server.trim()).send();
								event.setCanceled(true);
								break;
							}
						}
					}

				} else {
					new Chatter(StateStore.sender).add(S.YELLOW)
							.add(S.BOLD, name).add().add(S.AQUA)
							.add(S.BOLD, "is in server ").add(S.YELLOW)
							.add(S.BOLD, server.trim()).send();
					event.setCanceled(true);
				}

			}

			// Auto-Translation is currently disabled, and is in the works

			if (!StateStore.translate)
				return;

			String message = event.message.getFormattedText();

			if (message.length() < 30)
				return;

			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						new Chatter(StateStore.sender).add(S.WHITE,
								" " + TranslateConnection.translate(message))
								.send();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
			event.setCanceled(true);
		}

	}

}
