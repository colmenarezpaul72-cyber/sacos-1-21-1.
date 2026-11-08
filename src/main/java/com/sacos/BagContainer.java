package com.sacos;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;

/**
 * Inventario de una bolsa: se lee y se guarda dentro del propio item (componente CONTAINER).
 * La pantalla de cofre siempre tiene 9 columnas; si la bolsa tiene menos (ej. Gleba = 8),
 * las columnas sobrantes se bloquean con un cristal gris que no se puede mover.
 */
public class BagContainer extends SimpleContainer {
	private final ItemStack bag;
	private final int usableCols;
	private boolean loading = true;

	public BagContainer(ItemStack bag, int rows, int usableCols) {
		super(rows * 9);
		this.bag = bag;
		this.usableCols = usableCols;
		NonNullList<ItemStack> items = NonNullList.withSize(rows * 9, ItemStack.EMPTY);
		bag.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(items);
		for (int i = 0; i < rows * 9; i++) {
			if (isLocked(i)) {
				ItemStack pane = new ItemStack(Items.GRAY_STAINED_GLASS_PANE);
				pane.set(DataComponents.CUSTOM_NAME, Component.literal(" "));
				setItem(i, pane);
			} else {
				setItem(i, items.get(i));
			}
		}
		loading = false;
	}

	public boolean isLocked(int slot) {
		return slot % 9 >= usableCols;
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (!loading) {
			List<ItemStack> list = new ArrayList<>();
			for (int i = 0; i < getContainerSize(); i++) {
				list.add(isLocked(i) ? ItemStack.EMPTY : getItem(i));
			}
			bag.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(list));
		}
	}
}
