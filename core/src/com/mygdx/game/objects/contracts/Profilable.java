package com.mygdx.game.objects.contracts;

public interface Profilable {

    void setLevel(int level);
    void setExp(int exp);
    void setCurrentHp(int hp);
    void setMaximumHp(int hp);
    void setCurrentMp(int mp);
    void setMaximumMp(int mp);

    // Every thing will have base stats:
    //  Strength, Magic, Attack, Defense
    void setStrength(int strength);
    void setMagic(int magic);
    void setAttack(int attack);
    void setDefense(int defense);

    String getName();
    int getLevel();
    int getExp();

    int getCurrentHp();
    int getMaximumHp();

    int getCurrentMp();
    int getMaximumMp();

    int getStrength();
    int getMagic();
}
