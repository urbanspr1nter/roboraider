package com.mygdx.game.configuration.models;

public class Watermark {
    public boolean Display;
    public int Size;
    public Image Image;
    public Text Text;

    public Watermark() {
        this.Image = new Image();
        this.Text = new Text();
    }
}
