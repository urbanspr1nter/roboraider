package com.mygdx.game.menu;

import com.mygdx.game.GameStore;
import com.mygdx.game.character.skills.contracts.Learnable;
import com.mygdx.game.menu.enums.MenuInteractionDialogNames;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuSkillsInteraction extends MenuInteraction<String>  {
    private static Point CURSOR_START = new Point(4, 427);
    private Map<?, ?> data;
    private List<Point> cursorLocations;

    public MenuSkillsInteraction(GameStore store) {
        super(store);
        this.cursorLocations = new ArrayList<>();
    }

    @Override
    public void setData(Map<?, ?> data) {
        this.data = data;

        for(Object s : this.data.keySet()) {
            Integer slotNumber = (Integer)s;
            this.cursorLocations.add(new Point(CURSOR_START.x, CURSOR_START.y - (slotNumber * 21)));
        }
    }

    @Override
    public String transformData() {
        StringBuilder sb = new StringBuilder();

        for(Object s : data.keySet()) {
            Integer slotNumber = (Integer)s;
            Learnable skill = (Learnable)(this.data.get(slotNumber));

            if(skill == null) {
                sb.append("--------");
            } else {
                sb.append(skill.getName() + " (" + skill.getMpNeeded() + ")");
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public List<Point> getCursorLocations() {
        return this.cursorLocations;
    }

    @Override
    public MenuInteractionDialogNames getDialogName() {
        return MenuInteractionDialogNames.Skills;
    }
}
