package com.mygdx.game.logging;

import com.badlogic.gdx.graphics.Color;

public class LogMessage {
    private String message;
    private long timestamp;
    private Color color;

    public LogMessage(String message, long timestamp, Color color) {
        this.message = message;
        this.timestamp = timestamp;
        this.color = color;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getMessage() {
        return this.message;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public Color getColor() {
        return this.color;
    }
}