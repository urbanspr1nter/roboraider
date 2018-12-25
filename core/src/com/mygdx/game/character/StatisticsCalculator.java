/*
 * StatisticsCalculator.java
 *
 * Author: Roger Ngo
 * Copyright: 2018
 *
 * Calculate various character statistics.
 */

package com.mygdx.game.character;

import com.mygdx.game.character.models.PlayerProfile;

public class StatisticsCalculator {
    public static int newHp(PlayerProfile characterStatistics) {
        return (int)(Math.floor(
                1.04 * (characterStatistics.getLevel()) + characterStatistics.getMaximumHp())
                + 0.67 * characterStatistics.getVitality()
                + 0.20 * characterStatistics.getStamina()
        );
    }

    public static int newMp(PlayerProfile characterStatistics) {
        return (int)Math.floor(
                1.005 * characterStatistics.getMaximumMp()
                + 0.67 * characterStatistics.getMagic()
        );
    }

    public static int newAttack(PlayerProfile characterStatistics) {
        return (int) Math.round(characterStatistics.getAttack() * 1.05) + 1;
    }

    public static int newDefense(PlayerProfile characterStatistics) {
        return (int)Math.round(characterStatistics.getDefense() * 1.04) + 1;
    }

    public static int newMagic(PlayerProfile characterStatistics) {
        return (int)Math.round(characterStatistics.getMagic() * 1.03) + 1;
    }

    public static int newSpirit(PlayerProfile characterStatistics) {
        return (int)Math.round(characterStatistics.getSpirit() * 1.04) + 1;
    }

    public static int newSpeed(PlayerProfile characterStatistics) {
        return (int)Math.round(characterStatistics.getSpeed() * 1.04) + 1;
    }

    public static int newAccuracy(PlayerProfile characterStatistics) {
        return (int)Math.round(characterStatistics.getAccuracy() * 1.02) + 1;
    }

    public static int newEvasion(PlayerProfile characterStatistics) {
        return (int)Math.round(characterStatistics.getEvasion() * 1.03) + 1;
    }

    public static int newStamina(PlayerProfile characterStatistics) {
        return (int)Math.round(characterStatistics.getStamina() * 1.03) + 1;
    }

    public static int newLuck(PlayerProfile characterStatistics) {
        return (int)Math.round(characterStatistics.getLuck() * 1.01) + 1;
    }

    public static int newVitality(PlayerProfile characterStatistics) {
        return (int)Math.round(characterStatistics.getVitality() * 1.05) + 1;
    }
}
