package com.mygdx.game.objects.contracts;

import com.mygdx.game.character.PlayerData;
import com.mygdx.game.character.items.Item;
import com.mygdx.game.character.skills.Skill;

public interface Targetable {
    boolean applyAttack(Targetable source);
    boolean applySkill(Skill s, PlayerData player);
    boolean applyItem(Item i);
    void setDeath(boolean death);
    boolean getDeath();
}
