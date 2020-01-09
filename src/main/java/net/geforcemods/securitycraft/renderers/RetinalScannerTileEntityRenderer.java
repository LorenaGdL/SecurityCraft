package net.geforcemods.securitycraft.renderers;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.Map;
import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import net.geforcemods.securitycraft.blocks.RetinalScannerBlock;
import net.geforcemods.securitycraft.misc.CustomModules;
import net.geforcemods.securitycraft.tileentity.RetinalScannerTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RetinalScannerTileEntityRenderer extends TileEntityRenderer<RetinalScannerTileEntity> {
   public static RetinalScannerTileEntityRenderer instance;

   public void render(RetinalScannerTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
      //float f = tileEntityIn.getAnimationProgress(partialTicks);
      float f = 0.0F;
      BlockState blockstate = tileEntityIn.getBlockState();
      System.out.println("isDisguised: " + tileEntityIn.hasModule(CustomModules.DISGUISE));
      boolean flag = blockstate.getBlock() instanceof WallSkullBlock;
      //Direction direction = flag ? blockstate.get(WallSkullBlock.FACING) : null;
      Direction direction = blockstate.get(RetinalScannerBlock.FACING);
      float f1 = 22.5F * (float)(flag ? (2 + direction.getHorizontalIndex()) * 4 : 0.0F);
      if (!tileEntityIn.hasModule(CustomModules.DISGUISE))
    	  this.render((float)x, (float)y, (float)z, direction, f1, tileEntityIn.getPlayerProfile(), destroyStage, f);
   }

   public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
      super.setRendererDispatcher(rendererDispatcherIn);
      instance = this;
   }

   public void render(float x, float y, float z, @Nullable Direction facing, float rotationIn, @Nullable GameProfile playerProfile, int destroyStage, float animationProgress) {
      
      if (destroyStage >= 0) {
         this.bindTexture(DESTROY_STAGES[destroyStage]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scalef(4.0F, 2.0F, 1.0F);
         GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         this.bindTexture(this.func_199356_a(playerProfile));
      }

      GlStateManager.pushMatrix();
      
      //GlStateManager.disableCull();
//      if (facing == null) {
//         GlStateManager.translatef(x + 0.5F, y + 1.0F/16.0F, z + 0.25F);
//      } else {
//         switch(facing) {
//         case NORTH:
//            GlStateManager.translatef(x + 0.5F, y + 0.25F, z + 0.74F);
//            break;
//         case SOUTH:
//            GlStateManager.translatef(x + 0.5F, y + 0.25F, z + 0.26F);
//            break;
//         case WEST:
//            GlStateManager.translatef(x + 0.74F, y + 0.25F, z + 0.5F);
//            break;
//         case EAST:
//         default:
//            GlStateManager.translatef(x + 0.26F, y + 0.25F, z + 0.5F);
//         }
//      }
      
      
      if (facing == null)
    	  return;
      else {
        switch(facing) {
        case NORTH:
            GlStateManager.translatef(x + 0.25F, y + 1.0F/16.0F, z); //translate to block corner
           break;
        case SOUTH:
        	GlStateManager.translatef(x + 0.75F, y + 1.0F/16.0F, z + 1.0F);
            GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
           break;
        case WEST:
        	GlStateManager.translatef(x, y + 1.0F/16.0F, z + 0.75F);
            GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
           break;
        case EAST:
        	GlStateManager.translatef(x + 1.0F, y + 1.0F/16.0F, z + 0.25F);
            GlStateManager.rotatef(270.0F, 0.0F, 1.0F, 0.0F);
        	break;
        default:
        	break;
        }
     }
  
      
      //GlStateManager.translatef(x + 0.25F, y + 1.0F/16.0F, z); //translate to block corner

      GlStateManager.enableRescaleNormal();
      GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
      GlStateManager.enableAlphaTest();
      RenderHelper.disableStandardItemLighting();

      //if (type == SkullBlock.Types.PLAYER) {
         //GlStateManager.setProfile(GlStateManager.Profile.PLAYER_SKIN);
      //}

      //genericheadmodel.func_217104_a(animationProgress, 0.0F, 0.0F, rotationIn, 0.0F, 0.0625F); //renders head
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      
      //GlStateManager.enableBlend();
      //GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ZERO);
      
      //face
      bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(0, 0, -0.125/16.0).tex(0.125, 0.25).endVertex();
      bufferbuilder.pos(0, -0.5, -0.125/16.0).tex(0.125, 0.125).endVertex();
      bufferbuilder.pos(-0.5, -0.5, -0.125/16.0).tex(0.25, 0.125).endVertex();
      bufferbuilder.pos(-0.5, 0, -0.125/16.0).tex(0.25, 0.25).endVertex();

      tessellator.draw();
      
      //helmet front
      bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(0, 0, -0.25/16.0).tex(0.625, 0.25).endVertex();
      bufferbuilder.pos(0, -0.5, -0.25/16.0).tex(0.625, 0.125).endVertex();
      bufferbuilder.pos(-0.5, -0.5, -0.25/16.0).tex(0.75, 0.125).endVertex();
      bufferbuilder.pos(-0.5, 0, -0.25/16.0).tex(0.75, 0.25).endVertex();

      tessellator.draw();

      //GlStateManager.disableBlend();
      
      GlStateManager.popMatrix();
      
      //CUSTOM CODE -----  
      GlStateManager.pushMatrix();

      GlStateManager.scalef(1.0F, 1.0F, 1.0F); //restore scaling
      GlStateManager.translatef(x, y, z); //translate to block corner
      RenderHelper.enableStandardItemLighting();
      //GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      //GlStateManager.enableBlend();

//      Tessellator tessellator = Tessellator.getInstance();
//      BufferBuilder bufferbuilder = tessellator.getBuffer();
      
      /*
      this.bindTexture (new ResourceLocation(SecurityCraft.MODID, "textures/block/retinal_scanner_front_hole.png"));

      bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(0, 0, 0).tex(0, 1).endVertex();
      bufferbuilder.pos(0, 1, 0).tex(0, 0).endVertex();
      bufferbuilder.pos(1, 1, 0).tex(1, 0).endVertex();
      bufferbuilder.pos(1, 0, 0).tex(1, 1).endVertex();

      tessellator.draw();
      
      this.bindTexture (new ResourceLocation("textures/block/furnace_side.png"));

      bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(1, 0, 0).tex(0, 1).endVertex();
      bufferbuilder.pos(1, 1, 0).tex(0, 0).endVertex();
      bufferbuilder.pos(1, 1, 1).tex(1, 0).endVertex();
      bufferbuilder.pos(1, 0, 1).tex(1, 1).endVertex();
      
      tessellator.draw();
      
      
      bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(0, 0, 0).tex(0, 1).endVertex();
      bufferbuilder.pos(0, 0, 1).tex(1, 1).endVertex();
      bufferbuilder.pos(0, 1, 1).tex(1, 0).endVertex();
      bufferbuilder.pos(0, 1, 0).tex(0, 0).endVertex();
      
      tessellator.draw();
      
      bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(0, 0, 1).tex(0, 1).endVertex();
      bufferbuilder.pos(1, 0, 1).tex(1, 1).endVertex();
      bufferbuilder.pos(1, 1, 1).tex(1, 0).endVertex();
      bufferbuilder.pos(0, 1, 1).tex(0, 0).endVertex();
      
      tessellator.draw();
      
      this.bindTexture (new ResourceLocation("textures/block/furnace_top.png"));

      bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(0, 1, 0).tex(0, 1).endVertex();
      bufferbuilder.pos(0, 1, 1).tex(0, 0).endVertex();
      bufferbuilder.pos(1, 1, 1).tex(1, 0).endVertex();
      bufferbuilder.pos(1, 1, 0).tex(1, 1).endVertex();
      
      tessellator.draw();
      */

      //RenderHelper.enableStandardItemLighting();
      //GlStateManager.disableBlend();
      //GlStateManager.enableCull();


      // END CUSTOM CODE ---------
      
      
      GlStateManager.popMatrix();
      if (destroyStage >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }

   }

   private ResourceLocation func_199356_a(@Nullable GameProfile p_199356_2_) {
      ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();
      if (p_199356_2_ != null) {
         Minecraft minecraft = Minecraft.getInstance();
         Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(p_199356_2_);
         if (map.containsKey(Type.SKIN)) {
            resourcelocation = minecraft.getSkinManager().loadSkin(map.get(Type.SKIN), Type.SKIN);
         } else {
            resourcelocation = DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(p_199356_2_));
         }
      }

      return resourcelocation;
   }
}