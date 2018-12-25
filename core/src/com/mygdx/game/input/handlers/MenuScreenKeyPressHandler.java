/*
 * MenuKeyPressHandler.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * The key press handler to handle all menu key presses within
 * the menu state.
 */

package com.mygdx.game.input.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameStore;
import com.mygdx.game.character.skills.contracts.Learnable;
import com.mygdx.game.character.skills.enums.FieldType;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.audio.UiSounds;
import com.mygdx.game.character.items.contracts.Equippable;
import com.mygdx.game.character.items.Item;
import com.mygdx.game.menu.enums.MenuInteractionDialogNames;
import com.mygdx.game.menu.contracts.MenuInteractable;

public class MenuScreenKeyPressHandler extends DefaultKeyPressHandler {
    private UiSounds uiSounds;

    // Stupid way to 'emulate' an 'enum'. We just need it for readability.
    private static class CursorDialogOn {
        static int Items = 0;
        static int Skills = 1;
        static int Equipment = 2;
        static int Statistics = 3;
        static int Configuration = 4;
        static int Save = 5;
    }

    public MenuScreenKeyPressHandler(GameStore store) {
        super(store);
        this.uiSounds = new UiSounds(this.store);
        this.store.menuInteractionState.returnToNavigation(this.store);
    }

