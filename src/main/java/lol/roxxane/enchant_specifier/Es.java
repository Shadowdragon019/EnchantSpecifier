package lol.roxxane.enchant_specifier;

import com.mojang.logging.LogUtils;
import lol.roxxane.enchant_specifier.config.EsClientConfig;
import lol.roxxane.enchant_specifier.config.EsServerConfig;
import lol.roxxane.enchant_specifier.loot.EsLootModifiers;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Es.id)
public class Es {
    public static final String id = "enchant_specifier";
    @SuppressWarnings("unused")
    public static final Logger logger = LogUtils.getLogger();

    public Es() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        EsLootModifiers.register(modEventBus);
        MinecraftForge.EVENT_BUS.addListener((VillagerTradesEvent event) -> {
            if (EsServerConfig.REMOVE_ENCHANTED_BOOKS_IN_VILLAGER_TRADES.get()) {
                event.getTrades().replaceAll((level, trades) -> {
                    trades.removeIf(listing -> listing instanceof VillagerTrades.EnchantBookForEmeralds);
                    return trades;
                });
            }
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, EsServerConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, EsClientConfig.SPEC);
    }
}