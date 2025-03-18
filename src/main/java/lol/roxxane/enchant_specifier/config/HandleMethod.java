package lol.roxxane.enchant_specifier.config;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum HandleMethod {
	PRESERVE, REPLACE, REMOVE;
	public ItemStack replacement() {
		return switch (this) {
			case PRESERVE -> throw new IllegalStateException();
			case REPLACE -> Items.BOOK.getDefaultInstance();
			case REMOVE -> Items.AIR.getDefaultInstance();
		};
	}
}
