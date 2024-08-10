package com.roxxane.enchant_specifier.mixins;

import com.roxxane.enchant_specifier.EsConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Map;
import java.util.Objects;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    /**
     * @author Roxxane
     * @reason Change default enchant behavior
     */
    @Overwrite(remap = false)
    public static int getTagEnchantmentLevel(Enchantment enchant, ItemStack stack) {
        return Objects.requireNonNullElse(EsConfig.getEnchants(stack.getItem()).get(enchant), 0);
    }

    /**
     * @author Roxxane
     * @reason Change default enchant behavior
     */
    @Overwrite(remap = false)
    public static Map<Enchantment, Integer> getEnchantments(ItemStack stack) {
        return EsConfig.getEnchants(stack.getItem());
    }

    /**
     * @author Roxxane
     * @reason Change default enchant behavior
     */
    @Overwrite(remap = false)
    public static void setEnchantments(Map<Enchantment, Integer> enchants, ItemStack stack) {
        stack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 0);
    }
}
