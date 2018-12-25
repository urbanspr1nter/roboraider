/*
 * EnvironmentSpriteRegistry.java
 *
 * Author: Roger Ngo
 * Copyright: 2018
 */

package com.mygdx.game.map;

import com.mygdx.game.objects.Character;
import com.mygdx.game.sprite.SpriteUnitLocationNode;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EnvironmentSpriteRegistry {
    private List<Character> registry;

    public EnvironmentSpriteRegistry() {
        this.registry = new ArrayList<Character>();
    }

    public List<Character> getObjects() {
        return new ArrayList<Character>(this.registry);
    }

    public void register(Character character) {
        if(!this.registry.contains(character)) {
            this.registry.add(character);
        };
    }

    public void updateOrientation(Character thatSprite, SpriteUnitLocationNode loc) {
        for(Character d : this.registry) {
            if(d.equals(thatSprite)) {
                d.getSpriteData().setOrientation(loc);
            }
        }
    }


    public void updatePosition(Character thatSprite, Point newPosition) {
        for(Character d : this.registry) {
            if(d.equals(thatSprite)) {
                d.getSpriteData().setMapPosition(newPosition);
            }
        }
    }

    public boolean contains(Character thatCharacter) {
        return this.registry.contains(thatCharacter);
    }

    public void remove(String name) {
        Character toRemove = null;
        for(Character c : this.registry) {
            if(c.getSpriteData().getName().equals(name)) {
                toRemove = c;
                break;
            }
        }

        if(toRemove != null) {
            this.registry.remove(toRemove);
        }
    }
}
