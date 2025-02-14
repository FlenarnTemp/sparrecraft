package org.flenarn.mixin;


import net.minecraft.server.world.ServerWorld;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	private int tickTowardsRandomMessage = 0;
	private int nextRandomMessage = getRandomThreshold();

	private static int getRandomThreshold() {
		return ThreadLocalRandom.current().nextInt(13200, 18001); // 11 - 15 minutes interval, assuming the TPS doesn't go down the drain.
	}
	@Inject(method = "tick", at = @At(value = "HEAD"))
	public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		if ((tickTowardsRandomMessage % 100) == 0) {
			if (tickTowardsRandomMessage >= nextRandomMessage) {
				((ServerWorld) (Object) this).getServer().getPlayerManager().broadcast(Text.literal("RANDOM INTERVAL").formatted(Formatting.GOLD), true);
				tickTowardsRandomMessage = 0;
				nextRandomMessage = getRandomThreshold();
			}
		}
		tickTowardsRandomMessage++;
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;wakeSleepingPlayers()V"))
	public void onWakeSleepingPlayers(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		((ServerWorld) (Object) this).getServer().getPlayerManager().broadcast(Text.literal("TEST").formatted(Formatting.GOLD), true);
	}
}