package com.roxxane.enchant_specifier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class EsClient {
	public static void clientReload() {
		if (!EsConfig.reload() && Minecraft.getInstance().level != null)
			for (var player : Minecraft.getInstance().level.players())
				player.sendSystemMessage(Component.translatable("chat.enchant_specifier.reload_error"));
	}
}
