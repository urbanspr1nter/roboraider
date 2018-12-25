package com.mygdx.game.battle.dependency;

import java.util.HashMap;
import java.util.Map;

public class InjecteeContainer {
    private Map<Class<?>, Object> container;

    public InjecteeContainer() {
        this.container = new HashMap<>();
    }

    public void register(Class<?> type, Object object) {
        this.container.put(type, object);
    }

    public Object get(Class<?> type) {
        return this.container.get(type);
    }
}
