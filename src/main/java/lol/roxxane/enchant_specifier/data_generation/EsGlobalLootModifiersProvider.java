package lol.roxxane.enchant_specifier.data_generation;

import lol.roxxane.enchant_specifier.Es;
import lol.roxxane.enchant_specifier.loot.EnchantedBookHandlerModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class EsGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public EsGlobalLootModifiersProvider(PackOutput output) {
        super(output, Es.id);
    }

    @Override
    protected void start() {
        add("enchanted_book_handler", new EnchantedBookHandlerModifier(
                new LootItemCondition[]{}, true
        ));
    }
}
