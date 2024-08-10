package com.roxxane.enchant_specifier.data_generation;

import com.roxxane.enchant_specifier.Es;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Es.mod_id, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();

        generator.addProvider(
            event.includeClient(),
            new EsLanguageProvider(output, "en_us")
        );

        generator.addProvider(
            event.includeServer(),
            new EsGlobalLootModifiersProvider(output)
        );
    }
}