package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.GameStore;

import java.awt.*;
import java.io.InputStream;

public class MonsterLoader {
    private GameStore store;

    public MonsterLoader(GameStore store) {
        this.store = store;
    }

    public Monster load(String jsonFile) {
        InputStream data = Gdx.files.internal(jsonFile).read();
        JsonReader reader = new JsonReader();
        JsonValue valueFromFile = reader.parse(data);

        String monsterName = valueFromFile.get("name").asString();
        boolean boss = valueFromFile.get("boss").asBoolean();

        String spriteAssetName = valueFromFile.get("sprite")
                .get("main")
                .get("asset").asString();
        int spriteAssetUnitWidth = valueFromFile.get("sprite")
                .get("main")
                .get("unit")
                .get("width").asInt();
        int spriteAssetUnitHeight = valueFromFile.get("sprite")
                .get("main")
                .get("unit")
                .get("height").asInt();
        int spriteAssetTotalFrames = valueFromFile.get("sprite")
                .get("main")
                .get("frames")
                .asInt();
        int spriteAssetFrameTime = valueFromFile.get("sprite")
                .get("main")
                .get("frameTimeMs").asInt();

        String spriteAttackAssetName = valueFromFile.get("sprite")
                .get("attack")
                .get("asset").asString();
        int spriteAttackAssetUnitWidth = valueFromFile.get("sprite")
                .get("attack")
                .get("unit")
                .get("width").asInt();
        int spriteAttackAssetUnitHeight = valueFromFile.get("sprite")
                .get("attack")
                .get("unit")
                .get("height").asInt();
        int spriteAttackAssetFrames = valueFromFile.get("sprite")
                .get("attack")
                .get("frames").asInt();
        int spriteAttackAssetFrameTime = valueFromFile.get("sprite")
                .get("attack")
                .get("frameTimeMs").asInt();

        String attackSound = valueFromFile.get("sounds").get("attack").asString();
        String defeatSound = valueFromFile.get("sounds").get("defeat").asString();

        MonsterConfiguration monsterConfiguration = new MonsterConfiguration();
        monsterConfiguration.spriteAssetName = spriteAssetName;
        monsterConfiguration.spriteUnit = new Point(spriteAssetUnitWidth, spriteAssetUnitHeight);
        monsterConfiguration.spriteFrames = spriteAssetTotalFrames;
        monsterConfiguration.spriteFrameTime = spriteAssetFrameTime;
        monsterConfiguration.spriteAttackAssetName = spriteAttackAssetName;
        monsterConfiguration.spriteAttackUnit = new Point(spriteAttackAssetUnitWidth, spriteAttackAssetUnitHeight);
        monsterConfiguration.spriteAttackAssetFrames = spriteAttackAssetFrames;
        monsterConfiguration.spriteAttackAssetFrameTime = spriteAttackAssetFrameTime;
        monsterConfiguration.attackSoundAssetName = attackSound;
        monsterConfiguration.defeatSoundAssetName = defeatSound;

        int level = valueFromFile.get("profile").get("level").asInt();
        int experience = valueFromFile.get("profile").get("experience").asInt();
        int currentHp = valueFromFile.get("profile").get("currentHp").asInt();
        int maximumHp = valueFromFile.get("profile").get("maximumHp").asInt();
        int currentMp = valueFromFile.get("profile").get("currentMp").asInt();
        int maximumMp = valueFromFile.get("profile").get("maximumMp").asInt();
        int strength = valueFromFile.get("profile").get("strength").asInt();
        int magic = valueFromFile.get("profile").get("magic").asInt();
        int attack = valueFromFile.get("profile").get("attack").asInt();
        int defense = valueFromFile.get("profile").get("defense").asInt();

        MonsterProfile profile = new MonsterProfile(monsterName);
        profile.setLevel(level);
        profile.setExp(experience);
        profile.setCurrentHp(currentHp);
        profile.setMaximumHp(maximumHp);
        profile.setCurrentMp(currentMp);
        profile.setMaximumMp(maximumMp);
        profile.setStrength(strength);
        profile.setMagic(magic);
        profile.setAttack(attack);
        profile.setDefense(defense);


        Monster m = new Monster(
                this.store,
                new Point(32, 150),
                monsterConfiguration,
                profile
        );

        m.setBoss(boss);

        return m;
    }
}
