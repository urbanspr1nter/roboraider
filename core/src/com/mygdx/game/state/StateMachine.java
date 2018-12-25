/*
 * StateMachine.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * StateMachine will manage the various states within the shared.
 */

package com.mygdx.game.state;

import com.mygdx.game.GameStore;
import com.mygdx.game.GameStoreEntity;

import java.util.*;

public class StateMachine {
    // Since states and entities are tied together, we want to keep these
    // two objects encapsulated within a bigger object for easier stack-management.
    class StateMachineNode {
        private String key;
        private BaseState state;

        StateMachineNode(String key, BaseState state) {
            this.key = key;
            this.state = state;
        }

        String getKey() {
            return this.key;
        }

        BaseState getState() {
            return this.state;
        }
    }

    private StateMachineNode previousState;
    private StateMachineNode currentState;
    private Map<String, List<StateMachineNode>> graph;

    private GameStore store;

    public StateMachine(GameStore store) {
        this.graph = new HashMap<>();
        this.store = store;
    }

    public void exitState() {
        this.currentState.getState().exit();
    }

    public void setState(String name, BaseState state) {
        StateMachineNode node = new StateMachineNode(name, state);

        if(!this.graph.containsKey(name)) {
            this.graph.put(name, new LinkedList<>());
        }

        if(this.currentState != null) {
            this.graph.get(currentState.key).add(node);
            this.graph.get(name).add(currentState);
        }

        this.previousState = this.currentState;
        this.currentState = node;

        this.store.setEntity(this.currentState.getState().getEntity());

        // call the hooks
        this.currentState.getState().enter();
        this.currentState.getState().update();
    }

    public BaseState getPreviousState() {
        return this.previousState.getState();
    }

    public String getPreviousStateKey() {
        return this.previousState.getKey();
    }

    public GameStoreEntity getPreviousEntity() {
        return this.previousState
                .getState()
                .getEntity();
    }

    public String getStateKey() {
        return this.currentState.getKey();
    }

    public BaseState getState() {
        return this.currentState.getState();
    }

    public GameStoreEntity getEntity() {
        return this.currentState
                .getState()
                .getEntity();
    }
}
