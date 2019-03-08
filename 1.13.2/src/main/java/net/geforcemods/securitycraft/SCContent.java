package net.geforcemods.securitycraft;

import net.geforcemods.securitycraft.api.TileEntitySCTE;
import net.geforcemods.securitycraft.blocks.mines.BlockMine;
import net.geforcemods.securitycraft.entity.EntityBouncingBetty;
import net.geforcemods.securitycraft.entity.EntityBullet;
import net.geforcemods.securitycraft.entity.EntityIMSBomb;
import net.geforcemods.securitycraft.entity.EntitySecurityCamera;
import net.geforcemods.securitycraft.entity.EntitySentry;
import net.geforcemods.securitycraft.entity.EntityTaserBullet;
import net.geforcemods.securitycraft.items.ItemModule;
import net.geforcemods.securitycraft.tileentity.TileEntityAlarm;
import net.geforcemods.securitycraft.tileentity.TileEntityCageTrap;
import net.geforcemods.securitycraft.tileentity.TileEntityClaymore;
import net.geforcemods.securitycraft.tileentity.TileEntityIMS;
import net.geforcemods.securitycraft.tileentity.TileEntityInventoryScanner;
import net.geforcemods.securitycraft.tileentity.TileEntityKeycardReader;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypad;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadChest;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadFurnace;
import net.geforcemods.securitycraft.tileentity.TileEntityLaserBlock;
import net.geforcemods.securitycraft.tileentity.TileEntityLogger;
import net.geforcemods.securitycraft.tileentity.TileEntityMotionLight;
import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.geforcemods.securitycraft.tileentity.TileEntityPortableRadar;
import net.geforcemods.securitycraft.tileentity.TileEntityProtecto;
import net.geforcemods.securitycraft.tileentity.TileEntityRetinalScanner;
import net.geforcemods.securitycraft.tileentity.TileEntityScannerDoor;
import net.geforcemods.securitycraft.tileentity.TileEntitySecretSign;
import net.geforcemods.securitycraft.tileentity.TileEntitySecurityCamera;
import net.geforcemods.securitycraft.tileentity.TileEntityTrackMine;
import net.geforcemods.securitycraft.util.Reinforced;
import net.geforcemods.securitycraft.util.Tinted;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class SCContent
{
	//Blocks
	public static Block alarm;
	public static Block alarmLit;
	public static Block bogusLavaFlowing;
	public static Block bogusWaterFlowing;
	public static Block bouncingBetty;
	public static Block cageTrap;
	public static Block claymore;
	public static Block cobblestoneMine;
	public static Block diamondOreMine;
	public static Block dirtMine;
	public static Block frame;
	public static Block furnaceMine;
	public static Block gravelMine;
	public static Block ims;
	public static Block inventoryScanner;
	public static Block inventoryScannerField;
	public static Block ironFence;
	public static Block ironTrapdoor;
	public static Block keycardReader;
	public static Block keypad;
	public static Block keypadChest;
	public static Block keypadFurnace;
	public static Block laserBlock;
	public static Block laserField;
	public static Block motionActivatedLight;
	public static Block panicButton;
	public static Block portableRadar;
	public static Block protecto;
	@Reinforced @Tinted public static Block reinforcedBoneBlock;
	@Reinforced @Tinted public static Block reinforcedBrick;
	@Reinforced @Tinted public static Block reinforcedCarpet;
	@Reinforced @Tinted public static Block reinforcedCobblestone;
	@Reinforced @Tinted public static Block reinforcedCompressedBlocks;
	@Reinforced @Tinted public static Block reinforcedConcrete;
	@Reinforced @Tinted public static Block reinforcedDirt;
	public static Block reinforcedDoor;
	@Tinted public static Block reinforcedDoubleStoneSlabs;
	@Tinted public static Block reinforcedDoubleStoneSlabs2;
	@Tinted public static Block reinforcedDoubleWoodSlabs;
	@Reinforced @Tinted public static Block reinforcedEndStone;
	@Reinforced @Tinted public static Block reinforcedEndStoneBricks;
	public static Block reinforcedFencegate;
	@Reinforced public static Block reinforcedGlass;
	@Reinforced public static Block reinforcedGlassPane;
	@Reinforced @Tinted public static Block reinforcedGlowstone;
	@Reinforced @Tinted public static Block reinforcedGravel;
	@Reinforced @Tinted public static Block reinforcedHardenedClay;
	@Reinforced public static Block reinforcedIronBars;
	@Reinforced @Tinted public static Block reinforcedMetals;
	@Reinforced @Tinted public static Block reinforcedMossyCobblestone;
	@Reinforced @Tinted public static Block reinforcedNetherBrick;
	@Reinforced @Tinted public static Block reinforcedNetherrack;
	@Reinforced @Tinted public static Block reinforcedNewLogs;
	@Reinforced @Tinted public static Block reinforcedObsidian;
	@Reinforced @Tinted public static Block reinforcedOldLogs;
	@Reinforced @Tinted public static Block reinforcedPrismarine;
	@Reinforced @Tinted public static Block reinforcedPurpur;
	@Reinforced @Tinted public static Block reinforcedQuartz;
	@Reinforced @Tinted public static Block reinforcedRedNetherBrick;
	@Reinforced @Tinted public static Block reinforcedRedSandstone;
	@Reinforced @Tinted public static Block reinforcedSand;
	@Reinforced @Tinted public static Block reinforcedSandstone;
	@Reinforced @Tinted public static Block reinforcedSeaLantern;
	@Reinforced public static Block reinforcedStainedGlass;
	@Reinforced public static Block reinforcedStainedGlassPanes;
	@Reinforced @Tinted public static Block reinforcedStainedHardenedClay;
	@Tinted public static Block reinforcedStairsAcacia;
	@Tinted public static Block reinforcedStairsBirch;
	@Tinted public static Block reinforcedStairsBrick;
	@Tinted public static Block reinforcedStairsCobblestone;
	@Tinted public static Block reinforcedStairsDarkoak;
	@Tinted public static Block reinforcedStairsJungle;
	@Tinted public static Block reinforcedStairsNetherBrick;
	@Tinted public static Block reinforcedStairsOak;
	@Tinted public static Block reinforcedStairsPurpur;
	@Tinted public static Block reinforcedStairsQuartz;
	@Tinted public static Block reinforcedStairsRedSandstone;
	@Tinted public static Block reinforcedStairsSandstone;
	@Tinted public static Block reinforcedStairsSpruce;
	@Tinted public static Block reinforcedStairsStone;
	@Tinted public static Block reinforcedStairsStoneBrick;
	@Reinforced @Tinted public static Block reinforcedStone;
	@Reinforced @Tinted public static Block reinforcedStoneBrick;
	@Tinted public static Block reinforcedStoneSlabs;
	@Tinted public static Block reinforcedStoneSlabs2;
	@Reinforced @Tinted public static Block reinforcedWoodPlanks;
	@Tinted public static Block reinforcedWoodSlabs;
	@Reinforced @Tinted public static Block reinforcedWool;
	public static Block retinalScanner;
	public static Block sandMine;
	public static Block scannerDoor;
	public static Block secretSignStanding;
	public static Block secretSignWall;
	public static Block securityCamera;
	public static Block stoneMine;
	public static Block trackMine;
	public static Block usernameLogger;
	public static BlockMine mine;
	public static BlockMine mineCut;
	public static BlockStaticLiquid bogusLava;
	public static BlockStaticLiquid bogusWater;

	//Items
	public static Item adminTool;
	public static Item briefcase;
	public static Item cameraMonitor;
	public static Item codebreaker;
	public static Item fLavaBucket;
	public static Item fWaterBucket;
	public static Item keycardLvl1;
	public static Item keycardLvl2;
	public static Item keycardLvl3;
	public static Item keycardLvl4;
	public static Item keycardLvl5;
	public static Item keyPanel;
	public static Item limitedUseKeycard;
	public static Item reinforcedDoorItem;
	public static Item remoteAccessMine;
	public static Item scannerDoorItem;
	public static Item scManual;
	public static Item secretSignItem;
	public static Item sentry;
	public static Item taser;
	public static Item taserPowered;
	public static Item universalBlockModifier;
	public static Item universalBlockReinforcerLvL1;
	public static Item universalBlockReinforcerLvL2;
	public static Item universalBlockReinforcerLvL3;
	public static Item universalBlockRemover;
	public static Item universalKeyChanger;
	public static Item universalOwnerChanger;
	public static Item wireCutters;

	//Modules
	public static ItemModule blacklistModule;
	public static ItemModule disguiseModule;
	public static ItemModule harmingModule;
	public static ItemModule redstoneModule;
	public static ItemModule smartModule;
	public static ItemModule storageModule;
	public static ItemModule whitelistModule;

	//Tile entity typses
	public static TileEntityType<TileEntityOwnable> teTypeOwnable;
	public static TileEntityType<TileEntitySCTE> teTypeAbstract;
	public static TileEntityType<TileEntityKeypad> teTypeKeypad;
	public static TileEntityType<TileEntityLaserBlock> teTypeLaserBlock;
	public static TileEntityType<TileEntityCageTrap> teTypeCageTrap;
	public static TileEntityType<TileEntityKeycardReader> teTypeKeycardReader;
	public static TileEntityType<TileEntityInventoryScanner> teTypeInventoryScanner;
	public static TileEntityType<TileEntityPortableRadar> teTypePortableRadar;
	public static TileEntityType<TileEntitySecurityCamera> teTypeSecurityCamera;
	public static TileEntityType<TileEntityLogger> teTypeUsernameLogger;
	public static TileEntityType<TileEntityRetinalScanner> teTypeRetinalScanner;
	public static TileEntityType<TileEntityKeypadChest> teTypeKeypadChest;
	public static TileEntityType<TileEntityAlarm> teTypeAlarm;
	public static TileEntityType<TileEntityClaymore> teTypeClaymore;
	public static TileEntityType<TileEntityKeypadFurnace> teTypeKeypadFurnace;
	public static TileEntityType<TileEntityIMS> teTypeIms;
	public static TileEntityType<TileEntityProtecto> teTypeProtecto;
	public static TileEntityType<TileEntityScannerDoor> teTypeScannerDoor;
	public static TileEntityType<TileEntitySecretSign> teTypeSecretSign;
	public static TileEntityType<TileEntityMotionLight> teTypeMotionLight;
	public static TileEntityType<TileEntityTrackMine> teTypeTrackMine;

	//Entity types
	public static EntityType<EntityBouncingBetty> eTypeBouncingBetty;
	public static EntityType<EntityTaserBullet> eTypeTaserBullet;
	public static EntityType<EntityIMSBomb> eTypeImsBomb;
	public static EntityType<EntitySecurityCamera> eTypeSecurityCamera;
	public static EntityType<EntitySentry> eTypeSentry;
	public static EntityType<EntityBullet> eTypeBullet;
}
