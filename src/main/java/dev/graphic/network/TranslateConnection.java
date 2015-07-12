package dev.graphic.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.minecraft.util.StringTranslate;

public abstract class TranslateConnection {

	public static String userLanguage = "en";
	private static String langCodes;
	private static String langDisps;

	public static String translate(String source) throws Exception {
		if (source.length() < 2)
			return "Query too short!";
		else
			return getTranslation(source, getLanguage(source), userLanguage);
	}

	private static String getLanguage(String languageInput) throws Exception {
		String temp = getData(rootURL + detReq + authKey + text + languageInput).toString();
		temp = temp.substring(temp.lastIndexOf(':') + 2, temp.lastIndexOf("\""));
		if (!temp.equals(""))
			return temp;
		else
			return (userLanguage);
	}

	private static String getTranslation(String source, String sourceLang, String targetLang) throws Exception {
		if (sourceLang == targetLang)
			return "Cannot be translated";

		String temp = getData(rootURL + transReq + authKey + lang + sourceLang + "-" + targetLang + text + source).toString();
		temp = temp.substring(temp.indexOf('[') + 2, temp.lastIndexOf(']') - 1);
		return getVerbose(sourceLang) + temp;
	}

	private static String getVerbose(String code) {
		String temp = langDisps.substring(langDisps.indexOf(code) + 5);
		temp = temp.substring(0, temp.indexOf('\"'));
		return "§f(" + temp.toUpperCase() + ") ";
	}

	private static StringBuffer getData(String url) throws Exception {
		URL yandex;
		yandex = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) yandex.openConnection();
		connection.setRequestMethod("GET");
		int responseCode = connection.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;
	}

	private static final String rootURL = "https://translate.yandex.net/api/v1.5/tr.json/";
	private static final String transReq = "translate?key=";
	private static final String detReq = "detect?key=";
	private static final String lngReq = "getLangs?key=";
	private static final String authKey = "trnsl.1.1.20150712T221844Z.5db6a8fe563ed788.45e45200e053a2d9b44e4567935f7ccbc7109f8f";
	private static final String lang = "&lang=";
	private static final String text = "&text=";
	private static final String lngDisp = "&ui=";

	public static void captureLanguages() throws Exception {
		String temp = getData(rootURL + lngReq + authKey + lngDisp + userLanguage).toString().toLowerCase();
		langCodes = temp.substring(temp.indexOf('[') + 1, temp.indexOf(']') - 1);
		langDisps = temp.substring(temp.indexOf("langs") + 8, temp.indexOf('}') - 2);
	}

	public static String setLanguage(String lang) {
		if (langDisps.contains(lang) && lang.length() > 2) {
			userLanguage = langDisps.substring(langDisps.indexOf(lang) - 5, langDisps.indexOf(lang) - 4);
			return lang;
		} else
			userLanguage = "en";
		return "English";

	}

}