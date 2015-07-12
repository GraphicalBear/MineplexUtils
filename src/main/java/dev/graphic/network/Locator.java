package dev.graphic.network;

import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.JsonArray;

import dev.graphic.store.NameStore;
import dev.graphic.store.StateStore;
import dev.graphic.store.StateStore.LoginCode;
import net.minecraft.client.Minecraft;

public class Locator {

	private int counter = 0;
	private static Locator locator;
	private Timer timer;
	private static boolean coordLoad = true;
	private JsonArray coordSave;

	public Locator() {
		locator = this;

	}

	public static Locator getLocator() {
		return locator;
	}

	public void startLocation() {
		timer = new Timer();
		if (StateStore.loginCode.equals(LoginCode.APPS))
			timer.scheduleAtFixedRate(new AppsCounterTask(), 1000, 6000);
		if (StateStore.loginCode.equals(LoginCode.MODCOORD))
			timer.scheduleAtFixedRate(new CoordCounterTask(), 1000, 6000);
		if (StateStore.loginCode.equals(LoginCode.STREAMS))
			timer.scheduleAtFixedRate(new StreamsCounterTask(), 1000, 6000);
	}

	public void stopLocation() {
		timer.cancel();
		timer.purge();
		counter = 0;
	}

	private class StreamsCounterTask extends TimerTask {

		@Override
		public void run() {

			String name = "";
			if (counter < NameStore.getYouTubers().size()) {
				name = NameStore.getYouTubers().get(counter);
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/locate " + name);
				testCount();
				return;
			}
			int moddedcount = counter - NameStore.getYouTubers().size();
			if (moddedcount < NameStore.getStreamers().size()) {
				name = NameStore.getStreamers().get(moddedcount);
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/locate " + name);
				testCount();
				return;
			}
		}
	}

	private class AppsCounterTask extends TimerTask {

		@Override
		public void run() {

			String name = NameStore.getAppsList().get(counter);
			Minecraft.getMinecraft().thePlayer.sendChatMessage("/locate " + name);
			counter++;
			if (counter >= NameStore.getAppsList().size())
				counter = 0;
		}
	}

	private class CoordCounterTask extends TimerTask {

		@Override
		public void run() {

			if (coordLoad) {
				String user_name = Minecraft.getMinecraft().thePlayer.getName().toLowerCase();
				coordSave = NameStore.getCoordList().getAsJsonObject().get(user_name).getAsJsonArray();
				coordLoad = false;
			}

			String name = coordSave.get(counter).getAsString();
			Minecraft.getMinecraft().thePlayer.sendChatMessage("/locate " + name);
			counter++;
			if (counter >= coordSave.size())
				counter = 0;
		}
	}

	private void testCount() {
		counter++;
		int fullcount = NameStore.getStreamers().size() + NameStore.getYouTubers().size();
		if (counter >= fullcount)
			counter = 0;
	}
}
