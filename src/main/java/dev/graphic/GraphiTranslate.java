package dev.graphic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import dev.graphic.commands.DisableLocator;
import dev.graphic.commands.DisableTranslation;
import dev.graphic.commands.EnableLocator;
import dev.graphic.commands.EnableTranslation;
import dev.graphic.commands.ListPlayers;
import dev.graphic.commands.ListTimes;
import dev.graphic.commands.Login;
import dev.graphic.commands.Logout;
import dev.graphic.commands.MineplexUtils;
import dev.graphic.commands.Translate;
import dev.graphic.network.ChatInterceptor;
import dev.graphic.network.Locator;
import dev.graphic.network.TranslateConnection;
import dev.graphic.store.StateStore;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = GraphiTranslate.MODID, name = GraphiTranslate.TITLE, version = GraphiTranslate.VERSION)
public class GraphiTranslate {

	@Instance
	public static GraphiTranslate instance;
	public static String configLanguage = "English";

	public static final String TITLE = "MineplexUtils";
	public static final String MODID = "mineplexutils";
	public static final String VERSION = "1.4";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Exception {
		MinecraftForge.EVENT_BUS.register(new ChatInterceptor());
		TranslateConnection.captureLanguages();
		StateStore.loadPasswords();

	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		Locator locator = new Locator();
		FMLCommonHandler.instance().bus().register(instance);

	}

	@EventHandler
	public void test(FMLPostInitializationEvent e) throws InterruptedException, ExecutionException {

		ClientCommandHandler.instance.registerCommand(new Login());
		ClientCommandHandler.instance.registerCommand(new Translate());
		ClientCommandHandler.instance.registerCommand(new EnableLocator());
		ClientCommandHandler.instance.registerCommand(new DisableLocator());
		ClientCommandHandler.instance.registerCommand(new ListTimes());
		ClientCommandHandler.instance.registerCommand(new MineplexUtils());
		ClientCommandHandler.instance.registerCommand(new Logout());
		ClientCommandHandler.instance.registerCommand(new ListPlayers());
		ClientCommandHandler.instance.registerCommand(new EnableTranslation());
		ClientCommandHandler.instance.registerCommand(new DisableTranslation());

	}

}
