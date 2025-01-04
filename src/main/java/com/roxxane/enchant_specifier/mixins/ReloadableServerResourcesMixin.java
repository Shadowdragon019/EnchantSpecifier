package com.roxxane.enchant_specifier.mixins;

import com.roxxane.enchant_specifier.EsClient;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableServerResources.class)
abstract class ReloadableServerResourcesMixin {
	@Inject(method = "loadResources", at = @At("HEAD"))
	private static void loadResourcesMixin(
		ResourceManager resourceManager,
		RegistryAccess.Frozen registryAccess,
		FeatureFlagSet enabledFeatures,
		Commands.CommandSelection commandSelection,
		int functionCompilationLevel,
		Executor backgroundExecutor,
		Executor gameExecutor,
		CallbackInfoReturnable<CompletableFuture<ReloadableServerResources>> cir
	) {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> EsClient::clientReload);
	}
}
