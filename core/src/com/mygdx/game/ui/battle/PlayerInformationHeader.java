package com.mygdx.game.ui.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameStore;
import com.mygdx.game.battle.CombatStage;
import com.mygdx.game.ui.DialogBox;
import com.mygdx.game.ui.DialogBoxPositions;
import com.mygdx.game.ui.enums.DialogBoxSize;

public class PlayerInformationHeader {
    private GameStore store;
    private DialogBox dialogBox;
    private float elapsedTime;
    private int colorIndex;
    private static Color[] colors = {
            Color.DARK_GRAY,
            Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY,
            Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
            Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
            Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
            Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
            Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
            Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE
    };
    private ShapeRenderer shapeRenderer;

    public PlayerInformationHeader(GameStore store) {
        this.store = store;
        this.dialogBox = new DialogBox(this.store.configuration);
        this.colorIndex = 0;
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
    }

    public void render() {
        if(!this.dialogBox.visible()) {
            this.dialogBox.toggle();
        }

        this.elapsedTime += (Gdx.graphics.getDeltaTime() * 1000);
        if(this.elapsedTime >= 33) {
            this.dialogBox.setColor(colors[this.colorIndex %  colors.length]);
            this.colorIndex++;
            if(this.colorIndex == colors.length) {
                this.colorIndex = 0;
            }
            this.elapsedTime = 0;
        }

        this.dialogBox.display(
            this.getPlayerInformation(),
            DialogBoxSize.Header,
            DialogBoxPositions.HeaderTop()
        );

        this.shapeRenderer.begin();
        this.shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        /*
            HP BAR
         */
        this.shapeRenderer.setColor(new Color(0.5f, 0.5f, 0.5f, 0.33f));
        this.shapeRenderer.rect(196, 458, 120, 4);

        float hpRatio = (float)(this.store.playerData.getPlayerStatistics().getCurrentHp()) /
                this.store.playerData.getPlayerStatistics().getMaximumHp();

        if(hpRatio >= 0.5) {
            this.shapeRenderer.setColor(Color.FOREST);
        } else if(hpRatio <= 0.5 && hpRatio >= 0.25) {
            this.shapeRenderer.setColor(Color.GOLDENROD);
        } else {
            this.shapeRenderer.setColor(Color.FIREBRICK);
        }
        this.shapeRenderer.rect(196, 458, Math.round(120 * hpRatio), 4);

        /*
            MP BAR
         */
        this.shapeRenderer.setColor(new Color(0.5f, 0.5f, 0.5f, 0.33f));
        this.shapeRenderer.rect(196, 455, 120, 3);

        float mpRatio = (float)(this.store.playerData.getPlayerStatistics().getCurrentMp()) /
                this.store.playerData.getPlayerStatistics().getMaximumMp();

        this.shapeRenderer.setColor(Color.PURPLE);
        this.shapeRenderer.rect(196, 455, Math.round(120 * mpRatio), 3);

        this.shapeRenderer.end();
    }

    private String getPlayerInformation() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.store.playerData.getPlayerStatistics().getName());
        sb.append(", LEVEL ");
        sb.append(this.store.playerData.getPlayerStatistics().getLevel());
        sb.append(System.lineSeparator());
        sb.append("   HP: ");
        sb.append(this.store.playerData.getPlayerStatistics().getCurrentHp());
        sb.append(" / ");
        sb.append(this.store.playerData.getPlayerStatistics().getMaximumHp());
        sb.append(System.lineSeparator());
        sb.append("   MP: ");
        sb.append(this.store.playerData.getPlayerStatistics().getCurrentMp());
        sb.append(" / ");
        sb.append(this.store.playerData.getPlayerStatistics().getMaximumMp());

        return sb.toString();
    }
}
