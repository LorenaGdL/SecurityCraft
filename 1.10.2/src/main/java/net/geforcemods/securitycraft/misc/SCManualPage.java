package net.geforcemods.securitycraft.misc;

import net.geforcemods.securitycraft.util.ClientUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SCManualPage {
	
	private Item item;
	private String helpInfo;
	private ItemStack[] customRecipe;

	public SCManualPage(Item item, String helpInfo){
		this.item = item;
		this.helpInfo = helpInfo;
		this.customRecipe = null;
	}
	
	public SCManualPage(Item item, String helpInfo, ItemStack[] customRecipe){
		this.item = item;
		this.helpInfo = helpInfo;
		this.customRecipe = customRecipe;
	}

	public Item getItem() {
		return item;
	}

	public String getItemName() {
		return item.getUnlocalizedName().substring(5).split("\\.")[0];
	}

	public String getHelpInfo() {
		return ClientUtils.localize(helpInfo);
	}
	
	public ItemStack[] getRecipe() {
		return customRecipe;
	}
	
	public boolean hasCustomRecipe() {
		return (customRecipe != null);
	}

}
