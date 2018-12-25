package com.mygdx.game.menu;

import com.mygdx.game.GameStore;
import com.mygdx.game.character.enums.EquipmentAreas;
import com.mygdx.game.character.items.contracts.Equippable;
import com.mygdx.game.character.items.Item;
import com.mygdx.game.menu.MenuInteraction;
import com.mygdx.game.menu.enums.MenuInteractionDialogNames;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuEquipmentInteraction extends MenuInteraction<Equippable> {
    private static Point CURSOR_START = new Point(4, 427);

    private Map<?, ?> data; // K=>EquipmentAreas, V=>Item
    private List<Point> cursorLocations;

    public MenuEquipmentInteraction(GameStore store) {
        super(store);
        this.cursorLocations = new ArrayList<>();
    }

    @Override
    public void setData(Map<?, ?> data) {
        this.data = data;

        for(int i = 0; i < this.data.size(); i++) {
            this.cursorLocations.add(new Point(
                    CURSOR_START.x,
                    CURSOR_START.y - (i * 21)
                )
            );
        }
    }

    @Override
    public String transformData() {
        StringBuilder sb = new StringBuilder();

        for(Object obj : this.data.keySet()) {
            EquipmentAreas area = (EquipmentAreas)(obj);

            sb.append(area.toString() + ": ");

            if(this.data.get(area) != null) {
                Item item = (Item)(this.data.get(area));
                String properName = this.toDisplayString(item.getName());
                sb.append(properName);

            }

            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public List<Point> getCursorLocations() {
        if(this.cursorLocations.size() == 0) {
            this.cursorLocations.add(CURSOR_START);
        }

        return this.cursorLocations;
    }

    @Override
    public MenuInteractionDialogNames getDialogName() {
        return MenuInteractionDialogNames.Equipment;
    }
}
