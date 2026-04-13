 package com.overlast.tileentity;

 import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
 import com.overlast.lib.ModBlocks;
 import com.overlast.util.client.BlockColors;
 import net.minecraft.block.Block;
 import net.minecraft.client.renderer.BufferBuilder;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.client.renderer.Tessellator;
 import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
 import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
 import net.minecraft.init.Blocks;
 import net.minecraft.tileentity.TileEntityBeacon;
 import net.minecraft.util.ResourceLocation;
 import net.minecraft.util.math.BlockPos;
 import net.minecraft.util.math.MathHelper;

 import java.awt.*;
 import java.util.List;
 
 public class TileOldBeaconRenderer
   extends TileEntityBeaconRenderer
 {
   //public static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation("overlast:textures/entity/beacon_beam.png");
   //public static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("overlast:textures/entity/beacon.png");
   public static final ResourceLocation TEXTURE_SELECTION = new ResourceLocation("overlast:textures/misc/selection.png");
   //private final ModelBase modelEnderCrystal = (ModelBase)new ModelEnderCrystal(0.8F, false);
   
   private static boolean debug;
   
   private static boolean flashing;

   private static boolean spinning;
   private static boolean rainbow;
   private static boolean texturesNOT;
   private static boolean circle;
   private static boolean selectionVisible;
   private static int beams;
   private static boolean isSpinning;
   private static int offset;
   private static float newAlpha;
   
   public void render(TileEntityBeacon te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
     debug = false;
     flashing = false;
     spinning = false;
     rainbow = false;
     texturesNOT = false;
     selectionVisible = false;
     circle = false;
     isSpinning = false;
     beams = 4;
     offset = 0;
     newAlpha = 0.125F;
     if (checkBlock(te, 0, 1, 0, Blocks.GLASS)) {
       selectionVisible = true;
       renderComponentBlockSelection(x, y + 1.0D, z, BlockColors.GLASS);
     } 
     for (int i = -3; i < 4; i++) {
       for (int j = -3; j < 4; j++) {
         for (int k = -3; k < 4; k++) {
           //最终方块，额外增加一道光束
           if (checkBlock(te, i, j, k, ModBlocks.FinalSpecimen)) {
             spinning = true;
             if (selectionVisible) {
               renderComponentBlockSelection(x + i, y + j, z + k, BlockColors.QUARTZ_BLOCK);
             }
           }
           //黑曜石，RGB灯效
           if (checkBlock(te, i, j, k, Blocks.OBSERVER)) {
             texturesNOT = true;
             if (selectionVisible) {
               renderComponentBlockSelection(x + i, y + j, z + k, BlockColors.OBSERVER);
             }
           }
           //感染方块，减少光束，同时与阶段对应
           if (checkBlock(te, i, j, k, SRPBlocks.InfestedStain)||checkBlock(te, i, j, k, SRPBlocks.ParasiteStain)||checkBlock(te, i, j, k, SRPBlocks.InfestRemain)||checkBlock(te, i, j, k, SRPBlocks.InfestedRubble)||checkBlock(te, i, j, k, SRPBlocks.ParasiteRubble)) {
             beams--;
             if (selectionVisible) {
               renderComponentBlockSelection(x + i, y + j, z + k, BlockColors.REDSTONE_BLOCK);
             }
           }
           //火把，增强光束
           if (checkBlock(te, i, j, k, Blocks.TORCH)) {
             beams++;
             if (selectionVisible) {
               renderComponentBlockSelection(x + i, y + j, z + k, BlockColors.SLIME_BLOCK);
             }
           }
           //黑曜石，RGB灯效
           if (checkBlock(te, i, j, k, Blocks.OBSIDIAN)) {
             rainbow = true;
             if (selectionVisible) {
               float[] colorComponentValues = generateRainbow(te.getWorld().getTotalWorldTime(), partialTicks);
               bindTexture(TEXTURE_SELECTION);
               renderSelectionArea(x + i, y + j, z + k, 0.5D, colorComponentValues, 1.0F, 1.0D, 0);
             } 
           }
           //海晶石
           if (checkBlock(te, i, j, k, Blocks.PRISMARINE)) {
             newAlpha += 0.125F;
             if (selectionVisible) {
               renderComponentBlockSelection(x + i, y + j, z + k, BlockColors.PRISMARINE);
             }
           } 
         } 
       } 
     }
     renderBeacon(x, y, z, partialTicks, te.shouldBeamRender(), te.getBeamSegments(), te.getWorld().getTotalWorldTime());
   }
 
   
   public void renderBeacon(double x, double y, double z, double partialTicks, double textureScale, List<TileEntityBeacon.BeamSegment> beamSegments, double totalWorldTime) {
     if (selectionVisible) {
       float alpha1 = flash(totalWorldTime, partialTicks);
       bindTexture(TEXTURE_SELECTION);
       renderSelectionArea(x, y, z, 3.5D, new float[] { 1.0F, 1.0F, 1.0F }, alpha1, 0.14285714285714285D, -3);
     } 
     GlStateManager.alphaFunc(516, 0.1F);
     bindTexture(TEXTURE_BEACON_BEAM);
     
     if (textureScale > 0.0D) {
       
       GlStateManager.disableFog();
       int i = 0;
       
       for (int j = 0; j < beamSegments.size(); j++) {
         
         TileEntityBeacon.BeamSegment tileentitybeacon$beamsegment = beamSegments.get(j);
         
         float glowAmount = newAlpha;
         
         double x1 = x;
         double y1 = y;
         double z1 = z;
         
         float[] colors = tileentitybeacon$beamsegment.getColors();
         float[] colorComponentValues = colors;
         
         if (rainbow) {
           colorComponentValues = generateRainbow(totalWorldTime, partialTicks);
         }
         if (debug) {
           x1 = moveColor(x, colorComponentValues[0]);
           y1 = moveColor(y, colorComponentValues[1]);
           z1 = moveColor(z, colorComponentValues[2]);
         } 
         if (flashing) {
           glowAmount = flash(totalWorldTime, partialTicks);
         }
         
         x1 += offset;
         z1 += offset;
         
         renderBeamSegment(x1, y1, z1, partialTicks, textureScale, totalWorldTime, i, tileentitybeacon$beamsegment.getHeight(), colorComponentValues, 0.2D, 0.125D, glowAmount, beams);
         
         i += tileentitybeacon$beamsegment.getHeight();
       } 
       
       GlStateManager.enableFog();
     } 
   }
   
   public static float[] generateRainbow(double totalWorldTime, double partialTicks) {
     double d0 = totalWorldTime % 36000.0D + partialTicks;
     d0 /= 100.0D;
     int colorEncode = Color.HSBtoRGB((float)d0, 1.0F, 1.0F);
     int i1 = (colorEncode & 0xFF0000) >> 16;
     int j1 = (colorEncode & 0xFF00) >> 8;
     int k1 = (colorEncode & 0xFF) >> 0;
     return new float[] { i1 / 255.0F, j1 / 255.0F, k1 / 255.0F };
   }
 
   
   public static float[] getBlockColorNormalize(BlockColors colorIn) {
     float red = colorIn.red;
     float green = colorIn.green;
     float blue = colorIn.blue;
     
     return new float[] { red / 255.0F, green / 255.0F, blue / 255.0F };
   }
 
   
   public static float getBlockAlpha(BlockColors colorIn) {
     float alpha = colorIn.alpha;
     
     return alpha;
   }
 
   
   public static boolean checkBlock(TileEntityBeacon te, int rx, int ry, int rz, Block block) {
     BlockPos pos = te.getPos().add(rx, ry, rz);
     Block blockToCheck = te.getWorld().getBlockState(pos).getBlock();
     
     if (blockToCheck == block) {
       return true;
     }
     return false;
   }
   
   public static double moveColor(double x, float colorChannel) {
     return x + colorChannel;
   }
   
   public static float flash(double time, double partialTicks) {
     float f = (float)(time + partialTicks);
     float f1 = (float)(Math.sin(f * 0.05D) / 2.0D + 0.5D);
     return f1 / 1.3F + 0.115384616F;
   }
   
   public void renderComponentBlockSelection(double x, double y, double z, BlockColors colorIn) {
     float[] color = getBlockColorNormalize(colorIn);
     float alpha1 = getBlockAlpha(colorIn);
     bindTexture(TEXTURE_SELECTION);
     renderSelectionArea(x, y, z, 0.5D, color, alpha1, 1.0D, 0);
   }
   
   public static void renderSelectionArea(double x, double y, double z, double radius, float[] color, float alpha, double textureScale, int yOffset) {
     GlStateManager.glTexParameteri(3553, 10242, 10497);
     GlStateManager.glTexParameteri(3553, 10243, 10497);
     GlStateManager.disableLighting();
     GlStateManager.disableCull();
     GlStateManager.enableBlend();
     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
     GlStateManager.depthMask(false);
     Tessellator tessellator = Tessellator.getInstance();
     BufferBuilder bufferbuilder = tessellator.getBuffer();
     
     int height = (int)(radius * 2.0D);
     int i = yOffset + height;
     
     float f0 = color[0];
     float f1 = color[1];
     float f2 = color[2];
     float f3 = alpha;
     
     double d0 = 0.0D;
     double d1 = (height < 0) ? d0 : -d0;
     double d2 = MathHelper.frac(d1 * 0.2D - MathHelper.floor(d1 * 0.1D));
     double d3 = 0.5D - radius;
     double d4 = 0.5D - radius;
     double d5 = 0.5D + radius;
     double d6 = 0.5D - radius;
     double d7 = 0.5D - radius;
     double d8 = 0.5D + radius;
     double d9 = 0.5D + radius;
     double d10 = 0.5D + radius;
     double d11 = 0.0D;
     double d12 = 1.0D;
     double d13 = -1.0D + d2;
     double d14 = height * textureScale + d13;
     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
     bufferbuilder.pos(x + d3, y + i, z + d4).tex(1.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d3, y + yOffset, z + d4).tex(1.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d5, y + yOffset, z + d6).tex(0.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d5, y + i, z + d6).tex(0.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d9, y + i, z + d10).tex(1.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d9, y + yOffset, z + d10).tex(1.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d7, y + yOffset, z + d8).tex(0.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d7, y + i, z + d8).tex(0.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d5, y + i, z + d6).tex(1.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d5, y + yOffset, z + d6).tex(1.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d9, y + yOffset, z + d10).tex(0.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d9, y + i, z + d10).tex(0.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d7, y + i, z + d8).tex(1.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d7, y + yOffset, z + d8).tex(1.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d3, y + yOffset, z + d4).tex(0.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d3, y + i, z + d4).tex(0.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d9, y + i, z + d8).tex(1.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d9, y + i, z + d4).tex(1.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d3, y + i, z + d4).tex(0.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d3, y + i, z + d8).tex(0.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d9, y + yOffset, z + d8).tex(1.0D, d14).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d9, y + yOffset, z + d4).tex(1.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d3, y + yOffset, z + d4).tex(0.0D, d13).color(f0, f1, f2, f3).endVertex();
     bufferbuilder.pos(x + d3, y + yOffset, z + d8).tex(0.0D, d14).color(f0, f1, f2, f3).endVertex();
     tessellator.draw();
     GlStateManager.enableLighting();
     GlStateManager.depthMask(true);
   }
 
 
   
   public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors) {
     renderBeamSegment(x, y, z, partialTicks, textureScale, totalWorldTime, yOffset, height, colors, 0.2D, 0.25D, 0.125F, 5);
   }
 
   
   public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors, float glowAmount) {
     renderBeamSegment(x, y, z, partialTicks, textureScale, totalWorldTime, yOffset, height, colors, 0.2D, 0.25D, glowAmount, 5);
   }
 
   
   public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors, double beamRadius, double glowRadius) {
     renderBeamSegment(x, y, z, partialTicks, textureScale, totalWorldTime, yOffset, height, colors, beamRadius, glowRadius, 0.125F, 5);
   }
 
   
   public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors, double beamRadius, double glowRadius, float glowAmount) {
     renderBeamSegment(x, y, z, partialTicks, textureScale, totalWorldTime, yOffset, height, colors, beamRadius, glowRadius, glowAmount, 5);
   }
 
   
   public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors, double beamRadius, double glowRadius, float GlowAmount, int beams) {
     double time = totalWorldTime;
     int i = yOffset + height;
     GlStateManager.glTexParameteri(3553, 10242, 10497);
     GlStateManager.glTexParameteri(3553, 10243, 10497);
     GlStateManager.disableLighting();
     GlStateManager.disableCull();
     GlStateManager.disableBlend();
     GlStateManager.depthMask(true);
     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
     if (!texturesNOT) {
       GlStateManager.disableTexture2D();
     }
     Tessellator tessellator = Tessellator.getInstance();
     BufferBuilder bufferbuilder = tessellator.getBuffer();
     float f0 = (float)Math.floorMod((long)totalWorldTime, 160L) + (float)partialTicks;
     double d0 = totalWorldTime + partialTicks;
     double d1 = (height < 0) ? d0 : -d0;
     double d2 = MathHelper.frac(d1 * 0.2D - MathHelper.floor(d1 * 0.1D));
     double dRad = glowRadius;
     float f = colors[0];
     float f1 = colors[1];
     float f2 = colors[2];
     double d3 = d0 * 0.025D * -1.5D;
     double d4 = 0.5D + Math.cos(d3 + 2.356194490192345D) * beamRadius;
     double d5 = 0.5D + Math.sin(d3 + 2.356194490192345D) * beamRadius;
     double d6 = 0.5D + Math.cos(d3 + 0.7853981633974483D) * beamRadius;
     double d7 = 0.5D + Math.sin(d3 + 0.7853981633974483D) * beamRadius;
     double d8 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * beamRadius;
     double d9 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * beamRadius;
     double d10 = 0.5D + Math.cos(d3 + 5.497787143782138D) * beamRadius;
     double d11 = 0.5D + Math.sin(d3 + 5.497787143782138D) * beamRadius;
     double d12 = 0.0D;
     double d13 = 1.0D;
     double d14 = -1.0D + d2;
     double d15 = height * textureScale * 0.5D / beamRadius + d14;
     if (spinning) {
       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
       bufferbuilder.pos(x + d4, y + i, z + d5).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d4, y + yOffset, z + d5).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d6, y + yOffset, z + d7).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d6, y + i, z + d7).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d10, y + i, z + d11).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d10, y + yOffset, z + d11).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d8, y + yOffset, z + d9).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d8, y + i, z + d9).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d6, y + i, z + d7).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d6, y + yOffset, z + d7).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d10, y + yOffset, z + d11).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d10, y + i, z + d11).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d8, y + i, z + d9).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d8, y + yOffset, z + d9).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d4, y + yOffset, z + d5).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
       bufferbuilder.pos(x + d4, y + i, z + d5).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
       tessellator.draw();
     } 
     GlStateManager.enableBlend();
     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
     GlStateManager.depthMask(false);
     
     for (int b = 0; b < beams; b++) {
       d3 = 0.5D - glowRadius;
       d4 = 0.5D - glowRadius;
       d5 = 0.5D + glowRadius;
       d6 = 0.5D - glowRadius;
       d7 = 0.5D - glowRadius;
       d8 = 0.5D + glowRadius;
       d9 = 0.5D + glowRadius;
       d10 = 0.5D + glowRadius;
       d11 = 0.0D;
       d12 = 1.0D;
       d13 = -1.0D + d2;
       d14 = height * textureScale + d13;
       if (isSpinning) {
         d3 = d0 * 0.025D * -1.5D;
         d4 = 0.5D + Math.cos(d3 + 2.356194490192345D) * glowRadius;
         d5 = 0.5D + Math.sin(d3 + 2.356194490192345D) * glowRadius;
         d6 = 0.5D + Math.cos(d3 + 0.7853981633974483D) * glowRadius;
         d7 = 0.5D + Math.sin(d3 + 0.7853981633974483D) * glowRadius;
         d8 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * glowRadius;
         d9 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * glowRadius;
         d10 = 0.5D + Math.cos(d3 + 5.497787143782138D) * glowRadius;
         d11 = 0.5D + Math.sin(d3 + 5.497787143782138D) * glowRadius;
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         bufferbuilder.pos(x + d4, y + i, z + d5).tex(1.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d4, y + yOffset, z + d5).tex(1.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d6, y + yOffset, z + d7).tex(0.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d6, y + i, z + d7).tex(0.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d10, y + i, z + d11).tex(1.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d10, y + yOffset, z + d11).tex(1.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d8, y + yOffset, z + d9).tex(0.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d8, y + i, z + d9).tex(0.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d6, y + i, z + d7).tex(1.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d6, y + yOffset, z + d7).tex(1.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d10, y + yOffset, z + d11).tex(0.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d10, y + i, z + d11).tex(0.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d8, y + i, z + d9).tex(1.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d8, y + yOffset, z + d9).tex(1.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d4, y + yOffset, z + d5).tex(0.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d4, y + i, z + d5).tex(0.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         tessellator.draw();
       } else {
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         bufferbuilder.pos(x + d3, y + i, z + d4).tex(1.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d3, y + yOffset, z + d4).tex(1.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d5, y + yOffset, z + d6).tex(0.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d5, y + i, z + d6).tex(0.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d9, y + i, z + d10).tex(1.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d9, y + yOffset, z + d10).tex(1.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d7, y + yOffset, z + d8).tex(0.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d7, y + i, z + d8).tex(0.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d5, y + i, z + d6).tex(1.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d5, y + yOffset, z + d6).tex(1.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d9, y + yOffset, z + d10).tex(0.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d9, y + i, z + d10).tex(0.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d7, y + i, z + d8).tex(1.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d7, y + yOffset, z + d8).tex(1.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d3, y + yOffset, z + d4).tex(0.0D, d13).color(f, f1, f2, GlowAmount).endVertex();
         bufferbuilder.pos(x + d3, y + i, z + d4).tex(0.0D, d14).color(f, f1, f2, GlowAmount).endVertex();
         tessellator.draw();
       } 
       glowRadius += dRad;
     } 
     GlStateManager.enableLighting();
     GlStateManager.enableTexture2D();
     GlStateManager.depthMask(true);
   }
 

 }


