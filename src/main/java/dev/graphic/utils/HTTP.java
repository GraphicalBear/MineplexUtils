package dev.graphic.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HTTP {

	// Note: User agents are required to prevent apache server from returning a
	// 403

	public static ArrayList<String> getList(String url) {
		ArrayList<String> builder = new ArrayList<String>();
		try {
			URL object = new URL(url);

			HttpURLConnection connection = (HttpURLConnection) object.openConnection();
			connection.setDoInput(true);
			connection.addRequestProperty("User-Agent", "Mozilla/4.0");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestMethod("GET");

			int HttpResult = connection.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.add(line.toLowerCase());
				}

				reader.close();

				System.out.println("GOOD RESPONSE)" + builder.toString());
				connection.disconnect();

			} else {

				System.err.println("BAD RESPONSE) " + connection.getResponseMessage());
				connection.disconnect();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder;
	}

	public static JsonObject getJsonObjectWithObjectsWithArray(String url) {
		JsonObject response = null;
		try {
			URL object = new URL(url);

			HttpURLConnection connection = (HttpURLConnection) object.openConnection();
			connection.setDoInput(true);
			connection.addRequestProperty("User-Agent", "Mozilla/4.0");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestMethod("GET");

			StringBuilder builder = new StringBuilder();
			int HttpResult = connection.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.append(line + "\n");
				}

				reader.close();

				response = new JsonParser().parse(builder.toString().toLowerCase()).getAsJsonObject();

				System.out.println("GOOD RESPONSE)" + builder.toString());
				connection.disconnect();

			} else {

				System.err.println("BAD RESPONSE) " + connection.getResponseMessage());
				connection.disconnect();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}

	public static String getPassword(String urlString) {
		HttpURLConnection httpcon;
		String tempStore = "";
		try {
			URL url = new URL(urlString);
			httpcon = (HttpURLConnection) url.openConnection();
			httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");
			InputStream input = httpcon.getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(input, writer, "utf-8");
			String theString = writer.toString();
			if (theString.length() > 2)
				tempStore = theString;
			httpcon.disconnect();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return tempStore;
	}

}
