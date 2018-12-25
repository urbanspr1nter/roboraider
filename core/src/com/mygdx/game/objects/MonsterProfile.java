package com.mygdx.game.objects;

import com.mygdx.game.character.skills.contracts.Learnable;
import com.mygdx.game.objects.contracts.Profilable;

import java.util.List;

public class MonsterProfile implements Profilable {
    private String name;
    private int level;
    private int exp;
    private int currentHp;
    private int maximumHp;
    private int currentMp;
    private int maximumMp;

    private int strength;
    private int magic;
    private int attack;
    private int defense;

    private List<Learnable> skills;

    public MonsterProfile(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public void setCurrentHp(int hp) {
        this.currentHp = hp;
    }

    @Override
    public void setMaximumHp(int hp) {
        this.maximumHp = hp;
    }

    @Override
    public void setCurrentMp(int mp) {
        this.currentMp = mp;
    }

    @Override
    public void setMaximumMp(int mp) {
        this.maximumMp = mp;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public void setMagic(int magic) {
        this.magic = magic;
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getExp() {
        return this.exp;
    }

    @Override
    public int getCurrentHp() {
        return this.currentHp;
    }

    @Override
    public int getMaximumHp() {
        return this.maximumHp;
    }

    @Override
    public int getCurrentMp() {
        return this.currentMp;
    }

    @Override
    public int getMaximumMp() {
        return this.maximumMp;
    }

    @Override
    public int getStrength() {
        return this.strength;
    }

    @Override
    public int getMagic() {
        return this.magic;
    }

    public int getDefense() {
        return this.defense;
    }
}
