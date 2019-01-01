package com.mygdx.game.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameStore;
import com.mygdx.game.audio.UiSounds;
import com.mygdx.game.battle.dependency.InjecteeContainer;
import com.mygdx.game.character.items.Item;
import com.mygdx.game.character.skills.Skill;
import com.mygdx.game.graphics.animation.DamageCounter;
import com.mygdx.game.objects.Monster;
import com.mygdx.game.objects.MonsterLoader;
import com.mygdx.game.objects.contracts.Targetable;
import com.mygdx.game.ui.Cursor;
import com.mygdx.game.util.Timer;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class BattleInteractionState {
    private GameStore store;
    private CombatStage currentStage;
    private InjecteeContainer diContainer;
    private Timer battleTimer;
    private Music currentBgm;
    private Music currentVictoryBgm;
    private Texture currentBackground;
    private int enemyIndex;
    private Cursor cursor;
    private boolean cursorVisible;
    private List<Monster> monsters;
    private DamageCounter counter;
    private Targetable targetted;
    private Skill targettedSkill;
    private Item targettedItem;
    private int actionChoice;
    private Stack<Integer> utilityBoxChoices;
    private int itemBoxChoice;
    private int playerHeaderBoxChoice;
    private int totalExp;

    public BattleInteractionState(GameStore store) {
        this.store = store;
        this.currentStage = CombatStage.WaitingAction;
        this.actionChoice = 0;
        this.cursor = new Cursor(this.store.configuration, this.store.spriteBatch);
        this.monsters = new LinkedList<>();
        this.totalExp = 0;
        this.utilityBoxChoices = new Stack<>();
        this.itemBoxChoice = 0;
        this.playerHeaderBoxChoice = 0;
        this.counter = new DamageCounter(this.store);
        this.battleTimer = new Timer(1000);

        this.diContainer = new InjecteeContainer();
        this.registerObjectsForInjection();

        //this.testInitializeMonsters();
    }

    public void setCurrentBackground(Texture background) {
        this.currentBackground = background;
    }

    public Texture getCurrentBackground() {
        return this.currentBackground;
    }

    public void setEnemies(List<String> enemies) {
        Point start = new Point(32, 150);

        MonsterLoader ml = new MonsterLoader(this.store);

        if(enemies.size() == 4) {
            start = new Point(32, 150);
        } else if(enemies.size() == 3) {
            start = new Point(100, 150);
        } else if(enemies.size() == 2) {
            start = new Point(176, 150);
        } else if(enemies.size() == 1) {
            start = new Point(248, 150);
        }

        int shift = 0;
        for(String enemyFile : enemies) {
            Monster m = ml.load(enemyFile);
            m.setPosition(new Point(start.x + (150 * shift), start.y));

            this.monsters.add(m);

            shift++;
        }
    }

    public void setCursorVisibility(boolean visible) {
        this.cursorVisible = visible;
    }

    public boolean getCursorVisiblity() {
        return this.cursorVisible;
    }

    public InjecteeContainer getServiceContainer() {
        return this.diContainer;
    }

    public void moveToStage(CombatStage stage) {
        this.currentStage = stage;
    }

    public CombatStage currentStage() {
        return this.currentStage;
    }

    public void setEnemyIndex(int enemyIndex) {
        this.enemyIndex = enemyIndex;
    }

    public int getEnemyIndex() {
        return this.enemyIndex;
    }

    public void setTarget(Targetable t) {
        this.targetted = t;
    }

    public Targetable getTargetted() {
        return this.targetted;
    }

    public void setTargettedSkill(Skill s) {
        this.targettedSkill = s;
    }

    public Skill getTargettedSkill() {
        return this.targettedSkill;
    }

    public void setTargettedItem(Item i) {
        this.targettedItem = i;
    }

    public Item getTargettedItem() {
        return this.targettedItem;
    }

    public void setBackgroundMusic(String musicAssetName) {
        this.currentBgm
                = Gdx.audio.newMusic(Gdx.files.internal(this.store.configuration.Assets.Registry.get(musicAssetName).File));
        if(this.currentBgm != null) {
            this.currentBgm.setLooping(true);
            this.currentBgm.setVolume(this.store.configuration.Assets.Registry.get(musicAssetName).Volume);
        }
    }

    public Music getCurrentBackgroundMusic() {
        return this.currentBgm;
    }

    public void setVictoryBackgroundMusic(String musicAssetName) {
        this.currentVictoryBgm
                = Gdx.audio.newMusic(Gdx.files.internal(this.store.configuration.Assets.Registry.get(musicAssetName).File));
        if(this.currentVictoryBgm != null) {
            this.currentVictoryBgm.setLooping(true);
            this.currentVictoryBgm.setVolume(this.store.configuration.Assets.Registry.get(musicAssetName).Volume);
        }
    }

    public Music getCurrentVictoryBgm() {
        return this.currentVictoryBgm;
    }

    private void registerObjectsForInjection() {
        this.diContainer = new InjecteeContainer();
        this.diContainer.register(BattleUiManager.class, new BattleUiManager(this.store));
        this.diContainer.register(BattleUiSounds.class, new BattleUiSounds(this.store));
        this.diContainer.register(BattleUiHelper.class, new BattleUiHelper(this.store));
        this.diContainer.register(UiSounds.class, new UiSounds(this.store));
    }

    private void testInitializeMonsters() {
        // For test...
        Point start = new Point(32, 150);

        MonsterLoader ml = new MonsterLoader(this.store);

        Monster m1 = ml.load("monsters/Machina.json");
        Monster m2 = ml.load("monsters/DemonCat.json");
        Monster m3 = ml.load("monsters/DemonCat.json");
        Monster m4 = ml.load("monsters/DemonCat.json");

        m1.setPosition(new Point(start.x + 72, 120));
        m2.setPosition(new Point(start.x + 150, 150));
        m3.setPosition(new Point(start.x + 300, 150));
        m4.setPosition(new Point(start.x + 450, 150));

        this.monsters.add(m1);
        //this.monsters.add(m2);
        //this.monsters.add(m3);
        //this.monsters.add(m4);
    }

    public Timer getTimer() {
        return this.battleTimer;
    }

    public void setCurrentTotalExp(int value) {
        this.totalExp = value;
    }

    public int getCurrentTotalExp() {
        return this.totalExp;
    }

    public DamageCounter getDamageCounter() {
        return this.counter;
    }

    public List<Monster> getMonsters() {
        return this.monsters;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    public void incrementActionChoice() {
        this.actionChoice++;
    }

    public void decrementActionChoice() {
        this.actionChoice--;
    }

    public int getActionChoice() {
        return this.actionChoice;
    }

    public void setUtilityBoxChoiceToPlayer() {
        if(this.utilityBoxChoices.empty()) {
            return;
        }

        this.utilityBoxChoices.pop();
        this.utilityBoxChoices.push(-1);
    }

    public void resetUtilityBoxChoice() {
        if(this.utilityBoxChoices.empty()) {
            return;
        }

        Integer i = this.utilityBoxChoices.pop();
        this.utilityBoxChoices.push(0);
    }

    public void incrementUtilityBoxChoice() {
        if(this.utilityBoxChoices.empty()) {
            return;
        }

        Integer i = this.utilityBoxChoices.pop();
        this.utilityBoxChoices.push(i + 1);
    }

    public void decrementUtilityBoxChoice() {
        if(this.utilityBoxChoices.empty()) {
            return;
        }

        Integer i = this.utilityBoxChoices.pop();
        this.utilityBoxChoices.push(i - 1);
    }

    public int getUtilityBoxChoice() {
        return this.utilityBoxChoices.peek();
    }

    public void setUtilityBoxChoiceToFirst() {
        this.utilityBoxChoices.push(0);
    }

    public void popUtilityBoxChoice() {
        if(this.utilityBoxChoices.empty()) {
            return;
        }
        this.utilityBoxChoices.pop();
    }

    public void incrementPlayerHeaderBoxChoice() {
        this.playerHeaderBoxChoice++;
    }

    public void decrementPlayerHeaderBoxChoice() {
        this.playerHeaderBoxChoice--;
    }

    public int getPlayerHeaderBoxChoice() {
        return this.playerHeaderBoxChoice;
    }

    public void incrementItemBoxChoice() {
        this.itemBoxChoice++;
    }

    public void incrementItemBoxChoice(int amount) {
        this.itemBoxChoice += amount;
    }

    public void decrementItemBoxChoice() {
        this.itemBoxChoice--;
    }

    public void decrementItemBoxChoice(int amount) {
        this.itemBoxChoice -= amount;
    }

    public int getItemBoxChoice() {
        return this.itemBoxChoice;
    }

    public void resetItemBoxChoice() {
        this.itemBoxChoice = 0;
    }
}
