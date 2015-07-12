package dev.graphic.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Chatter {

	private String message = "";
	private static ICommandSender sender;

	public Chatter(ICommandSender sender) {
		this.sender = sender;
	}

	public Chatter(String message) {
		this.message = message;
	}

	public Chatter add(S format, String message) {
		return new Chatter(this.message + "" + style(format) + message + "");
	}

	public Chatter add(S format) {
		return new Chatter(this.message + "" + style(format) + "");
	}

	public Chatter add(String message) {
		return new Chatter(this.message + "" + message + "");
	}

	public Chatter add() {
		return new Chatter(this.message + " ");
	}

	public void send() {
		sender.addChatMessage(new ChatComponentText("§b§kA §6§lMPU>§r " + message));
	}

	public void sendRaw() {
		sender.addChatMessage(new ChatComponentText(message));
	}

	public static enum S {
		AQUA("§b"), BLACK("§0"), BLUE("§9"), BOLD("§l"), D_AQUA("§3"), D_BLUE("§1"), D_GRAY("§8"), D_GREEN("§2"), PURPLE("§5"), D_RED("§4"), GOLD("§6"), GRAY("§7"), GREEN("§a"), ITALIC("§o"), PINK(
				"§d"), OBF("§k"), RED("§c"), RESET("§r"), STRIKE("§m"), UNDER("§n"), WHITE("§f"), YELLOW("§e");

		private String code;

		S(String format) {
			this.code = format;
		}

		@Override
		public String toString() {
			return code;
		}
	}

	private EnumChatFormatting style(S style) {
		switch (style) {
		case AQUA:
			return EnumChatFormatting.AQUA;
		case BLACK:
			return EnumChatFormatting.BLACK;
		case BLUE:
			return EnumChatFormatting.BLUE;
		case BOLD:
			return EnumChatFormatting.BOLD;
		case D_AQUA:
			return EnumChatFormatting.DARK_AQUA;
		case D_BLUE:
			return EnumChatFormatting.DARK_BLUE;
		case PURPLE:
			return EnumChatFormatting.DARK_PURPLE;
		case D_RED:
			return EnumChatFormatting.DARK_RED;
		case GOLD:
			return EnumChatFormatting.GOLD;
		case GRAY:
			return EnumChatFormatting.GRAY;
		case GREEN:
			return EnumChatFormatting.GREEN;
		case ITALIC:
			return EnumChatFormatting.ITALIC;
		case PINK:
			return EnumChatFormatting.LIGHT_PURPLE;
		case OBF:
			return EnumChatFormatting.OBFUSCATED;
		case RED:
			return EnumChatFormatting.RED;
		case RESET:
			return EnumChatFormatting.RESET;
		case STRIKE:
			return EnumChatFormatting.STRIKETHROUGH;
		case UNDER:
			return EnumChatFormatting.UNDERLINE;
		case WHITE:
			return EnumChatFormatting.WHITE;
		case YELLOW:
			return EnumChatFormatting.YELLOW;
		default:
			return EnumChatFormatting.WHITE;
		}
	}
}