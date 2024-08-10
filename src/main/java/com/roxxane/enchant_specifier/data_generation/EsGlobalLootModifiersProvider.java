package com.roxxane.enchant_specifier.data_generation;

import com.roxxane.enchant_specifier.Es;
import com.roxxane.enchant_specifier.loot.EnchantedBookRemoverModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class EsGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public EsGlobalLootModifiersProvider(PackOutput output) {
        super(output, Es.mod_id);
    }

    @Override
    protected void start() {
        add("enchanted_book_remover", new EnchantedBookRemoverModifier(
                new LootItemCondition[]{}, true
        ));
    }
}
