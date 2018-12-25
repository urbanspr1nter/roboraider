package com.mygdx.game.character.skills;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameStore;
import com.mygdx.game.character.skills.enums.FieldType;
import com.mygdx.game.graphics.animation.Animation;
import com.mygdx.game.logging.Logger;

public class Fire extends Skill {
    public Fire(GameStore store, Animation animation, Sound sound, String name, int mpNeeded) {
        super(store, animation, sound, name, mpNeeded);
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.InBattleOnly;
    }

    @Override
    public boolean use() {
        if(this.store.playerData.getPlayerStatistics().getCurrentMp() < this.mpNeeded) {
            return false;
        }

        Logger.log("USED FIRE SKILL!");

        return true;
    }
}
