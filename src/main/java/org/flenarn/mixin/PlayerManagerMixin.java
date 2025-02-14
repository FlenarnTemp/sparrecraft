package org.flenarn.mixin;

import net.minecraft.network.ClientConnection;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(at = @At(value = "TAIL"), method = "onPlayerConnect")
    private void onPlayerJoin(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {
        if (player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.LEAVE_GAME)) < 1) {
            player.getServer().getPlayerManager().broadcast(Text.literal("First join").formatted(Formatting.GOLD), true);
        } else {
            player.getServer().getPlayerManager().broadcast(Text.literal("Normal join").formatted(Formatting.GOLD), true);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "broadcast")
    private void broadcast(Text message, boolean overlay, CallbackInfo ci) {
        if (message.contains(Text.of("joined the game"))) {
            ci.cancel();
        } else if (message.contains(Text.of("left the game"))) {
            ci.cancel();
        }
    }
}
