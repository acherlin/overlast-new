 package com.overlast.util.client;
 
 public enum BlockColors {
   PURPUR_BLOCK(234.0F, 119.0F, 255.0F, 1.0F),
   COMMAND_BLOCK(255.0F, 232.0F, 119.0F, 1.0F),
   QUARTZ_BLOCK(255.0F, 255.0F, 255.0F, 1.0F),
   GLASS(255.0F, 255.0F, 255.0F, 0.5F),
   OBSIDIAN(127.0F, 0.0F, 255.0F, 1.0F),
   REDSTONE_BLOCK(255.0F, 0.0F, 0.0F, 1.0F),
   SLIME_BLOCK(0.0F, 255.0F, 0.0F, 1.0F),
   OBSERVER(127.0F, 127.0F, 127.0F, 1.0F),
   SEA_LANTERN(0.0F, 255.0F, 255.0F, 1.0F),
   PRISMARINE(0.0F, 255.0F, 255.0F, 1.0F),
   LAPIS_BLOCK(0.0F, 0.0F, 255.0F, 1.0F),
   COAL_BLOCK(0.0F, 0.0F, 0.0F, 1.0F),
   END_STONE(255.0F, 255.0F, 100.0F, 1.0F),
   END_STONE_BRICKS(255.0F, 255.0F, 100.0F, 1.0F);
   
   public float red;
   
   public float green;
   
   public float blue;
   
   public float alpha;
   
   BlockColors(float red, float green, float blue, float alpha) {
     this.red = red;
     this.green = green;
     this.blue = blue;
     this.alpha = alpha;
   }
 }


