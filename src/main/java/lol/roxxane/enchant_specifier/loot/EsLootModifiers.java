package lol.roxxane.enchant_specifier.loot;

import com.mojang.serialization.Codec;
import lol.roxxane.enchant_specifier.Es;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EsLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> lootModifierSerializers =
        DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Es.id);

    @SuppressWarnings("unused")
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> enchantedBookRemover =
        lootModifierSerializers.register("enchanted_book_remover", EnchantedBookHandlerModifier.codec);

    public static void register(IEventBus bus) {
        lootModifierSerializers.register(bus);
    }
}
