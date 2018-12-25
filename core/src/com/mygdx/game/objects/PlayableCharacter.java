package com.mygdx.game.objects;

import com.mygdx.game.configuration.Configuration;

import java.awt.*;

public class PlayableCharacter extends Character {
    private boolean isPlayable;

    public PlayableCharacter(
            Configuration configuration,
            String name,
            String resourceFilename,
            Point startingCellLocation
    ) {
        super(configuration, name, resourceFilename, startingCellLocation);

        this.isPlayable = true;
    }

    public void setPlayable(boolean playable) {
        this.isPlayable = playable;
    }
}
