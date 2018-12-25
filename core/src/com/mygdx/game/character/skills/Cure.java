package com.mygdx.game.character.skills;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameStore;
import com.mygdx.game.character.skills.enums.FieldType;
import com.mygdx.game.graphics.animation.Animation;

public class Cure extends Skill {
    public Cure(GameStore store, Animation animation, Sound sound, String name, int mpNeeded) {
        super(store, animation, sound, name, mpNeeded);
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.InAndOutBattle;
    }

    @Override
    public boolean use() {
        if(this.store.playerData.getPlayerStatistics().getCurrentMp() < this.mpNeeded) {
            return false;
        }

        this.store.playerData.getPlayerStatistics().setCurrentHp(this.store.playerData.getPlayerStatistics().getCurrentHp() + 10);

        if(this.store.playerData.getPlayerStatistics().getCurrentHp() > this.store.playerData.getPlayerStatistics().getMaximumHp()) {
            this.store.playerData.getPlayerStatistics().setCurrentHp(this.store.playerData.getPlayerStatistics().getMaximumHp());
        }

        this.store.playerData.getPlayerStatistics().setCurrentMp(this.store.playerData.getPlayerStatistics().getCurrentMp() - this.mpNeeded);
        return true;
    }
}
