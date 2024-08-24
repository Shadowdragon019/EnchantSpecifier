package com.roxxane.enchant_specifier;

import com.mojang.logging.LogUtils;
import com.roxxane.enchant_specifier.loot.EsLootModifiers;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// TODO: Port to 1.19.2 for inridiculam on Dsicord
@Mod(Es.mod_id)
public class Es {
    public static final String mod_id = "enchant_specifier";
    public static final Logger logger = LogUtils.getLogger();

    public Es() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        EsLootModifiers.register(modEventBus);
        MinecraftForge.EVENT_BUS.addListener((VillagerTradesEvent event) -> {
            if (EsConfig.removeEnchantedBooksFromVillagerTrades) {
                event.getTrades().replaceAll((level, trades) -> {
                    trades.removeIf(listing -> listing instanceof VillagerTrades.EnchantBookForEmeralds);
                    return trades;
                });
            }
        });
    }
}