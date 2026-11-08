package com.sacos;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

public class SacosMod implements ModInitializer {
	public static final String MOD_ID = "sacos";

	@Override
	public void onInitialize() {
		ModItems.init();
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
