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
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.server.ServerLifecycleHooks;


public class RetinalScannerTileEntity extends DisguisableTileEntity implements ITickableTileEntity {

	private BooleanOption activatedByEntities = new BooleanOption("activatedByEntities", false);
	
	 private GameProfile playerProfile;
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
	
	//--- SKULL
	
	 public static void setProfileCache(PlayerProfileCache profileCacheIn) {
	      profileCache = profileCacheIn;
	   }

	   public static void setSessionService(MinecraftSessionService sessionServiceIn) {
	      sessionService = sessionServiceIn;
	   }

	   @Override
	   public CompoundNBT write(CompoundNBT compound) {
		   System.out.println("Write");
	      super.write(compound);
	      if (this.playerProfile != null) {
	         CompoundNBT compoundnbt = new CompoundNBT();
	         NBTUtil.writeGameProfile(compoundnbt, this.playerProfile);
	         compound.put("ownerProfile", compoundnbt);
			   System.out.println("Write - playerProfile: " + this.playerProfile);
	      }

	      return compound;
	   }

	   @Override
	   public void read(CompoundNBT compound) {
	    	 System.out.println("Test1");
	      super.read(compound);
	      if (compound.contains("ownerProfile", 10) && compound.contains("owner", 8)) {
	    	  GameProfile profile = NBTUtil.readGameProfile(compound.getCompound("ownerProfile"));
	    	  String profileOwner = profile.getName();
	    	  if (compound.getString("owner").equals(profileOwner))
	    		  this.setPlayerProfile(NBTUtil.readGameProfile(compound.getCompound("ownerProfile")));
	    	  else {
	    		 String s = compound.getString("owner");
	 	    	 System.out.println("Test3 - " + s);
	 	         if (!StringUtils.isNullOrEmpty(s)) {
	 	            this.setPlayerProfile(new GameProfile((UUID)null, s));
	 	         }
	    	  }
	    	  
	      } else if (compound.contains("owner", 8)) {
	         String s = compound.getString("owner");
	    	 System.out.println("Test2 - " + s);
	         if (!StringUtils.isNullOrEmpty(s)) {
	            this.setPlayerProfile(new GameProfile((UUID)null, s));
	         }
	      }
	      System.out.println("Read - " + compound);

	   }
	   
	   @Nullable
	   @OnlyIn(Dist.CLIENT)
	   public GameProfile getPlayerProfile() {
	      return this.playerProfile;
	   }

	   /**
	    * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
	    * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
	    */
	   @Nullable
	   @Override
	   public SUpdateTileEntityPacket getUpdatePacket() {
	      System.out.println("GetUpdatePacket");
	      return new SUpdateTileEntityPacket(this.pos, 4, this.getUpdateTag());
	   }

	   /**
	    * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
	    * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
	    */
	   @Override
	   public CompoundNBT getUpdateTag() {
	    	 System.out.println("GetUpdateTag");
		  CompoundNBT supertag=super.getUpdateTag();
	      return write(supertag);
	   }
	   
	    @Override
	    public void handleUpdateTag(CompoundNBT tag) {
	    	super.handleUpdateTag(tag);
	    	read(tag);
	    }
	   
	   @Override
	    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
	        // Here we get the packet from the server and read it into our client side tile entity
	    	 System.out.println("onDataPacket");
	        this.read(packet.getNbtCompound());
	    }

	   public void setPlayerProfile(@Nullable GameProfile p_195485_1_) {
	      this.playerProfile = p_195485_1_;
	      this.updatePlayerProfile();
	   }

	   private void updatePlayerProfile() {
	      this.playerProfile = updateGameProfile(this.playerProfile);
	      this.markDirty();
	   }

	   //CAREFUL HERE!!! TO REVIEW (ONLY CLIENT ADDED MANUALLY)
	   //@OnlyIn(Dist.CLIENT)
	   public static GameProfile updateGameProfile(GameProfile input) {
			//if (world.isRemote) {
			//	this.profileCache = Minecraft.getInstance().getIntegratedServer().getPlayerProfileCache();
			//	this.sessionService = Minecraft.getInstance().getSessionService();
			//}
			//else {
		   if (profileCache == null)
				setProfileCache(ServerLifecycleHooks.getCurrentServer().getPlayerProfileCache());
		   if(sessionService == null)
				setSessionService(ServerLifecycleHooks.getCurrentServer().getMinecraftSessionService());
			//}
	      if (input != null && !StringUtils.isNullOrEmpty(input.getName())) {
	    	  System.out.println("User name: " + input.getName());
	         if (input.isComplete() && input.getProperties().containsKey("textures")) {
	            return input;
	         } else if (profileCache != null && sessionService != null) {
	        	 System.out.println("NOT NULL!!!");
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
