package com.sacos;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class ModItems {
	private static final List<Item> ALL = new ArrayList<>();

	// register(nombre, filas, columnas, esEnder)
	// Bolsa comun (marron): 3 filas x 9 = 27 espacios. Se tine con tinte + bolsa marron.
	public static final Item BOLSA = register("bolsa", 3, 9, false);
	// Bolsas de colores (15 tintes), 27 espacios cada una
	public static final String[] COLORES = {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink",
			"gray", "light_gray", "cyan", "purple", "blue", "green", "red", "black"};
	static {
		for (String c : COLORES) {
			register("bolsa_" + c, 3, 9, false);
		}
	}
	// Bolsa Primigenia (la beige): 27 espacios, no se craftea
	public static final Item BOLSA_PRIMIGENIA = register("bolsa_primigenia", 3, 9, false);
	// Bolsa Ender: abre tu ender chest
	public static final Item ENDER_BAG = register("ender_bag", 3, 9, true);
	// Bolsa Phora: 6 filas x 9 = 54 espacios, no se craftea
	public static final Item PHORA_BAG = register("phora_bag", 6, 9, false);
	// Bolsa Gleba: 4 filas x 8 columnas = 32 espacios, no se craftea
	public static final Item BOLSA_GLEBA = register("bolsa_gleba", 4, 8, false);

	private static Item register(String name, int rows, int cols, boolean ender) {
		Item item = Registry.register(BuiltInRegistries.ITEM, SacosMod.id(name),
				new BagItem(new Item.Properties().stacksTo(1), rows, cols, ender));
		ALL.add(item);
		return item;
	}

	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
			for (Item item : ALL) {
				entries.accept(item);
			}
		});
	}
}
