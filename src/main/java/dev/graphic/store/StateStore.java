package dev.graphic.store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;

import org.apache.commons.io.IOUtils;

import net.minecraft.command.ICommandSender;

public class StateStore {

	public static boolean translate = false;
	public static boolean locate = false;
	public static ICommandSender sender = null;
	public static String streamsPassword = "[public repository, redacted]";
	public static String appsPassword = "[public repository, redacted]";
	public static String coordPassword = "[public repository, redacted]";
	public static LoginCode loginCode = LoginCode.NONE;
	public static boolean executing = false;
	public static Timer timeTimer;
	public static boolean failed = false;

	static {

		streamsPassword = HTTP.getPassword("[public repository, redacted]");
		appsPassword = HTTP.getPassword("[public repository, redacted]");
		coordPassword = HTTP.getPassword("[public repository, redacted]");
		
	}
	
	public static enum LoginCode {
		STREAMS, MODCOORD, APPS, NONE;
	}

}
