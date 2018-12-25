package com.mygdx.game.character.models;

import com.mygdx.game.objects.contracts.Profilable;

public class PlayerProfile implements Profilable {
    private static int MAX_STAT = 255;

    private int exp;
    private int level;
    private int maxHp;
    private int maxMp;
    private int currentHp;
    private int currentMp;
    private int currentExp;
    private int attack;
    private int defense;
    private int magic;
    private int spirit;
    private int speed;
    private int accuracy;
    private int evasion;
    private int stamina;
    private int luck;
    private int vitality;

    public PlayerProfile() {
        this.level = 1;
        this.maxHp = 3;
        this.maxMp = 3;
        this.currentHp = 3;
        this.currentMp = 3;
        this.currentExp = 0;
        this.attack = 1;
        this.defense = 1;
        this.magic = 1;
        this.spirit = 1;
        this.speed = 1;
        this.accuracy = 1;
        this.evasion = 1;
        this.stamina = 1;
        this.luck = 1;
        this.vitality = 1;
    }

    @Override
    public String getName() {
        return "ALEXA";
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
    public void setMaximumHp(int maxHp) {
        this.maxHp = maxHp;
    }

    @Override
    public void setMaximumMp(int maxMp) {
        this.maxMp = maxMp;
    }

    @Override
    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public void setCurrentMp(int currentMp) {
        this.currentMp = currentMp;
    }

    @Override
    public void setStrength(int strength) {

    }

    public void setAttack(int attack) {
        this.attack = attack;

        if(this.attack > MAX_STAT) {
            this.attack = MAX_STAT;
        }
    }

    public void setDefense(int defense) {
        this.defense = defense;

        if(this.defense > MAX_STAT) {
            this.defense = MAX_STAT;
        }
    }

    public void setMagic(int magic) {
        this.magic = magic;

        if(this.magic > MAX_STAT) {
            this.magic = MAX_STAT;
        }
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;

        if(this.spirit > MAX_STAT) {
            this.spirit = MAX_STAT;
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;

        if(this.speed > MAX_STAT) {
            this.speed = MAX_STAT;
        }
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;

        if(this.accuracy > MAX_STAT) {
            this.accuracy = MAX_STAT;
        }
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;

        if(this.evasion > MAX_STAT) {
            this.evasion = MAX_STAT;
        }
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;

        if(this.stamina > MAX_STAT) {
            this.stamina = MAX_STAT;
        }
    }

    public void setLuck(int luck) {
        this.luck = luck;

        if(this.luck > MAX_STAT) {
            this.luck = MAX_STAT;
        }
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;

        if(this.vitality > MAX_STAT) {
            this.vitality = MAX_STAT;
        }
    }

    public int getLevel() {
        return this.level;
    }

    @Override
    public int getExp() {
        return this.exp;
    }

    @Override
    public int getMaximumHp() {
        return this.maxHp;
    }

    @Override
    public int getMaximumMp() {
        return this.maxMp;
    }

    public int getCurrentHp() {
        return this.currentHp;
    }

    public int getCurrentMp() {
        return this.currentMp;
    }

    public int getCurrentExp() {
        return this.currentExp;
    }

    @Override
    public int getStrength() {
        return 0;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
    }

    public int getMagic() {
        return this.magic;
    }

    public int getSpirit() {
        return this.spirit;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public int getEvasion() {
        return this.evasion;
    }

    public int getStamina() {
        return this.stamina;
    }

    public int getLuck() {
        return this.luck;
    }

    public int getVitality() {
        return this.vitality;
    }

    public String toHealthString() {
        StringBuilder sb = new StringBuilder();

        sb.append("LEVEL: " + this.level + ", ");
        sb.append("HP: " + this.currentHp + "/" + this.maxHp + ", ");
        sb.append("MP: " + this.currentMp + "/" + this.maxMp + ", ");
        sb.append("EXP: " + this.currentExp + ", ");
        sb.append("ATK: " + this.attack + ", ");
        sb.append("DEF: " + this.defense + ", ");
        sb.append("MAG: " + this.magic + ", ");
        sb.append("SPR: " + this.spirit);

        return sb.toString();
    }
}
