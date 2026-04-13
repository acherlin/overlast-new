package com.overlast.cap.temperature;

import com.overlast.config.OverConfig;
import com.overlast.lib.ModItems;
import com.overlast.lib.ModMobEffects;
import com.overlast.season.Season;
import com.overlast.season.WorldSeason;
import com.overlast.util.ProximityDetect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

/*
 * Where temperature is directly and indirectly modified.
 */

public class TemperatureModifier {

    private int weatherTimer = 0;

	public void onPlayerUpdate(EntityPlayer player) {
		
		// Capabilities
		ITemperature temperature = player.getCapability(TemperatureProvider.TEMPERATURE_CAP, null);

        // Player's coordinates. 玩家的所处坐标
        double playerPosX = player.posX;
        double playerPosY = player.posY;
        double playerPosZ = player.posZ;

        // Player's Block position.
        BlockPos blockPos = player.getPosition();

        // =============================================
        //       FIRST FACTOR: THE BIOME
        // =============================================
        // Technically, the temperature of the biome. The temperature for the plains biome is 0.8. Desert is 2.0. Cold Taiga is -0.5. Blah blah blah

        // Biome at player position.
        // =============================================
        // 第一个因素：生物群落
        // =============================================
        // 从技术上讲，是指生物群落的温度。平原生物群落的温度是0.8。沙漠是2.0。寒冷的泰加山脉是-0.5。诸如此类
        // 玩家位置的生物群落。
        Biome biome = player.world.getBiome(blockPos);

        // Biome's temperature.  生物群落的温度。
        float biomeTemp = biome.getTemperature(blockPos);

        // Some cold biomes are too cold. Make them not so.
        // The hot biomes are too hot for the newTargetTemp below. Make them a bit less hot.
        // 一些寒冷的生物群落太冷了。让它们不再如此。
        // 热的生物群落对于下面的newTargetTemp来说太热了。让它们的温度降低一些。
        //这里不使用这个设定，这个设定会导致生存过于复杂化

        if (biomeTemp < -0.2) {
            biomeTemp = -0.2f;
        }
        else if (biomeTemp > 1.5) {
            biomeTemp = 1.5f;
        }


        // If in a cave, stick with a cool, constant temperature  如果在山洞里，坚持使用凉爽、恒定的温度
        if (playerPosY <= (player.world.getSeaLevel() - 15)) {
            biomeTemp = 0.7f;
        }

        // New target temperature based on biome. This constant right here could change. Who knows.  基于生物群落的新目标温度。这里的这个常数可能会改变。谁知道呢。
        float newTargetTemp = 70 * biomeTemp;

        // Set it. any other factor will either add to it or take from it. 设置它。任何其他因素要么增加它，要么从它那里拿走。
        temperature.setTarget(newTargetTemp);

        // =====================================================
        //        MISC. FACTORS AFFECTING TARGET TEMP
        // =====================================================

        // Is the player directly in the sun?
        // =====================================================
        // MISC. 影响目标温度的因素
        // 玩家是否直接在阳光下？
        if (player.world.isDaytime() && player.world.canBlockSeeSky(blockPos)) {

            temperature.increaseTarget(10.0f);
        }

        else if (!player.world.isDaytime() && playerPosY >= (player.world.getSeaLevel() - 10)) {
            //晚上，且位于洞穴，温度-10
            temperature.decreaseTarget(10.0f);
        }

        else if (!player.world.canBlockSeeSky(blockPos)) {
            //否则晚上-5
            temperature.decreaseTarget(5.0f);
        }

        // Is the player in the rain? 玩家在雨中吗？
        if (player.isWet()) {
            temperature.decreaseTarget(15.0f);
        }


        // ==================================
        //        PROXIMITY DETECT
        // ==================================

        // These blocks of if-statements are used to detect blocks near the player, either warming them or cooling them.
        // Check if the player is near fire. Warm the player.
        // ==================================
        // 近距离检测
        // 这些块状的if语句用来检测玩家附近的块状物，要么给它们加温，要么给它们降温。
        // 检查玩家是否在火附近。温暖玩家。

        // Lava. Warm the player. 熔岩，温暖玩家。
        if (ProximityDetect.isBlockNextToPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.LAVA, player)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(40.0f); } else { temperature.increaseTarget(45.0f); } }
        else if (ProximityDetect.isBlockNearPlayer2(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.LAVA, player, false)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(20.0f); } else { temperature.increaseTarget(25.0f); } }
        else if (ProximityDetect.isBlockUnderPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.LAVA, player)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(40.0f); } else { temperature.increaseTarget(45.0f); } }
        else if (ProximityDetect.isBlockUnderPlayer2(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.LAVA, player, false)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(20.0f); } else { temperature.increaseTarget(25.0f); } }
        else if (ProximityDetect.isBlockNextToPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.FLOWING_LAVA, player)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(30.0f); } else { temperature.increaseTarget(35.0f); } }
        else if (ProximityDetect.isBlockNearPlayer2(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.FLOWING_LAVA, player, false)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(15.0f); } else { temperature.increaseTarget(20.0f); } }
        else if (ProximityDetect.isBlockUnderPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.FLOWING_LAVA, player)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(30.0f); } else { temperature.increaseTarget(35.0f); } }
        else if (ProximityDetect.isBlockUnderPlayer2(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.FLOWING_LAVA, player, false)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(15.0f); } else { temperature.increaseTarget(20.0f); } }

        // Lit furnace. could act as a heater for now. same y-level only. And at the face.
        // 点燃的炉子。可以暂时充当加热器。同样的y等级。
        if (ProximityDetect.isBlockNextToPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.LIT_FURNACE, player)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(15.0f); } else { temperature.increaseTarget(30.0f); } }
        else if (ProximityDetect.isBlockNearPlayer2(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.LIT_FURNACE, player, false)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(7.5f); } else { temperature.increaseTarget(15.0f); } }
        else if (ProximityDetect.isBlockAtPlayerFace(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.LIT_FURNACE, player)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(15.0f); } else { temperature.increaseTarget(30.0f); } }
        else if (ProximityDetect.isBlockAtPlayerFace2(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.LIT_FURNACE, player, false)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(7.5f); } else { temperature.increaseTarget(15.0f); } }

        // Magma block. One y-level down only. 岩浆块。仅有一个y级向下。
        if (ProximityDetect.isBlockUnderPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.MAGMA, player)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(10.0f); } else { temperature.increaseTarget(20.0f); } }
        else if (ProximityDetect.isBlockUnderPlayer2(blockPos.getX(), blockPos.getY(), blockPos.getZ(), Blocks.MAGMA, player, false)) { if (player.world.canBlockSeeSky(blockPos)) { temperature.increaseTarget(5.0f); } else { temperature.increaseTarget(10.0f); } }



        //最终目标温度，理论上非天灾期间应该不会出现极端的情况
        if(temperature.getTargetTemperature()>=80&&!(WorldSeason.getSeason()==Season.SUMMER)) {
            temperature.setTarget(80);
        }

        if(temperature.getTargetTemperature()<=20&&!(WorldSeason.getSeason()==Season.WINTER)) {
            temperature.setTarget(20);
        }


        // Actually affect the player's temperature. Explained in greater detail at the method.
        //实际上影响到玩家的温度。在方法上有更详细的解释。
        changeRateOfTemperature(player);

        // ======================================
        //             SIDE EFFECTS
        // ======================================

        // Heat stroke, hyperthermia, heat exhaustion, whatever you call it, include the side effects of fatigue, nausea, and thirst.
        // The opposite (hypothermia, frostbite...) includes fatigue, shivering, and more fatigue.
        // Here, it'll start off as some slowness, because being hot or cold just sucks.
        // Then it'll get more and more serious.


        // Overheating (dehydration is taken care of in ThirstModifier).
        // Damage player
        // 过热（脱水在ThirstModifier中得到了处理）。

        //夏季，白天，露天，无雨
        if (WorldSeason.getSeason() == Season.SUMMER && player.world.isDaytime() && player.world.canBlockSeeSky(blockPos)) {
            temperature.setTarget(120);
        } else if (WorldSeason.getSeason() == Season.SUMMER && playerPosY >= player.world.getSeaLevel()) {
            temperature.increaseTarget(10);
        }
        //冬季，全天，露天，无雨
        if (WorldSeason.getSeason() == Season.WINTER && player.world.canBlockSeeSky(blockPos) && !player.world.isDaytime()) {
            temperature.setTarget(-20);
        } else if (WorldSeason.getSeason() == Season.WINTER && playerPosY >= player.world.getSeaLevel() && player.world.canBlockSeeSky(blockPos)&& player.world.isDaytime()) {
            temperature.decreaseTarget(20);
        } else if (WorldSeason.getSeason() == Season.WINTER && playerPosY >= player.world.getSeaLevel()) {
            temperature.decreaseTarget(10);
        }


        if (weatherTimer < 20) {
            // Increment timer until it reaches 20. 计时器递增，直到达到20。一秒一次
            weatherTimer++;
        }
        else {
            weatherTimer=0;
            // 高于100过热
            if (temperature.getTemperature() > 110.0f&&OverConfig.SEASONS.enableSummerPlayerEffect) {
                player.addPotionEffect(new PotionEffect(ModMobEffects.OVERHEATING, 100, 0, false, false));
                player.setFire(1);
            }else if (temperature.getTemperature() > 80.0f&&OverConfig.SEASONS.enableSummerPlayerEffect) {
                // 高于80高温辐射
                player.addPotionEffect(new PotionEffect(ModMobEffects.HEATRADIATION, 100, 0, false, false));
            }

            // Freezing
            // 低温
            if (temperature.getTemperature() < 15.0f&&temperature.getTemperature() >= 5.0f&&OverConfig.SEASONS.enableWinterPlayerEffect) {
                player.addPotionEffect(new PotionEffect(ModMobEffects.HYPOTHERMIA, 100, 0, false, false));
            }else if (temperature.getTemperature() < 5.0f&&OverConfig.SEASONS.enableWinterPlayerEffect) {
                // 霜冻
                player.addPotionEffect(new PotionEffect(ModMobEffects.FROSTY, 100, 0, false, false));
            }
        }
	}
	
	// This checks any consumed item by the player, and affects temperature accordingly.
    //  这将检查玩家的任何消耗品，并对温度产生相应影响。
	public void onPlayerConsumeItem(EntityPlayer player, ItemStack item) {
		
		// Capability 能力
		ITemperature temperature = player.getCapability(TemperatureProvider.TEMPERATURE_CAP, null);

        // Number of items. 物品的数量
        int amount = item.getCount();

        // If cooked food, increase temperature. 如果是煮熟的食物，要提高温度。
        if (item.areItemStacksEqual(item, new ItemStack(Items.COOKED_CHICKEN, amount))) { temperature.increase(5.0f); }
        else if (item.areItemStacksEqual(item, new ItemStack(ModItems.Ice_Sucker, amount))) { temperature.decrease(10.0f); }
        else if (item.areItemStacksEqual(item, new ItemStack(ModItems.Melon_Ice, amount))) { temperature.decrease(10.0f); }
	}

	// This is called in order to affect the rate of the player's temperature, based on the target temperature.
	// The bigger the difference between the target temp and player temp, the quicker the player temp changes towards the target temp, positive or negative.
    //调用这个功能是为了根据目标温度来影响玩家的温度变化速度。
    //目标温度和玩家温度之间的差异越大，玩家温度向目标温度变化的速度就越快，无论是正还是负。
	private void changeRateOfTemperature(EntityPlayer player) {

        // Capability
        ITemperature temperature = player.getCapability(TemperatureProvider.TEMPERATURE_CAP, null);

        // Difference between target temp and player temp.
        float tempDifference = temperature.getTargetTemperature() - temperature.getTemperature();

        // Rate at which the player's temp shall change. This constant might change as well.
        // Changed by config
        // 玩家温度变化的速度。这个常数也可能改变。
        // 通过配置而改变
        float modifier = (float) OverConfig.MECHANICS.temperatureScale;
        float rateOfChange = tempDifference * 0.005f * modifier;

        // Change player's temp.
        // 改变玩家的温度。
        temperature.increase(rateOfChange);
	}
}