    @Override
    public void handleKeyPress(int keyCode) {
        // Handle debug key if needed to toggle debug mode on/off, and handle any "quick quits"
        this.handleDebugKey(keyCode);
        if(this.store.configuration.Game.Debug) {
            this.store.setCurrentKeyCode(keyCode);
            this.handleQuit();
        }

        // If the current state of the menu interaction is showing a item usage confirmation box,
        // don't allow any other key presses to be invoked.
        if(this.store.menuInteractionState.confirmationBox.isValid()) {
            Logger.log("Ignoring key press. Confirmation box for item use is displayed.");
            return;
        }

        // For whatever weird case, and hopefully not, the menu state can lose the cursor. So, as a last-resort,
        // if the menu interaction state doesn't have dialog to place onto, put it on the Navigation box.
        if(!this.store.menuInteractionState.getMenuNavigationStack().hasMenuViewOnStack()) {
            Logger.log("Setting currentDialogCursorOn: " + MenuInteractionDialogNames.Navigation);
            this.handlePopOrReturnToNavigation();
        }

        // Helper methods to handle key presses on a per-dialog basis. We need to handle the keys independently as
        // each key performs a different function in a specific dialog view. For example, an Equipment dialog view
        // doesn't care about LEFT/RIGHT key presses, while Item dialog view depends on UP, DOWN, LEFT and RIGHT!
        MenuInteractable topOfStack = this.store.menuInteractionState.getMenuNavigationStack().peekMenuViewOnStack();

        if(topOfStack.getDialogName() == MenuInteractionDialogNames.Navigation) {
            Logger.log("Handle a Navigation key press.");
            this.handleNavigationKeyPress(keyCode);
        } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Items) {
            Logger.log("Handle a Items key press.");
            this.handleItemsKeyPress(keyCode);
        } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Skills) {
            Logger.log("Handle a Skills key press.");
            this.handleSkillsKeyPress(keyCode);
        } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Equipment) {
            Logger.log("Handle a Equipment key press.");
            this.handleEquipmentKeyPress(keyCode);
        } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Statistics) {
            Logger.log("Handle a Statistics key press... Pass through as Navigation.");
            this.handleNavigationKeyPress(keyCode);
        } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Configuration) {
            Logger.log("Handle a Configuration key press.");
            this.handleConfigurationKeyPress(keyCode);
        } else if(topOfStack.getDialogName() == MenuInteractionDialogNames.Save) {
            Logger.log("Handle a Save key press.");
            this.handleSaveKeyPress(keyCode);
        }
    }

    private void handleNavigationKeyPress(int keyCode) {
        /*
            Valid navigation KEY presses:
                UP -> Moves the cursor up through the list of items within the navigation box.
                DOWN -> Moves the cursor down through the list of items within the navigation box.
                SHIFT-LEFT -> Exits the menu state, and brings the player back to the previous state.
                SPACE -> Confirms the choice made in the navigation box. Pushes a new instance of a MenuInteraction
                         view into the navigation stack.
         */

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.uiSounds.playCursor();
            this.store.menuInteractionState.decrementNavigationCursorChoice();
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.uiSounds.playCursor();
            this.store.menuInteractionState.incrementNavigationCursorChoice();
        } else if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            /*
                Exit the menu, and set the state back to the previous state held within the state machine.
             */
            this.uiSounds.playCancel();
            this.store.stateMachine.exitState();
            this.store.stateMachine.setState(
                this.store.stateMachine.getPreviousStateKey(),
                this.store.stateMachine.getPreviousState()
            );
        } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.uiSounds.playCursorConfirm();
            this.store.menuInteractionState.setChoiceMade(!this.store.menuInteractionState.getChoiceMade());

            if(this.store.menuInteractionState.getCurrentNavigationCursorChoice() == CursorDialogOn.Items) {
                Logger.log("Choice made from navigation: Items");
                this.store.menuInteractionState.setEquipMode(false);
                this.store.menuInteractionState.getMenuNavigationStack().pushViewToNavigationStack(MenuInteractionDialogNames.Items);
            } else if(this.store.menuInteractionState.getCurrentNavigationCursorChoice() == CursorDialogOn.Skills) {
                Logger.log("Choice made from navigation: Skills");
                this.store.menuInteractionState.getMenuNavigationStack().pushViewToNavigationStack(MenuInteractionDialogNames.Skills);
            } else if(this.store.menuInteractionState.getCurrentNavigationCursorChoice() == CursorDialogOn.Equipment) {
                Logger.log("Choice made from navigation: Equipment");
                this.store.menuInteractionState.setEquipMode(true);
                this.store.menuInteractionState.getMenuNavigationStack().pushViewToNavigationStack(MenuInteractionDialogNames.Equipment);
            } else if(this.store.menuInteractionState.getCurrentNavigationCursorChoice() == CursorDialogOn.Statistics) {
                Logger.log("Choice made from navigation: Statistics");
                this.store.menuInteractionState.getMenuNavigationStack().pushViewToNavigationStack(MenuInteractionDialogNames.Statistics);
            } else if(this.store.menuInteractionState.getCurrentNavigationCursorChoice() == CursorDialogOn.Configuration) {
                Logger.log("Choice made from navigation: Configuration");
                this.store.menuInteractionState.getMenuNavigationStack().pushViewToNavigationStack(MenuInteractionDialogNames.Configuration);
            } else if(this.store.menuInteractionState.getCurrentNavigationCursorChoice() == CursorDialogOn.Save) {
                Logger.log("Choice made from navigation: Save");
                this.store.menuInteractionState.getMenuNavigationStack().pushViewToNavigationStack(MenuInteractionDialogNames.Save);
            }
        }
    }

    private void handleItemsKeyPress(int keyCode) {
        int rowSize = 3;
        int inventorySize = this.store.playerData.getInventory().size();

        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.handlePopOrReturnToNavigation();
        } else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(this.store.playerData.getInventory().size() == 0
                    || this.store.menuInteractionState.getCurrentItemChoice() < rowSize) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.menuInteractionState.decrementCurrentItemChoice(rowSize);
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(this.store.playerData.getInventory().size() == 0
                    || this.store.menuInteractionState.getCurrentItemChoice() + rowSize >= inventorySize) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.menuInteractionState.incrementCurrentItemChoice(rowSize);
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            int itemChoice = this.store.menuInteractionState.getCurrentItemChoice();

            if(this.store.playerData.getInventory().size() == 0 || itemChoice > inventorySize - 1) {
                this.uiSounds.playError();
            } else if(itemChoice + 1 >= inventorySize) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.menuInteractionState.incrementCurrentItemChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if(this.store.playerData.getInventory().size() == 0
                    || this.store.menuInteractionState.getCurrentItemChoice() == 0) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.menuInteractionState.decrementCurrentItemChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            int itemChoice = this.store.menuInteractionState.getCurrentItemChoice();

            if(inventorySize == 0 || itemChoice > inventorySize - 1) {
                this.uiSounds.playError();
            } else {
                this.useSelectedItem(itemChoice);
            }
        }
    }

    private void handleEquipmentKeyPress(int keyCode) {
        int numberOfEquippableAreas = this.store.playerData.getEquipped().keySet().size();

        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.uiSounds.playCancel();
            this.store.menuInteractionState.setEquipMode(false);
            this.handlePopOrReturnToNavigation();
        } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.uiSounds.playCursorConfirm();
            this.store.menuInteractionState.getMenuNavigationStack().pushViewToNavigationStack(MenuInteractionDialogNames.Items);
        } else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(this.store.menuInteractionState.getCurrentEquipmentAreaChoice() > 0) {
                this.uiSounds.playCursor();
                this.store.menuInteractionState.decrementCurrentEquipmentAreaChoice();
            } else {
                this.uiSounds.playError();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(this.store.menuInteractionState.getCurrentEquipmentAreaChoice() < numberOfEquippableAreas - 1) {
                this.uiSounds.playCursor();
                this.store.menuInteractionState.incrementCurrentEquipmentAreaChoice();
            } else {
                this.uiSounds.playError();
            }
        }
    }

    private void handleSkillsKeyPress(int keyCode) {
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.handlePopOrReturnToNavigation();
        } else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(this.store.menuInteractionState.getCurrentSkillChoice() <= 0) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.menuInteractionState.decrementCurrentSkillChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(this.store.menuInteractionState.getCurrentSkillChoice()
                    >= this.store.playerData.getSkills().keySet().size() - 1) {
                this.uiSounds.playError();
            } else {
                this.uiSounds.playCursor();
                this.store.menuInteractionState.incrementCurrentSkillChoice();
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            int choice = this.store.menuInteractionState.getCurrentSkillChoice();
            Learnable skill = this.store.playerData.getSkill(choice);
            if(skill == null) {
                this.uiSounds.playError();
            } else {
                FieldType skillFieldType = skill.getFieldType();
                if((skillFieldType == FieldType.OutBattleOnly || skillFieldType == FieldType.InAndOutBattle) && skill.use()) {
                    this.uiSounds.playCursorConfirm();
                    this.store.menuInteractionState.confirmationBox.setMessageAndReset(skill.getName() + " used!");
                } else {
                    this.uiSounds.playError();
                    this.store.menuInteractionState.confirmationBox.setMessageAndReset("Can't use skill!");
                }
            }
        }
    }

    private void handleConfigurationKeyPress(int keyCode) {
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.handlePopOrReturnToNavigation();
        }
    }

    private void handleSaveKeyPress(int keyCode) {
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            this.handlePopOrReturnToNavigation();
        }
    }

    private void handlePopOrReturnToNavigation() {
        // Generic handler for the SHIFT-lEFT key for most of the navigation dialog boxes.
        // Play the cancel sound, check if there is currently something on the stack, pop, and
        // hope that whatever is left on top is rendered next. :) If not, then just point back
        // to the navigation box and set the dialog to the default statistics screen.
        this.uiSounds.playCancel();

        if(this.store.menuInteractionState.getMenuNavigationStack().hasMenuViewOnStack()) {
            this.store.menuInteractionState.getMenuNavigationStack().popMenuViewFromStack();
        } else {
            this.store.menuInteractionState.returnToNavigation(this.store);
        }
    }

    private void useSelectedItem(int selectedItem) {
        Object[] itemArr = this.store.playerData.getInventory().keySet().toArray();
        Item i = (Item)itemArr[selectedItem];

        // Is the item and item which can be used, or is the item an equip-able one onto the body?
        // Only equip if we are in equip mode, which means that the underlying dialog within this current one
        // is the equipment dialog screen.
        if(this.store.menuInteractionState.getEquipMode()
                && i instanceof Equippable
                && ((Equippable) i).equippableArea() == this.store.menuInteractionState.getCurrentEquipmentAreaToModify()) {
            if(i.use()) {
                this.store.menuInteractionState.confirmationBox.setMessageAndReset(i.getName() + " equipped!");
                this.store.playerData.equipItemOnCharacter(this.store.menuInteractionState.getCurrentEquipmentAreaToModify(), i);

                if(this.store.playerData.getInventory().get(i) - 1 <= 0) {
                    this.store.playerData.getInventory().remove(i);
                } else {
                    this.store.playerData.getInventory().put(i, this.store.playerData.getInventory().get(i) - 1);
                }

                Logger.log("EQUIPPED "
                        + i.getName() + " to "
                        + this.store.menuInteractionState.getCurrentEquipmentAreaToModify()
                );
            } else {
                this.store.menuInteractionState.confirmationBox.setMessageAndReset("Can't equip.");
            }
        } else if(!this.store.menuInteractionState.getEquipMode() && !(i instanceof Equippable) && i.use()) {
            this.store.menuInteractionState.confirmationBox.setMessageAndReset(i.getName() + " used!");
            if(this.store.playerData.getInventory().get(i) - 1 <= 0) {
                this.store.playerData.getInventory().remove(i);
            } else {
                this.store.playerData.getInventory().put(i, this.store.playerData.getInventory().get(i) - 1);
            }
            Logger.log("USED item: " + this.store.menuInteractionState.getCurrentItemChoice() + ", " + i.getName());
        } else {
            this.store.menuInteractionState.confirmationBox.setMessageAndReset("Can't use it here.");
            Logger.log("Can't use equipment as an item: " + i.getName());
        }
    }

}
