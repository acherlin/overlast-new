 package com.overlast.util.client;
 
 public enum BeaconDyeColors {
   WHITE(16777215),
   ORANGE(16744192),
   MAGENTA(16711935),
   LIGHT_BLUE(8355839),
   YELLOW(16776960),
   LIME(65280),
   PINK(16711871),
   GRAY(8355711),
   SILVER(12566463),
   CYAN(32639),
   PURPLE(8323327),
   BLUE(255),
   BROWN(8339200),
   GREEN(32512),
   RED(16711680),
   BLACK(0);
   
   private final int newColor;
   
   BeaconDyeColors(int newColor) {
     this.newColor = newColor;
   }
   
   public int getNewColor() {
     return this.newColor;
   }
 }


