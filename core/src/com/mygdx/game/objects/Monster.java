package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.GameStore;
import com.mygdx.game.battle.enums.CombatStage;
import com.mygdx.game.character.PlayerData;
import com.mygdx.game.character.items.Item;
import com.mygdx.game.character.skills.Skill;
import com.mygdx.game.logging.Logger;
import com.mygdx.game.objects.contracts.Escapable;
import com.mygdx.game.objects.contracts.Targetable;

import java.awt.*;
import java.util.Date;
import java.util.Random;

public class Monster implements Targetable, Escapable {
    private GameStore store;
    private Texture graphic;
    private Texture graphicAttack;
    private Point position;
    private float elapsedTime;
    private int frame;
    private boolean death;
    private boolean isBoss;
    private int spriteFrames;
    private int attackFrames;
    private int spriteFrameTime;
    private int attackFrameTime;
    private String attackSoundAssetName;
    private String defeatSoundAssetName;
    private Sound attackSound;
    private Sound defeatSound;
    private Point spriteUnit;
    private Point spriteAttackUnit;
    private MonsterProfile profile;
    private Random rand;

    protected Monster(GameStore store, Point position, MonsterConfiguration monsterConfiguration, MonsterProfile profile) {
        this.store = store;
        this.attackSoundAssetName = monsterConfiguration.attackSoundAssetName;
        this.defeatSoundAssetName = monsterConfiguration.defeatSoundAssetName;
        this.attackSound =
            Gdx.audio.newSound(
                Gdx.files.internal(
                    this.store.configuration.Assets.Registry.get(this.attackSoundAssetName).File
                )
            );
        this.defeatSound =
            Gdx.audio.newSound(
                Gdx.files.internal(
                    this.store.configuration.Assets.Registry.get(this.defeatSoundAssetName).File
                )
            );

        this.graphic =
            new Texture(
                Gdx.files.internal(
                    this.store.configuration.Assets.Registry.get(monsterConfiguration.spriteAssetName).File
                )
            );
        this.graphicAttack =
            new Texture(
                Gdx.files.internal(
                    this.store.configuration.Assets.Registry.get(monsterConfiguration.spriteAttackAssetName).File
                )
            );
        this.spriteFrames = monsterConfiguration.spriteFrames;
        this.attackFrames = monsterConfiguration.spriteAttackAssetFrames;
        this.spriteFrameTime = monsterConfiguration.spriteFrameTime;
        this.attackFrameTime = monsterConfiguration.spriteAttackAssetFrameTime;
        this.spriteUnit = monsterConfiguration.spriteUnit;
        this.spriteAttackUnit = monsterConfiguration.spriteAttackUnit;

        this.position = position;
        this.profile = profile;
        this.death = false;
        this.elapsedTime = 0;
        this.frame = 0;
        this.rand = new Random(new Date().getTime());
    }

    public void playAttackSound() {
        float volume = this.store.configuration.Assets.Registry.get(this.attackSoundAssetName).Volume;

        this.attackSound.play(volume);
    }

    public void playDefeatSound() {
        float volume = this.store.configuration.Assets.Registry.get(this.defeatSoundAssetName).Volume;

        this.defeatSound.play(volume);
    }

    public int getDamage(PlayerData player) {
        int playerAttack = player.getPlayerStatistics().getAttack();
        int monsterDefense = this.getProfile().getDefense();

        for(int i = 0; i < 3; i++) {
            this.rand.setSeed(new Date().getTime());
        }

        return Math.abs((int)Math.round(playerAttack - (monsterDefense * 0.67)
                - (0.5 * this.rand.nextInt(9))));
    }

    public int getSkillDamage(PlayerData player) {
        int playerMagic = player.getPlayerStatistics().getMagic();
        int monsterDefense = this.getProfile().getDefense();

        for(int i = 0; i < 3; i++) {
            this.rand.setSeed(new Date().getTime());
        }

        return (int)Math.round(playerMagic - (monsterDefense * 0.67)
                - (0.5 * this.rand.nextInt(9)));
    }

    @Override
    public boolean isBoss() {
        return this.isBoss;
    }

    public void setBoss(boolean isBoss) {
        this.isBoss = isBoss;
    }

    private void setDeathIfNeeded() {
        if(this.profile.getCurrentHp() <= 0) {
            this.profile.setCurrentHp(0);
            this.setDeath(true);
        }
    }

    @Override
    public boolean applyAttack(Targetable source) {
        PlayerData player = (PlayerData)source;
        this.profile.setCurrentHp(this.profile.getCurrentHp() - this.getDamage(player));
        this.setDeathIfNeeded();

        Logger.log("DEALT " + this.getDamage(player) + "DMG! Current HP " + this.profile.getCurrentHp());

        return false;
    }

    @Override
    public boolean applySkill(Skill s, PlayerData player) {
        this.profile.setCurrentHp(this.profile.getCurrentHp() - this.getSkillDamage(player));
        this.setDeathIfNeeded();

        Logger.log("DEALT " + 5 + "DMG! Current HP " + this.profile.getCurrentHp());

        return false;
    }

    @Override
    public boolean applyItem(Item i) {
        return false;
    }

    @Override
    public void setDeath(boolean death) {
        this.death = death;
    }

    @Override
    public boolean getDeath() {
        return this.death;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return this.position;
    }

    public Point getSize() {
        return this.spriteUnit;
    }

    public MonsterProfile getProfile() {
        return this.profile;
    }

    public boolean isRemovalRenderComplete() {
        return this.frame == this.spriteFrames;
    }

    public void render(CombatStage stage, boolean attacking) {
        if(stage == CombatStage.EnemyAttack && attacking) {
            this.elapsedTime += (Gdx.graphics.getDeltaTime() * 1000);
            if(this.elapsedTime >= this.attackFrameTime) {
                this.elapsedTime = 0;
                this.frame++;
            }

            this.store.spriteBatch.begin();
            this.store.spriteBatch.draw(
                new TextureRegion(
                    this.graphicAttack,
                    (this.spriteAttackUnit.x * (this.frame % this.attackFrames)),
                    0,
                    this.spriteAttackUnit.x,
                    this.spriteAttackUnit.y
                ),
                this.position.x,
                this.position.y
            );
            this.store.spriteBatch.end();

            this.frame = 0;
        } else {
            if(this.frame == this.spriteFrames) {
                return;
            }

            if(this.death && stage == CombatStage.WaitingAction) {
                this.elapsedTime += (Gdx.graphics.getDeltaTime() * 1000);
                if(this.elapsedTime >= this.spriteFrameTime) {
                    this.elapsedTime = 0;
                    this.frame++;
                }
            }

            this.store.spriteBatch.begin();
            this.store.spriteBatch.draw(
                    new TextureRegion(
                            this.graphic, (this.spriteUnit.x * this.frame),
                            0,
                            this.spriteUnit.x,
                            this.spriteUnit.y
                    ),
                    this.position.x,
                    this.position.y
            );
            this.store.spriteBatch.end();
        }
    }

    @Override
    public String toString() {
        return this.profile.getName();
    }

}
