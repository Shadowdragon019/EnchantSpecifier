package lol.roxxane.enchant_specifier.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import lol.roxxane.enchant_specifier.Es;
import lol.roxxane.enchant_specifier.utils.EsUtils;
import lol.roxxane.enchant_specifier.utils.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Es.id, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EsServerConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	private static final ForgeConfigSpec.ConfigValue<CommentedConfig> DEFAULT_ENCHANTS_VALUE =
		BUILDER.comment("Default enchants for items").define("default_enchants",
			EsUtils.new_config("sharpness", 111, "looting", 42), o -> {
			if (o instanceof UnmodifiableConfig config) {
				for (var entry : config.entrySet())
					if (!is_valid_enchant_entry(entry)) return false;
				return true;
			} else return false;
		});

	private static final ForgeConfigSpec.ConfigValue<CommentedConfig> ENCHANTS_VALUE =
		BUILDER.comment("Specific enchants for specific items").define("enchants",
			EsUtils.new_config("stick", EsUtils.new_config("sharpness", 100),
				"bedrock", EsUtils.new_config(), "potion", EsUtils.new_config()),
			o -> {
			if (o instanceof UnmodifiableConfig config) {
				for (var entry : config.entrySet()) {
					if (!(item_exists(entry.getKey()) && entry.getValue() instanceof UnmodifiableConfig enchants))
						return false;
					for (var enchant_entry : enchants.entrySet())
						if (!(is_valid_enchant_entry(enchant_entry)))
							return false;
				}
				return true;
			} else return false;
		});


	public static final ForgeConfigSpec.BooleanValue REMOVE_ENCHANTED_BOOKS_IN_VILLAGER_TRADES =
		BUILDER.define("remove_enchanted_books_in_villager_trades", true);
	public static final ForgeConfigSpec.EnumValue<HandleMethod> ENCHANTED_BOOKS_IN_LOOT_TABLES_HANDLER =
		BUILDER.defineEnum("enchanted_books_in_loot_tables_handler", HandleMethod.REMOVE);

	private final static HashMap<Enchantment, Integer> DEFAULT_ENCHANTS = new HashMap<>();
	private final static HashMap<Item, Map<Enchantment, Integer>> ENCHANTS = new HashMap<>();

	public static final ForgeConfigSpec SPEC = BUILDER.build();

	@SubscribeEvent
	static void on_load(final ModConfigEvent event) {
		DEFAULT_ENCHANTS.clear();
		ENCHANTS.clear();

		if (SPEC.isLoaded()) {
			DEFAULT_ENCHANTS_VALUE.get().valueMap().forEach((key, value) ->
				DEFAULT_ENCHANTS.put(get_enchant(key), (Integer) value));
			ENCHANTS_VALUE.get().valueMap().forEach((item_string, config) ->
				ENCHANTS.put(get_item(item_string),
					EsUtils.map(((UnmodifiableConfig) config).valueMap(),
						(enchant_string, level) -> Pair.of(get_enchant(enchant_string), (Integer) level))
				));
		}
	}

	private static boolean is_valid_enchant_entry(UnmodifiableConfig.Entry entry) {
		return (enchant_exists(entry.getKey()) && entry.getValue() instanceof Integer integer &&
			integer >= 0 && integer <= Byte.MAX_VALUE);
	}

	private static Enchantment get_enchant(String string) {
		return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(string));
	}
	private static boolean enchant_exists(String string) {
		return ForgeRegistries.ENCHANTMENTS.containsKey(new ResourceLocation(string));
	}

	private static Item get_item(String string) {
		return ForgeRegistries.ITEMS.getValue(new ResourceLocation(string));
	}
	private static boolean item_exists(String string) {
		return ForgeRegistries.ITEMS.containsKey(new ResourceLocation(string));
	}

	public static HashMap<Enchantment, Integer> get_enchants(Item item) {
		return new HashMap<>(ENCHANTS.getOrDefault(item, DEFAULT_ENCHANTS));
	}

	public static boolean has_enchants(Item item) {
		if (ENCHANTS.containsKey(item))
			return !ENCHANTS.get(item).isEmpty();
		else return !DEFAULT_ENCHANTS.isEmpty();
	}
}