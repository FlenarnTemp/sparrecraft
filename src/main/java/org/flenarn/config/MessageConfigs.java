package org.flenarn.config;

import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

import net.fabricmc.loader.api.FabricLoader;
public class MessageConfigs extends ReflectiveConfig {

    public static final MessageConfigs sleepMessagesInstance = MessageConfigs.createToml(FabricLoader.getInstance().getConfigDir(), )

}
