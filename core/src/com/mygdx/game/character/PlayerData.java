package com.mygdx.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameStore;
import com.mygdx.game.character.enums.EquipmentAreas;
import com.mygdx.game.character.items.*;
import com.mygdx.game.character.items.enums.Name;
import com.mygdx.game.character.models.PlayerProfile;
import com.mygdx.game.character.skills.Cure;
import com.mygdx.game.character.skills.Fire;
import com.mygdx.game.character.skills.Skill;
import com.mygdx.game.graphics.animation.Flame;
import com.mygdx.game.graphics.animation.Slash;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.objects.Monster;
import com.mygdx.game.objects.contracts.Targetable;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class PlayerData implements Targetable {
    private static int MAX_GOLD = 999999;

    private GameStore store;
    private Map<Item, Integer> inventory;
    private Map<EquipmentAreas, Item> equipment;
    private Map<Integer, Skill> skills;

    private int gold;

    private PlayerProfile stats;

    public PlayerData(GameStore store) {
        this.store = store;

        this.inventory = new LinkedHashMap<>();

        this.equipment = new LinkedHashMap<>();
        this.initializeEquipment();

        this.skills = new LinkedHashMap<>();
        this.initializeSkills();

        this.stats = new PlayerProfile();
        this.gold = 0;

        // For debugging purposes...
        this.testQuickInitialize();
    }

    @Override
    public boolean applyAttack(Targetable source) {
        Monster m = (Monster)source;

        int playerAttack = this.getPlayerStatistics().getAttack();
        int monsterDefense = m.getProfile().getDefense();

        Random rand = new Random(new Date().getTime());

        int damage = Math.abs((int)Math.round(playerAttack - (monsterDefense * 0.67)
                - (0.5 * rand.nextInt(13))));
        this.getPlayerStatistics().setCurrentHp(this.getPlayerStatistics().getCurrentHp() - damage);
        Logger.log("DEALT " + damage + "DMG! Current HP " + this.getPlayerStatistics().getCurrentHp());
        return false;
    }

    @Override
    public boolean applySkill(Skill s, PlayerData player) {
        this.getPlayerStatistics().setCurrentHp(this.getPlayerStatistics().getCurrentHp() - 5);
        Logger.log("DEALT " + 5 + "DMG! Current HP " + this.getPlayerStatistics().getCurrentHp());
        return false;
    }

    @Override
    public boolean applyItem(Item i) {
        return false;
    }

    @Override
    public void setDeath(boolean death) {
        // set death
    }

    @Override
    public boolean getDeath() {
        return false;
    }

    public void setGold(int gold) {
        this.gold = gold;

        if(this.gold >= MAX_GOLD) {
            this.gold = MAX_GOLD;
        }
    }

    public PlayerProfile getPlayerStatistics() {
        return this.stats;
    }

    public Map<Integer, Skill> getSkills() {
        return this.skills;
    }

    public void setSkill(Integer slot, Skill skill) {
        if(slot >= this.skills.keySet().size()) {
            return;
        }
        this.skills.put(slot, skill);
    }

    public Skill getSkill(Integer slot) {
        return this.skills.get(slot);
    }

    public Map<Item, Integer> getInventory() {
        return this.inventory;
    }

    public Map<EquipmentAreas, Item> getEquipped() {
        return this.equipment;
    }

    public void removeEquipped(EquipmentAreas area) {
        Item oldEquipped = this.equipment.get(area);
        if(this.inventory.containsKey(oldEquipped)) {
            this.inventory.put(oldEquipped, this.inventory.get(oldEquipped) + 1);
        } else {
            this.inventory.put(oldEquipped, 1);
        }
    }

    public void equipItemOnCharacter(EquipmentAreas area, Item equipment) {
        this.equipment.put(area, equipment);
    }

    public Item getEquippedItemOnCharacter(EquipmentAreas area) {
        if(this.equipment.containsKey(area)) {
            return this.equipment.get(area);
        }

        return null;
    }

    public int getGold() {
        return this.gold;
    }

    public void testQuickInitialize() {
        /// make some fake items
        this.inventory.put(new Potion(store, Name.Potion), 4);
        this.inventory.put(new Ether(store, Name.Ether), 2);
        this.inventory.put(new ClothArmor(store, Name.ClothArmor), 1);
        this.inventory.put(new Helmet(store, Name.Helmet), 1);
        this.inventory.put(new IronSword(store, Name.IronSword), 1);
        this.inventory.put(new LifeRing(store, Name.LifeRing), 1);

        // add some skillz
        Sound flameSound = Gdx.audio
                .newSound(Gdx.files.internal(this.store.configuration.Assets.Registry.get("BattleFlame").File));
        this.skills.put(0, new Cure(this.store, new Flame(this.store), flameSound, "CURE", 2));
        this.skills.put(1, new Fire(this.store, new Flame(this.store), flameSound, "FIRE", 3));

        this.gold = MAX_GOLD;

        for(int i = 0; i < 11; i++) {
            this.levelUp();
        }

        this.getPlayerStatistics().setCurrentHp(this.getPlayerStatistics().getMaximumHp());
        this.getPlayerStatistics().setCurrentMp(this.getPlayerStatistics().getMaximumMp());
    }

    public void levelUp() {
        if(this.stats.getLevel() >= 99) {
            return;
        }

        this.stats.setLevel(this.stats.getLevel() + 1);
        this.stats.setMaximumHp(StatisticsCalculator.newHp(this.stats));
        this.stats.setMaximumMp(StatisticsCalculator.newMp(this.stats));
        this.stats.setAttack(StatisticsCalculator.newAttack(this.stats));
        this.stats.setDefense(StatisticsCalculator.newDefense(this.stats));
        this.stats.setMagic(StatisticsCalculator.newMagic(this.stats));
        this.stats.setSpirit(StatisticsCalculator.newSpirit(this.stats));
        this.stats.setSpeed(StatisticsCalculator.newSpeed(this.stats));
        this.stats.setAccuracy(StatisticsCalculator.newAccuracy(this.stats));
        this.stats.setEvasion(StatisticsCalculator.newEvasion(this.stats));
        this.stats.setStamina(StatisticsCalculator.newStamina(this.stats));
        this.stats.setLuck(StatisticsCalculator.newLuck(this.stats));
        this.stats.setVitality(StatisticsCalculator.newVitality(this.stats));
    }


    private void initializeEquipment() {
        Item ironSword = new IronSword(this.store, Name.IronSword);
        ((IronSword) ironSword).setAnimation(new Slash(this.store));
        this.equipItemOnCharacter(EquipmentAreas.LeftHand, null);
        this.equipItemOnCharacter(EquipmentAreas.RightHand, ironSword);
        this.equipItemOnCharacter(EquipmentAreas.Accessory, null);
        this.equipItemOnCharacter(EquipmentAreas.Head, null);
        this.equipItemOnCharacter(EquipmentAreas.Chest, null);
        this.equipItemOnCharacter(EquipmentAreas.Arms, null);
        this.equipItemOnCharacter(EquipmentAreas.Legs, null);
    }

    private void initializeSkills() {
        this.skills.put(0, null);
        this.skills.put(1, null);
        this.skills.put(2, null);
        this.skills.put(3, null);
    }
}
