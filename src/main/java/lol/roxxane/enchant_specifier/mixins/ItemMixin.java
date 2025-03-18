package lol.roxxane.enchant_specifier.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import lol.roxxane.enchant_specifier.config.EsClientConfig;
import lol.roxxane.enchant_specifier.config.EsServerConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
abstract class ItemMixin {
	@Unique
	public Item es$cast() { return (Item) (Object) this; }

	@ModifyExpressionValue(method = "getRarity",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEnchanted()Z"))
	private boolean getRarity_Inject(boolean original) {
		if (EsClientConfig.REMOVE_ENCHANT_RARITY.get() && EsServerConfig.has_enchants(es$cast()))
			return false;
		else return original;
	}

	@Inject(method = "isFoil", at = @At("HEAD"), cancellable = true)
	private void hasFoilInject(CallbackInfoReturnable<Boolean> cir) {
		if (EsClientConfig.REMOVED_ENCHANT_GLINT.get() && EsServerConfig.has_enchants(es$cast()))
			cir.setReturnValue(false);
	}
}