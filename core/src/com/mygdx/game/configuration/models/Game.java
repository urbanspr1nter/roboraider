package com.mygdx.game.configuration.models;

public class Game {
    public int Fps;
    public int LogLevel;
    public boolean Debug;
    public Watermark Watermark;
    public TitleScreen TitleScreen;

    public Game() {
        this.Watermark = new Watermark();
        this.TitleScreen = new TitleScreen();
    }
}