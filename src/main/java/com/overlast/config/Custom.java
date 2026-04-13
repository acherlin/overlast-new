package com.overlast.config;

import net.minecraftforge.common.config.Config;



@Config.LangKey("config.overlast:custom")
public class Custom {

    @Config.LangKey("config.overlast:custom.enableAutoFoodSan")
    @Config.RequiresWorldRestart
    @Config.Comment("Whether to enable automatic configuration of food Sanity.")
    public boolean enableAutoFoodSan = false;

    @Config.LangKey("config.overlast:custom.foodSanity")
    @Config.Comment({"Eating food altered sanity.(eg: minecraft:id;meta;value)"})
    @Config.RequiresWorldRestart
    public String[] foodSanity= {
            "minecraft:chicken;0;-3",
            "minecraft:beef;0;-5",
            "minecraft:rabbit;0;-3",
            "minecraft:mutton;0;-3",
            "minecraft:porkchop;0;-5",
            "minecraft:fish;0;-3",
            "minecraft:rotten_flesh;0;-10",
            "minecraft:potato;0;-1",
            "minecraft:poisonous_potato;0;-5",
            "overlast:pollute_bowl_herbal;0;-5",
            "minecraft:cooked_chicken;0;3",
            "minecraft:bread;0;1.5",
            "minecraft:cooked_beef;0;5",
            "minecraft:cooked_rabbit;0;3",
            "minecraft:cooked_mutton;0;3",
            "minecraft:cooked_porkchop;0;3",
            "minecraft:baked_potato;0;1.5",
            "minecraft:cooked_fish;0;3",
            "minecraft:pumpkin_pie;0;10",
            "minecraft:cookie;0;1.5",
            "minecraft:rabbit_stew;0;3",
            "minecraft:mushroom_stew;0;1.5",
            "overlast:chocolate_smoothie;0;5",
            "overlast:beef_pickaxe;0;5",
            "overlast:bowl_herbal;0;5",
            "overlast:dumpling;0;5"
    };

    @Config.LangKey("config.overlast:custom.specimenFarm")
    @Config.Comment("Agricultural Specimen Recipe.(eg: minecraft:id;amount;meta)")
    public String[] specimenFarm={
            "overlast:crops;640;0",
            "minecraft:pumpkin;192;0",
    };

    @Config.LangKey("config.overlast:custom.specimenCell")
    @Config.Comment("Cellular Specimen Recipe. (eg: minecraft:id;amount;meta)")
    public String[] specimenCell={
            "srparasites:lurecomponent4;32;0",
            "srparasites:lurecomponent5;32;0",
            "srparasites:lurecomponent6;32;0",
            "srparasites:lurecomponent4;32;0",
            "overlast:boss_chip;8;0"
    };

    @Config.LangKey("config.overlast:custom.specimenInfect")
    @Config.Comment("Infected Specimen Recipe. (eg: minecraft:id;amount;meta)")
    public String[] specimenInfect={
            "srparasites:parasitesapling;16;2",
            "srparasites:parasiterubble;320;2",
            "srparasites:parasitestain;320;0",
            "srparasites:parasitethin;32;0",
            "srparasites:parasitecanister;32;2",
            "srparasites:parasitestain;320;3",
            "srparasites:parasitemouth;32;0",
            "srparasites:parasiterubble;64;1",
            "srparasites:biomeheart;0;8"

    };

    @Config.LangKey("config.overlast:custom.finalSpecimen")
    @Config.Comment("World Purification Core Recipe. (eg: minecraft:id;amount;meta)")
    public String[] finalSpecimen={
            "overlast:specimen_cell;1;0",
            "overlast:specimen_farm;1;0",
            "overlast:specimen_infect;1;0"
    };


    @Config.LangKey("config.overlast:custom.hideUI")
    @Config.Comment("Hide the evolution bar of the specified phase. (eg: -1,0,3)")
    @Config.RequiresWorldRestart
    public int[] hideUI={
    };

    @Config.LangKey("config.overlast:custom.finalBattleDim")
    @Config.Comment("Final Battle Dimension ID. (eg: 0)")
    @Config.RequiresWorldRestart
    public int finalBattleDimensionID=0;

    @Config.LangKey("config.overlast:custom.SRPEnchantType")
    @Config.Comment("SRP Killer applicable item types 0-ALL 1-WEAPON 2-FISHING_ROD 3-BOW (eg: 1)")
    @Config.RequiresWorldRestart
    public int SRPEnchantType=1;

    @Config.LangKey("config.overlast:custom.SRPEnchantRarity")
    @Config.Comment("SRP Killer of the Rarity. 0-COMMON 1-UNCOMMON 2-RARE 3-VERY_RARE(eg: 3)")
    @Config.RequiresWorldRestart
    public int SRPEnchantRarity=3;

    @Config.LangKey("config.overlast:custom.SRPEnchantBaseDamage")
    @Config.Comment("SRP Killer of the Damage. The default damage is 1.25 + 0.75 * level (eg: 0.75f)")
    @Config.RequiresWorldRestart
    public float SRPEnchantBaseDamage=0.75f;

    @Config.LangKey("config.overlast:custom.lowSanityPools")
    @Config.Comment("Low sanity spawn entity pools (eg: minecraft:id;phase;rate)")
    public String[] lowSanityPools={
            "srparasites:movingflesh;1;1"
    };

    @Config.LangKey("config.overlast:custom.lowSanityPotion")
    @Config.Comment("Effects of potions given to low sanity. (eg: minecraft:id;duration;amplifier)")
    public String[] lowSanityPotion={
            "minecraft:speed;100;1",
            "minecraft:invisibility;100;0",
            "minecraft:resistance;100;0",
            "srparasites:coth;100;3"
    };

    @Config.LangKey("config.overlast:custom.lowSanityParasites")
    @Config.Comment("Effects of potions given to low sanity under ParasitesInfection status. (eg: minecraft:id;duration;amplifier;parasites)")
    public String[] lowSanityParasites={
            "minecraft:speed;600;2;0",
            "minecraft:resistance;600;1;0",
            "minecraft:strength;1200;2;0",
            "minecraft:speed;600;3;1",
            "minecraft:resistance;600;3;1",
            "minecraft:strength;1200;3;1",
    };

    @Config.LangKey("config.overlast:custom.sanityDimBlacklist")
    @Config.Comment("Sanity will not change with brightness in the following dimension. (eg: 1,-1)")
    public Integer[] sanityDimBlacklist={
            1,
            -1
    };

    @Config.LangKey("config.overlast:custom.finalBattleRewards")
    @Config.Comment("Final Battle Victory Reward. (eg: 1,-1)")
    public String[] finalBattleRewards={
    };
}