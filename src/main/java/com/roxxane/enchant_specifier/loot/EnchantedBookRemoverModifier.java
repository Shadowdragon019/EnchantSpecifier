package com.roxxane.enchant_specifier.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.roxxane.enchant_specifier.EsConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class EnchantedBookRemoverModifier extends LootModifier {
    public final boolean enabled;
    // First time doing a Codec! That felt like something
    public static final Supplier<Codec<EnchantedBookRemoverModifier>> codec = Suppliers.memoize(
        () -> RecordCodecBuilder.create(
            instance -> codecStart(instance).and(
                Codec.BOOL.fieldOf("enabled").forGetter(
                    enchantedBookRemoverModifier -> enchantedBookRemoverModifier.enabled
                )
            ).apply(instance, EnchantedBookRemoverModifier::new)
        )
    );

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    public EnchantedBookRemoverModifier(LootItemCondition[] conditionsIn, boolean enabled) {
        super(conditionsIn);
        this.enabled = enabled;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (enabled && LootItemConditions.andConditions(conditions).test(context) && EsConfig.removeEnchantedBooksFromLootTables)
            generatedLoot.replaceAll(e -> {
                if (e.getItem() instanceof EnchantedBookItem) {
                    return Items.BOOK.getDefaultInstance();
                } else {
                    return e;
                }
            });
            //generatedLoot.removeIf(e -> e.getItem() instanceof EnchantedBookItem);
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return codec.get();
    }
}
