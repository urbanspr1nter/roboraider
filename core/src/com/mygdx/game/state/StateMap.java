package com.mygdx.game.state;

import java.util.HashMap;
import java.util.Map;

public class StateMap {
    private Map<String, Object> stateData;

    public StateMap() {
        this.stateData = new HashMap<String, Object>();
    }

    public void set(String key, Object value) {
        this.stateData.put(key, value);
    }

    public Object get(String key) {
        if(this.stateData.containsKey(key)) {
            return this.stateData.get(key);
        } else {
            return null;
        }
    }

    public boolean containsKey(String key) {
        return this.stateData.containsKey(key);
    }
}
