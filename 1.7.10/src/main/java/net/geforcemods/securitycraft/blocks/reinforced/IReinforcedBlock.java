package net.geforcemods.securitycraft.blocks.reinforced;

import java.util.List;

import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.minecraft.block.Block;
import scala.actors.threadpool.Arrays;

public interface IReinforcedBlock
{
	public static final List<Block> BLOCKS = Arrays.asList(new Block[] {
			mod_SecurityCraft.reinforcedBrick,
			mod_SecurityCraft.reinforcedCobblestone,
			mod_SecurityCraft.reinforcedCompressedBlocks,
			mod_SecurityCraft.reinforcedDirt,
			mod_SecurityCraft.reinforcedGlass,
			mod_SecurityCraft.reinforcedGlassPane,
			mod_SecurityCraft.reinforcedHardenedClay,
			mod_SecurityCraft.unbreakableIronBars,
			mod_SecurityCraft.reinforcedMetals,
			mod_SecurityCraft.reinforcedMossyCobblestone,
			mod_SecurityCraft.reinforcedNetherBrick,
			mod_SecurityCraft.reinforcedNewLogs,
			mod_SecurityCraft.reinforcedOldLogs,
			mod_SecurityCraft.reinforcedQuartz,
			mod_SecurityCraft.reinforcedSandstone,
			mod_SecurityCraft.reinforcedStainedHardenedClay,
			mod_SecurityCraft.reinforcedStone,
			mod_SecurityCraft.reinforcedStoneBrick,
			mod_SecurityCraft.reinforcedWoodPlanks,
			mod_SecurityCraft.reinforcedWool
	});

	public List<Block> getVanillaBlocks();

	public int getAmount();
}