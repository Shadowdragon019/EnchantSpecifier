package com.roxxane.enchant_specifier;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class EsConfig {
	public static boolean loaded = false;
	public static boolean removeEnchantedBooksFromVillagerTrades;
	public static boolean removeEnchantedBooksFromLootTables;

	private static final Path path = Path.of(FMLPaths.CONFIGDIR.get().toString() + "/enchant_specifier.json");
	private static HashMap<Enchantment, Integer> defaultEnchants;
	private static HashMap<Item, HashMap<Enchantment, Integer>> enchants;

	private static Enchantment getEnchant(String string) {
		return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(string));
	}

	private static Item getItem(String string) {
		return ForgeRegistries.ITEMS.getValue(new ResourceLocation(string));
	}

	public static boolean reload() {
		var reloadSuccess = false;
		try {
			Gson gson = new Gson();
			if (!Files.exists(path)) {
				JsonWriter writer = new JsonWriter(new FileWriter(path.toString()));
				JsonObject defaultData = new JsonObject();

				// Default enchants
				var jsonObject = new JsonObject();
				jsonObject.addProperty("sharpness", 1);
				jsonObject.addProperty("looting", 42);
				defaultData.add("default_enchants", jsonObject);

				// Enchants
				jsonObject = new JsonObject();
				jsonObject.add("stick", new JsonObject());
				jsonObject.getAsJsonObject("stick").addProperty("sharpness", 100);
				jsonObject.add("netherite_sword", new JsonObject());
				jsonObject.getAsJsonObject("netherite_sword").addProperty("sharpness", 0);
				jsonObject.getAsJsonObject("netherite_sword").addProperty("looting", 100);
				jsonObject.getAsJsonObject("netherite_sword").addProperty("unbreaking", 69);
				jsonObject.add("bedrock", new JsonObject());
				defaultData.add("enchants", jsonObject);

				// Remove from villagers & loot tables
				defaultData.addProperty("remove_enchanted_books_from_villager_trades", true);
				defaultData.addProperty("remove_enchanted_books_from_loot_tables", true);

				// Closing
				gson.toJson(defaultData, writer);
				writer.close();
			}

			JsonReader reader = new JsonReader(new FileReader(path.toString()));
			JsonObject data = gson.fromJson(reader, JsonObject.class);

			// Default enchants
			defaultEnchants = new HashMap<>();
			for (var entry : data.getAsJsonObject("default_enchants").entrySet()) {
				defaultEnchants.put(
					getEnchant(entry.getKey()),
					entry.getValue().getAsInt()
				);
			}

			// Enchants
			enchants = new HashMap<>();
			for (var entry : data.getAsJsonObject("enchants").entrySet()) {
				var item = getItem(entry.getKey());
				HashMap<Enchantment, Integer> map = new HashMap<>();
				for (var enchantInfo : entry.getValue().getAsJsonObject().entrySet()) {
					var enchant = getEnchant(enchantInfo.getKey());
					var level = enchantInfo.getValue().getAsInt();
					map.put(enchant, level);
				}
				enchants.put(item, map);
			}

			// Remove from villagers & loot tables
			removeEnchantedBooksFromVillagerTrades = data.get("remove_enchanted_books_from_villager_trades").getAsBoolean();
			removeEnchantedBooksFromLootTables = data.get("remove_enchanted_books_from_loot_tables").getAsBoolean();

			reloadSuccess = true;
		} catch (Exception ignored) {
			Es.logger.warn("Could not reload config!");
		}
		loaded = true;
		return reloadSuccess;
	}

	@SuppressWarnings("unchecked")
	public static HashMap<Enchantment, Integer> getEnchants(Item item) {
		if (enchants.containsKey(item)) {
			return (HashMap<Enchantment, Integer>) enchants.get(item).clone();
		} else {
			return (HashMap<Enchantment, Integer>) defaultEnchants.clone();
		}
	}
}