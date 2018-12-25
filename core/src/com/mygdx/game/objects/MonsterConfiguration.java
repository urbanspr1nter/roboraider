/*
 * MonsterConfiguration.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * POJO representation of the enemy configuration.
 */
package com.mygdx.game.objects;

import java.awt.*;

class MonsterConfiguration {
    public String spriteAssetName;
    public Point spriteUnit;
    public int spriteFrames;
    public int spriteFrameTime;

    public String spriteAttackAssetName;
    public Point spriteAttackUnit;
    public int spriteAttackAssetFrames;
    public int spriteAttackAssetFrameTime;

    public String attackSoundAssetName;
    public String defeatSoundAssetName;
}
