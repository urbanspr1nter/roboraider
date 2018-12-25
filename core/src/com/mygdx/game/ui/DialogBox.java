/*
 * DialogBox.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * DialogBox renderer.
 */

package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.graphics.ColorComponents;
import com.mygdx.game.ui.enums.DialogBoxSize;
import com.mygdx.game.ui.text.TextRenderer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class DialogBox {
    private Configuration configuration;
    private String menuAsset;
    private int dialogWidth;
    private int dialogHeight;
    private int rows;
    private int cols;
    private Color currentColor;

    private Texture menuTexture;
    private List<List<Sprite>> menuSprites;
    private boolean isVisible;
    private SpriteBatch dialogBatch;

    public DialogBox(Configuration configuration, boolean isDebug) {
        this(configuration);

        if(isDebug) {
            this.menuAsset = this.configuration.Ui.Menu.DebugImage;
        } else {
            this.menuAsset = this.configuration.Ui.Menu.Image;
        }

        this.menuTexture = new Texture(Gdx.files.internal(this.menuAsset));
    }

    public DialogBox(Configuration configuration) {
        this.configuration = configuration;
        this.menuAsset = this.configuration.Ui.Menu.Image;
        this.dialogWidth = 32;
        this.dialogHeight = 32;
        this.rows = 3;
        this.cols = 15;

        this.menuTexture = new Texture(Gdx.files.internal(this.menuAsset));
        this.menuSprites = new ArrayList<>(this.rows);

        this.isVisible = false;
        this.dialogBatch = new SpriteBatch();
        this.currentColor = Color.WHITE;
    }

    private void initialize() {
        if(menuSprites.size() == 0) {
            for(int i = 0; i < this.rows; i++) {
                menuSprites.add(new ArrayList<>());
            }
            for(int i = 0; i < this.rows; i++) {
                for(int j = 0; j < this.cols; j++) {
                    Sprite menuSprite = new Sprite(menuTexture);
                    if(i == 0 && j == 0) {
                        menuSprite.setRegion(32, 0, this.dialogWidth, this.dialogHeight);
                    } else if(i == 0 && j == this.cols - 1) {
                        menuSprite.setRegion(64, 0, this.dialogWidth, this.dialogHeight);
                    } else if((i > 0 && i < this.rows - 1) && j == 0) {
                        menuSprite.setRegion(224, 0, this.dialogWidth, this.dialogHeight);
                    } else if(i != this.rows - 1 && j == this.cols - 1) {
                        menuSprite.setRegion(256, 0, this.dialogWidth, this.dialogHeight);
                    } else if(i == this.rows - 1 && j == 0) {
                        menuSprite.setRegion(128, 0, this.dialogWidth, this.dialogHeight);
                    } else if(i == this.rows - 1 && j == this.cols - 1) {
                        menuSprite.setRegion(96, 0, this.dialogWidth, this.dialogHeight);
                    } else if(i == 0) {
                        menuSprite.setRegion(160, 0, this.dialogWidth, this.dialogHeight);
                    } else if(i == this.rows - 1) {
                        menuSprite.setRegion(192, 0, this.dialogWidth, this.dialogHeight);
                    } else {
                        menuSprite.setRegion(0, 0, this.dialogWidth, this.dialogHeight);
                    }
                    menuSprites.get(i).add(menuSprite);
                }
            }
        }
    }

    public void toggle() {
        this.isVisible = !this.isVisible;
    }

    public boolean visible() {
        return isVisible;
    }

    public void setColor(Color color) {
        this.currentColor = color;
    }

    public void display(String text, DialogBoxSize size, Point dialogBoxPosition) {
        if(size == DialogBoxSize.Small) {
            this.rows = 3;
            this.cols = 6;
        } else if(size == DialogBoxSize.Header) {
            this.rows = 3;
            this.cols = 14;
        } else if(size == DialogBoxSize.SmallHeader) {
            this.rows = 3;
            this.cols = 4;
        } else if(size == DialogBoxSize.SmallThin) {
            this.rows = 4;
            this.cols = 3;
        } else if(size == DialogBoxSize.SmallTall) {
            this.rows = 5;
            this.cols = 6;
        } else if(size == DialogBoxSize.Medium) {
            this.rows = 4;
            this.cols = 10;
        } else if(size == DialogBoxSize.Large) {
            this.rows = 4;
            this.cols = 15;
        } else if(size == DialogBoxSize.XLarge) {
            this.rows = 10;
            this.cols = 15;
        } else if(size == DialogBoxSize.Full) {
            this.rows = 14;
            this.cols = 19;
        } else if(size == DialogBoxSize.ThreeQuarters) {
            this.rows = 14;
            this.cols = 14;
        } else if(size == DialogBoxSize.Quarter) {
            this.rows = 14;
            this.cols = 5;
        } else if(size == DialogBoxSize.QuarterHalf) {
            this.rows = 8;
            this.cols = 5;
        } else if(size == DialogBoxSize.QuarterQuarter) {
            this.rows = 3;
            this.cols = 5;
        } else if(size == DialogBoxSize.XSmall) {
            this.rows = 3;
            this.cols = 3;
        }

        initialize();

        if(this.isVisible) {
            this.dialogBatch.begin();
            for(int i = 0; i < this.rows; i++) {
                for(int j = 0; j < this.cols; j++) {
                    this.dialogBatch.draw(
                        this.menuSprites.get(i).get(j),
                        dialogBoxPosition.x + (j * 32),
                        dialogBoxPosition.y + (-i * 32)
                    );
                }
            }
            TextRenderer.get().render(
                    this.dialogBatch,
                    text,
                    new Point(dialogBoxPosition.x + 16, dialogBoxPosition.y + 10),
                    this.currentColor);
            this.dialogBatch.end();
        }
    }
}
