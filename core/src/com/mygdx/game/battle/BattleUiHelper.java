/*
 * BattleUiHelper.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * UI helper methods for the BattleState.java class.
 */

package com.mygdx.game.battle;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.GameStore;
import com.mygdx.game.graphics.GraphicsHelper;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BattleUiHelper {
    private static Point ACTION_CURSOR_START = new Point(16, 108);
    private static Point UTILITY_CURSOR_START = new Point(116, 108);
    private static Point ITEM_CURSOR_START = new Point(116, 302);
    private static Point PLAYER_HEADER_CURSOR_START = new Point(164, 432);

    private static int CURSOR_DOWN_MOVEMENT_PIXEL_DELTA = 21;

    private GameStore store;

    public BattleUiHelper(GameStore store) {
        this.store = store;
    }

    public Map<String, Object> getFadeProps(int seconds, Color c) {
        return new GraphicsHelper().getFadeProps(seconds, c);
    }

    public String getAttackDamageMessage(int damage, String targetName) {
        StringBuilder sb = new StringBuilder();

        sb.append("Hit ");
        sb.append(damage);
        sb.append(" HP");
        sb.append(System.lineSeparator());
        sb.append("     on");
        sb.append(System.lineSeparator());
        sb.append(targetName);

        return sb.toString();
    }

    public String getSkillDamageMessage(int damage, String targetName) {
        StringBuilder sb = new StringBuilder();

        sb.append("Cast ");
        sb.append(damage);
        sb.append(" HP");
        sb.append(System.lineSeparator());
        sb.append("     on");
        sb.append(System.lineSeparator());
        sb.append(targetName);

        return sb.toString();
    }

    public void updateCursor(CombatStage stage) {
        if(!this.store.battleInteractionState.getCursorVisiblity()) {
            return;
        }

        Point newPoint;
        if (stage == CombatStage.WaitingAction) {
            newPoint = new Point(
                    ACTION_CURSOR_START.x,
                    ACTION_CURSOR_START.y
                            - (this.store.battleInteractionState.getActionChoice() * CURSOR_DOWN_MOVEMENT_PIXEL_DELTA)
            );
            this.store.battleInteractionState.getCursor().draw(newPoint);
        } else if (stage == CombatStage.ActionAttack) {
            if (this.store.battleInteractionState.getUtilityBoxChoice() == -1) {
                newPoint = new Point(PLAYER_HEADER_CURSOR_START.x, PLAYER_HEADER_CURSOR_START.y);
            } else {
                newPoint = new Point(
                        UTILITY_CURSOR_START.x,
                        UTILITY_CURSOR_START.y
                                - (this.store.battleInteractionState.getUtilityBoxChoice() * CURSOR_DOWN_MOVEMENT_PIXEL_DELTA));
            }
            this.store.battleInteractionState.getCursor().draw(newPoint);
        } else if (stage == CombatStage.ActionSkill) {
            newPoint = new Point(
                    UTILITY_CURSOR_START.x,
                    UTILITY_CURSOR_START.y
                            - (this.store.battleInteractionState.getUtilityBoxChoice() * CURSOR_DOWN_MOVEMENT_PIXEL_DELTA));
            this.store.battleInteractionState.getCursor().draw(newPoint);
        } else if (stage == CombatStage.ActionItem) {
            newPoint = new Point(
                    ITEM_CURSOR_START.x
                            + ((this.store.battleInteractionState.getItemBoxChoice() % 3) * 144),
                    ITEM_CURSOR_START.y
                            - ((this.store.battleInteractionState.getItemBoxChoice() / 3) * CURSOR_DOWN_MOVEMENT_PIXEL_DELTA)
            );
            this.store.battleInteractionState.getCursor().draw(newPoint);
        } else if (stage == CombatStage.ActionSkillTarget) {
            if (this.store.battleInteractionState.getUtilityBoxChoice() == -1) {
                newPoint = new Point(PLAYER_HEADER_CURSOR_START.x, PLAYER_HEADER_CURSOR_START.y);
            } else {
                newPoint = new Point(
                        UTILITY_CURSOR_START.x,
                        UTILITY_CURSOR_START.y
                                - (this.store.battleInteractionState.getUtilityBoxChoice() * CURSOR_DOWN_MOVEMENT_PIXEL_DELTA));
            }

            this.store.battleInteractionState.getCursor().draw(newPoint);
        } else if (stage == CombatStage.ActionItemTarget) {
            if (this.store.battleInteractionState.getUtilityBoxChoice() == -1) {
                newPoint = new Point(PLAYER_HEADER_CURSOR_START.x, PLAYER_HEADER_CURSOR_START.y);
            } else {
                newPoint = new Point(
                        UTILITY_CURSOR_START.x,
                        UTILITY_CURSOR_START.y
                                - (this.store.battleInteractionState.getUtilityBoxChoice() * CURSOR_DOWN_MOVEMENT_PIXEL_DELTA));
            }

            this.store.battleInteractionState.getCursor().draw(newPoint);
        }
    }
}
