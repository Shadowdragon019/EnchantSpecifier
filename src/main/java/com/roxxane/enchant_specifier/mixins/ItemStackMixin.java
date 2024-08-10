package com.roxxane.enchant_specifier.mixins;

import com.roxxane.enchant_specifier.EsConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.extensions.IForgeItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@SuppressWarnings("UnstableApiUsage")
@Mixin(ItemStack.class)
abstract class ItemStackMixin extends CapabilityProvider<ItemStack> implements IForgeItemStack {
	@Shadow public abstract void removeTagKey(String pKey);

	@Shadow public abstract CompoundTag getOrCreateTag();

	@Shadow @Nullable private CompoundTag tag;

	protected ItemStackMixin(Class<ItemStack> baseClass) {
		super(baseClass);
	}

	@Unique
	private void enchantSpecifier$enchant(Enchantment enchant, int level) {
		getOrCreateTag();
        assert tag != null;
        if (!tag.contains("Enchantments", 9)) {
			tag.put("Enchantments", new ListTag());
		}

		ListTag listtag = tag.getList("Enchantments", 10);
		listtag.add(EnchantmentHelper.storeEnchantment(EnchantmentHelper.getEnchantmentId(enchant), (byte) level));
	}

	/**
	 * @author Roxxane
	 * @reason Change default enchant behavior
	 */
	@Overwrite
	public void enchant(Enchantment enchant, int level) {
		removeTagKey(ItemStack.TAG_ENCH);
		if (EsConfig.loaded) {
			for (var entry : EsConfig.getEnchants(((ItemStack) (Object) this).getItem()).entrySet()) {
				enchantSpecifier$enchant(entry.getKey(), entry.getValue());
			}
		}
	}

	@Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
	private void publicInitInject(ItemLike item, int count, CompoundTag nbt, CallbackInfo ci) {
		enchant(Enchantments.SHARPNESS, 1);
	}

	@Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
	private void privateInitInject(CompoundTag compoundTag, CallbackInfo ci) {
		enchant(Enchantments.SHARPNESS, 1);
	}
}
