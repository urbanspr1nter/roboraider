package com.mygdx.game.objects;

import com.mygdx.game.dialog.Graph;

import java.util.List;

public class Dialog {
    private Graph graph;

    public Dialog(Graph dialogGraph) {
        this.graph = dialogGraph;
    }

    public void reset() {
        this.graph.reset();
    }

    public boolean hasNext() {
        return this.graph.currentDialog().size() > 0;
    }

    public List<String> getDialog() {
        return this.graph.currentDialog();
    }

    public String getText() {
        return this.graph.text();
    }

    public void setNext(String s) {
        this.graph.setNext(s);
    }
}
