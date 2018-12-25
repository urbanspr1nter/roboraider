package com.mygdx.game.menu;

import com.mygdx.game.GameStore;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.character.items.Item;
import com.mygdx.game.menu.enums.MenuInteractionDialogNames;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuItemsInteraction extends MenuInteraction<Item> {
    private static Point CURSOR_START = new Point(4, 427);
    private Map<?, ?> data;
    private List<Point> cursorLocations;

    public MenuItemsInteraction(GameStore store) {
        super(store);
        this.cursorLocations = new ArrayList<>();
    }

    @Override
    public void setData(Map<?, ?> data) {
        this.data = data;
        this.cursorLocations = new ArrayList<>();

        int j = 0;
        // Go one extra to add the extra location.
        for(int i = 0; i <= this.data.size(); i++) {
            if(i % 3 == 0 && i != 0) {
                j++;
            }

            int horizontalDelta = 8 * 18;

            this.cursorLocations.add(new Point(
                    CURSOR_START.x + ((i % 3) * horizontalDelta),
                    CURSOR_START.y - (j * 42)
                )
            );
        }

        Logger.log("# of ITEMS: " + this.data.size());
    }

    @Override
    public String transformData() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for(Object obj : this.data.keySet()) {
            Item item = (Item)(obj);

            if(i % 3 == 0 && i != 0) {
                sb.append(System.lineSeparator());
                sb.append(System.lineSeparator());
            }

            String properName = this.toDisplayString(item.getName());

            sb.append(properName);

            if((Integer)this.data.get(item) < 10) {
                sb.append(" (0" + this.data.get(item) + ")");
            } else {
                sb.append(" (" + this.data.get(item) + ")");
            }

            for(int j = 0; j < 6 + (8 - properName.trim().length()); j++) {
                sb.append(" ");
            }

            i++;
        }

        return sb.toString();
    }

    @Override
    public List<Point> getCursorLocations() {

        // Update the cursor locations if needed.
        if(this.cursorLocations.size() != this.data.size() + 1) {
            this.setData(this.data);
        }

        if(this.cursorLocations.size() == 0) {
            this.cursorLocations.add(CURSOR_START);
        }

        return this.cursorLocations;
    }

    @Override
    public MenuInteractionDialogNames getDialogName() {
        return MenuInteractionDialogNames.Items;
    }
}
