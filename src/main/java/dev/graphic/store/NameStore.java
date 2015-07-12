package dev.graphic.store;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.JsonObject;

import dev.graphic.utils.HTTP;

public class NameStore {

	private static final String yt = "[public repository, redacted]";
	private static final String st = "[public repository, redacted]";
	private static final String ap = "[public repository, redacted]";
	private static final String co = "[public repository, redacted]";

	private static ArrayList<String> youtubers;
	private static ArrayList<String> streamers;
	private static JsonObject coordlist;
	private static ArrayList<String> appslist;

	public static ArrayList<String> getYouTubers() {
		return youtubers;
	}

	public static ArrayList<String> getStreamers() {
		return streamers;
	}

	public static ArrayList<String> getAppsList() {
		return appslist;
	}

	public static JsonObject getCoordList() {
		return coordlist;
	}

	public static void loadStreams() {
		youtubers = HTTP.getList(yt);
		streamers = HTTP.getList(st);
	}

	public static void loadApps() {
		appslist = HTTP.getList(ap);
	}

	public static void loadCoord() {
		coordlist = HTTP.getJsonObjectWithObjectsWithArray(co);
	}

}
