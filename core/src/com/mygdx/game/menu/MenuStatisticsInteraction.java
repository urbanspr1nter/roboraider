package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameStore;
import com.mygdx.game.menu.enums.StatisticsLabel;
import com.mygdx.game.menu.enums.MenuInteractionDialogNames;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuStatisticsInteraction extends MenuInteraction<String> {
    private static Point CURSOR_START = new Point(4, 420);
    private Map<?, ?> data;
    private List<Point> cursorLocations;

    private Texture avatar;

    public MenuStatisticsInteraction(GameStore store) {
        super(store);

        this.cursorLocations = new ArrayList<>();
        this.avatar = new Texture(Gdx.files.internal("alexa_avatar.png"));
    }

    @Override
    public void render() {
        if(!this.displayBox.visible()) {
            this.displayBox.toggle();
        }

        this.displayBox.display(
                this.transformData(),
                DialogBoxSize.ThreeQuarters,
                DialogBoxPositions.ThreeQuarters()
        );

        this.store.spriteBatch.begin();
        this.store.spriteBatch.draw(this.avatar, 32, 316, 128, 128);
        this.store.spriteBatch.end();
    }

    @Override
    public void setData(Map<?, ?> data) { }

    @Override
    public String transformData() {
        StringBuilder sb = new StringBuilder();

        sb.append("                              ");
        sb.append("ALEXA");
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        if(this.store.playerData.getPlayerStatistics().getCurrentHp()
                < (int)Math.ceil(this.store.playerData.getPlayerStatistics().getMaximumHp() * 0.15)) {
            sb.append("                              ");
            sb.append("!! LOW HP !!");
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
        }
        sb.append("                                  ");
        sb.append("LEVEL: ");
        sb.append(this.store.playerData.getPlayerStatistics().getLevel());
        sb.append(System.lineSeparator());
        sb.append("                                  ");
        sb.append("HP: ");
        sb.append(this.store.playerData.getPlayerStatistics().getCurrentHp() + "/" + this.store.playerData.getPlayerStatistics().getMaximumHp());
        sb.append(System.lineSeparator());
        sb.append("                                  ");
        sb.append("MP: ");
        sb.append(this.store.playerData.getPlayerStatistics().getCurrentMp() + "/" + this.store.playerData.getPlayerStatistics().getMaximumMp());


        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("----------------------------------------------------");
        sb.append(System.lineSeparator());
        sb.append(this.toDisplayString(StatisticsLabel.Attack.toString()));
        sb.append("    ");
        sb.append(this.store.playerData.getPlayerStatistics().getAttack());
        sb.append(System.lineSeparator());
        sb.append(this.toDisplayString(StatisticsLabel.Defense.toString()));
        sb.append("    ");
        sb.append(this.store.playerData.getPlayerStatistics().getDefense());
        sb.append(System.lineSeparator());
        sb.append(this.toDisplayString(StatisticsLabel.Magic.toString()));
        sb.append("    ");
        sb.append(this.store.playerData.getPlayerStatistics().getMagic());
        sb.append(System.lineSeparator());
        sb.append(this.toDisplayString(StatisticsLabel.Spirit.toString()));
        sb.append("    ");
        sb.append(this.store.playerData.getPlayerStatistics().getSpirit());
        sb.append(System.lineSeparator());
        sb.append(this.toDisplayString(StatisticsLabel.Speed.toString()));
        sb.append("    ");
        sb.append(this.store.playerData.getPlayerStatistics().getSpeed());
        sb.append(System.lineSeparator());
        sb.append(this.toDisplayString(StatisticsLabel.Accuracy.toString()));
        sb.append("    ");
        sb.append(this.store.playerData.getPlayerStatistics().getAccuracy());
        sb.append(System.lineSeparator());
        sb.append(this.toDisplayString(StatisticsLabel.Evasion.toString()));
        sb.append("    ");
        sb.append(this.store.playerData.getPlayerStatistics().getEvasion());
        sb.append(System.lineSeparator());
        sb.append(this.toDisplayString(StatisticsLabel.Stamina.toString()));
        sb.append("    ");
        sb.append(this.store.playerData.getPlayerStatistics().getStamina());
        sb.append(System.lineSeparator());
        sb.append(this.toDisplayString(StatisticsLabel.Luck.toString()));
        sb.append("    ");
        sb.append(this.store.playerData.getPlayerStatistics().getLuck());
        sb.append(System.lineSeparator());
        sb.append(this.toDisplayString(StatisticsLabel.Vitality.toString()));
        sb.append("    ");
        sb.append(this.store.playerData.getPlayerStatistics().getVitality());

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
        return MenuInteractionDialogNames.Statistics;
    }
}
