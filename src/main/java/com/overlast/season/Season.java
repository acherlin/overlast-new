package com.overlast.season;

import com.overlast.config.OverConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * Basic properties for the seasons. Lotta switch statements, because enum stuff
 * 季节的基本属性。大量的switch语句，因为枚举的东西
 */
@AllArgsConstructor
@Getter
public enum Season {

    WINTER(OverConfig.SEASONS.winterLength, 0.60f),
    SPRING(OverConfig.SEASONS.springLength, 0.70f),
    SUMMER(OverConfig.SEASONS.summerLength, 0.40f),
    AUTUMN(OverConfig.SEASONS.autumnLength, 0.50f);


    private final int Length;

    private final float precipitationChance;


    // Goto next season
    public Season nextSeason() {
        Season value = Season.values()[this.ordinal() + 1 < Season.values().length ? this.ordinal() + 1 : 0];
        return value;
    }

    // Get previous season
    public Season prevSeason(Season season) {

        Season value = Season.values()[Math.max(this.ordinal() - 1, 0)];
        return value;
    }

    // Methods for setting the seasons apart.
    // Length of the season in Minecraft days

    /* Temperature difference of the season, relation to the original temperature of the biome.
     * With the changing seasons come the changing of the biome temperatures. Mid-spring and early-autumn are the points where the temperatures are normal (vanilla values).
     * The temperatures discretely increase from mid-winter to mid-summer, and decrease for the other half of the time.
     * Since I've made the effective temperature limited to between -0.2 and 1.5:
     *
     * -From mid-spring to mid-summer, the biome temperature will gradually increase by 0.5.
     * -From mid-summer to mid-autumn, it will decrease gradually by 0.8.
     * -From mid-autumn to mid-winter, it will decrease gradually by 0.7.
     * -From mid-winter to mid-spring, it will increase gradually by 1.0.
     *
     * (unevenly)
     *
     * The longer the length of a season, the more gradual the change is.
     * It also has to check if we are in the middle of a season, because in the summer and winter, it changes direction.
     * Remember, this is called at the beginning and middle of each season.
     * 季节的温差，与生物群落的原始温度的关系。
     * 随着季节的变化，生物群落的温度也在变化。仲春和初秋是温度正常的点（原版值）。
     * 从隆冬到仲夏，温度会离散地上升，另外一半时间则会下降。
     *因为我已经把有效温度限制在-0.2和1.5之间。
     *
     * -从仲春到仲夏，生物群落的温度将逐渐增加0.5。
     * -从仲夏到仲秋，它将逐渐减少0.8。
     *从中秋到隆冬，它将逐渐减少0.7。
     *从仲冬到仲春，它将逐渐增加1.0。
     *
     *（不均匀的）。
     *
     *一个季节的长度越长，变化就越渐进。
     * 它还必须检查我们是否处于一个季节的中间，因为在夏天和冬天，它的方向会改变。
     *记住，这是在每个季节的开始和中间调用的。
     */
    public float getTemperatureDifference(int daysElapsed) {

        switch (Season.this) {

            case WINTER:

                // If we're at the second half of the season
                if (daysElapsed > (WINTER.Length / 2)) {

                    return -0.6f;
                } else {

                    return -1.0f;
                }

            case SPRING:

                if (daysElapsed > (SPRING.Length / 2)) {

                    return 0.2f;
                } else {

                    return 0.0f;
                }

            case SUMMER:

                if (daysElapsed > (SUMMER.Length / 2)) {

                    return 0.0f;
                } else {

                    return 0.5f;
                }

            case AUTUMN:

                if (daysElapsed > (AUTUMN.Length / 2)) {

                    return -0.4f;
                } else {

                    return -0.3f;
                }

            default:
                return 0.0f;
        }
    }

    // Chance of getting precipitation on a particular day during a season.  在一个季节中的某一天获得降水的机会。
}