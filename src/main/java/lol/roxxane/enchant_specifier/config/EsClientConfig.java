package lol.roxxane.enchant_specifier.config;

import lol.roxxane.enchant_specifier.Es;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Es.id, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EsClientConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	public static final ForgeConfigSpec.BooleanValue REMOVED_ENCHANT_GLINT =
		BUILDER.define("remove_enchant_glint", true);
	public static final ForgeConfigSpec.BooleanValue REMOVE_ENCHANT_NAMES_IN_ITEM_DESCRIPTION =
		BUILDER.define("remove_enchant_names_in_item_description", true);
	public static final ForgeConfigSpec.BooleanValue REMOVE_ENCHANT_RARITY =
		BUILDER.define("remove_enchant_rarity", true);

	public static final ForgeConfigSpec SPEC = BUILDER.build();
}