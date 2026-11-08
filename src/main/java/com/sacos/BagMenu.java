package com.sacos;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** Menu de cofre para una bolsa: no deja meter bolsas dentro de bolsas y se cierra si mueves la bolsa. */
public class BagMenu extends ChestMenu {
	private final ItemStack bag;

	public BagMenu(MenuType<?> type, int id, Inventory inventory, Container container, int rows, ItemStack bag) {
		super(type, id, inventory, container, rows);
		this.bag = bag;
	}

	@Override
	protected Slot addSlot(Slot slot) {
		if (slot.container instanceof BagContainer) {
			final BagContainer bc = (BagContainer) slot.container;
			final int index = slot.getContainerSlot();
			slot = new Slot(slot.container, index, slot.x, slot.y) {
				@Override
				public boolean mayPlace(ItemStack stack) {
					return !bc.isLocked(index) && !(stack.getItem() instanceof BagItem);
				}

				@Override
				public boolean mayPickup(Player player) {
					return !bc.isLocked(index);
				}
			};
		}
		return super.addSlot(slot);
	}

	@Override
	public boolean stillValid(Player player) {
		if (!super.stillValid(player)) {
			return false;
		}
		Inventory inv = player.getInventory();
		for (int i = 0; i < inv.getContainerSize(); i++) {
			if (inv.getItem(i) == bag) {
				return true;
			}
		}
		return false;
	}
}
