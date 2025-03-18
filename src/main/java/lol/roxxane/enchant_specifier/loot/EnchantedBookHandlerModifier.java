package lol.roxxane.enchant_specifier.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lol.roxxane.enchant_specifier.config.EsServerConfig;
import lol.roxxane.enchant_specifier.config.HandleMethod;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class EnchantedBookHandlerModifier extends LootModifier {
    public final boolean enabled;
    // First time doing a Codec! That felt like something
    public static final Supplier<Codec<EnchantedBookHandlerModifier>> codec = Suppliers.memoize(
        () -> RecordCodecBuilder.create(
            instance -> codecStart(instance).and(
                Codec.BOOL.fieldOf("enabled").forGetter(
                    enchantedBookRemoverModifier -> enchantedBookRemoverModifier.enabled
                )
            ).apply(instance, EnchantedBookHandlerModifier::new)
        )
    );

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    public EnchantedBookHandlerModifier(LootItemCondition[] conditionsIn, boolean enabled) {
        super(conditionsIn);
        this.enabled = enabled;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (enabled && LootItemConditions.andConditions(conditions).test(context) &&
            EsServerConfig.ENCHANTED_BOOKS_IN_LOOT_TABLES_HANDLER.get() != HandleMethod.PRESERVE
        )
            generatedLoot.replaceAll(stack -> {
                if (stack.getItem() instanceof EnchantedBookItem)
                    return EsServerConfig.ENCHANTED_BOOKS_IN_LOOT_TABLES_HANDLER.get().replacement();
                else return stack;
            });
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return codec.get();
    }
}
