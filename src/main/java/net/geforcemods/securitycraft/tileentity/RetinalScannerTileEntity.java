package net.geforcemods.securitycraft.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.Option;
import net.geforcemods.securitycraft.api.Option.BooleanOption;
import net.geforcemods.securitycraft.blocks.RetinalScannerBlock;
import net.geforcemods.securitycraft.misc.CustomModules;
import net.geforcemods.securitycraft.util.BlockUtils;
import net.geforcemods.securitycraft.util.ClientUtils;
import net.geforcemods.securitycraft.util.ModuleUtils;
import net.geforcemods.securitycraft.util.PlayerUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.server.ServerLifecycleHooks;


public class RetinalScannerTileEntity extends DisguisableTileEntity {

	private BooleanOption activatedByEntities = new BooleanOption("activatedByEntities", false);
	
	 private GameProfile ownerProfile;
	 private static PlayerProfileCache profileCache;
	 private static MinecraftSessionService sessionService;

	public RetinalScannerTileEntity()
	{
		super(SCContent.teTypeRetinalScanner);
	}

	@Override
	public void entityViewed(LivingEntity entity){
		if(!world.isRemote && !BlockUtils.getBlockPropertyAsBoolean(world, pos, RetinalScannerBlock.POWERED)){
			if(!(entity instanceof PlayerEntity) && !activatedByEntities.asBoolean())
				return;

			if(entity instanceof PlayerEntity && PlayerUtils.isPlayerMountedOnCamera(entity))
				return;

			if(entity instanceof PlayerEntity && !getOwner().isOwner((PlayerEntity) entity) && !ModuleUtils.checkForModule(world, pos, (PlayerEntity)entity, CustomModules.WHITELIST)) {
				PlayerUtils.sendMessageToPlayer((PlayerEntity) entity, ClientUtils.localize(SCContent.retinalScanner.getTranslationKey()), ClientUtils.localize("messages.securitycraft:retinalScanner.notOwner").replace("#", getOwner().getName()), TextFormatting.RED);
				return;
			}

			BlockUtils.setBlockProperty(world, pos, RetinalScannerBlock.POWERED, true);
			world.getPendingBlockTicks().scheduleTick(new BlockPos(pos), SCContent.retinalScanner, 60);

			if(entity instanceof PlayerEntity)
				PlayerUtils.sendMessageToPlayer((PlayerEntity) entity, ClientUtils.localize(SCContent.retinalScanner.getTranslationKey()), ClientUtils.localize("messages.securitycraft:retinalScanner.hello").replace("#", entity.getName().getFormattedText()), TextFormatting.GREEN);
		}
	}

	@Override
	public int getViewCooldown() {
		return 30;
	}

	@Override
	public boolean activatedOnlyByPlayer() {
		return !activatedByEntities.asBoolean();
	}

	@Override
	public CustomModules[] acceptedModules() {
		return new CustomModules[]{CustomModules.WHITELIST, CustomModules.DISGUISE};
	}

	@Override
	public Option<?>[] customOptions() {
		return new Option[]{ activatedByEntities };
	}
		
	public static void setProfileCache(PlayerProfileCache profileCacheIn) {
		profileCache = profileCacheIn;
	}

	public static void setSessionService(MinecraftSessionService sessionServiceIn) {
		sessionService = sessionServiceIn;
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		super.write(tag);
		if (!StringUtils.isNullOrEmpty(this.getOwner().getName()) && !(this.getOwner().getName().equals("owner")))
		{
			if (this.ownerProfile == null || !this.getOwner().getName().equals(this.ownerProfile.getName()))
				this.setPlayerProfile(new GameProfile((UUID)null, this.getOwner().getName()));

			this.updatePlayerProfile();
			CompoundNBT ownerProfileTag = new CompoundNBT();
			NBTUtil.writeGameProfile(ownerProfileTag, this.ownerProfile);
			tag.put("ownerProfile", ownerProfileTag);
			return tag;
		}
		return tag;
	}

	@Override
	public void read(CompoundNBT tag) {
		super.read(tag);
		if (tag.contains("ownerProfile", 10))
			this.ownerProfile = NBTUtil.readGameProfile(tag.getCompound("ownerProfile"));
	}

	@Nullable
	public GameProfile getPlayerProfile() {
		return this.ownerProfile;
	}

	public void setPlayerProfile(@Nullable GameProfile profile) {
		this.ownerProfile = profile;
	}

	public void updatePlayerProfile() {
		this.ownerProfile = updateGameProfile(this.ownerProfile);
	}

	public static GameProfile updateGameProfile(GameProfile input) {
		if (profileCache == null)
			setProfileCache(ServerLifecycleHooks.getCurrentServer().getPlayerProfileCache());
		if(sessionService == null)
			setSessionService(ServerLifecycleHooks.getCurrentServer().getMinecraftSessionService());

		if (input != null && !StringUtils.isNullOrEmpty(input.getName())) {
			if (profileCache != null && sessionService != null) {
				GameProfile gameprofile = profileCache.getGameProfileForUsername(input.getName());
				if (gameprofile == null) {
					return input;
				} else {
					Property property = Iterables.getFirst(gameprofile.getProperties().get("textures"), (Property)null);
					if (property == null) {
						gameprofile = sessionService.fillProfileProperties(gameprofile, true);
					}

					return gameprofile;
				}
			} else {
				return input;
			}
		} else {
			return input;
		}
	}
}
