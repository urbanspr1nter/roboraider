package com.mygdx.game.configuration.models;

import java.util.ArrayList;
import java.util.List;

public class TitleScreen {
    public String Image;
    public String Music;
    public List<String> Options;

    public TitleScreen() {
        this.Options = new ArrayList<>();
    }
}
