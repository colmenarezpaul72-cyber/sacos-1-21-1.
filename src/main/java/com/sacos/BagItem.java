package com.sacos;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BagItem extends Item {
	private final int rows;
	private final int cols;
	private final boolean ender;

	public BagItem(Properties properties, int rows, int cols, boolean ender) {
		super(properties);
		this.rows = rows;
		this.cols = cols;
		this.ender = ender;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide()) {
			Component title = stack.getHoverName();
			if (ender) {
				var enderChest = player.getEnderChestInventory();
				player.openMenu(new SimpleMenuProvider(
						(id, inv, p) -> ChestMenu.threeRows(id, inv, enderChest), title));
			} else {
				BagContainer container = new BagContainer(stack, rows, cols);
				MenuType<ChestMenu> type = rows >= 6 ? MenuType.GENERIC_9x6
						: rows == 5 ? MenuType.GENERIC_9x5
						: rows == 4 ? MenuType.GENERIC_9x4
						: rows == 3 ? MenuType.GENERIC_9x3
						: rows == 2 ? MenuType.GENERIC_9x2
						: MenuType.GENERIC_9x1;
				int r = Math.min(Math.max(rows, 1), 6);
				player.openMenu(new SimpleMenuProvider(
						(id, inv, p) -> new BagMenu(type, id, inv, container, r, stack), title));
			}
		}
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
}
