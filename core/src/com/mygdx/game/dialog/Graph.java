/*
 * Graph.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * This builds and stores the graph representation of a dialog
 * between the player and NPC.
 */

package com.mygdx.game.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.*;

/*
    You may now be wondering by now... How the *bleeping-bleep* does this all work?

    First, let's talk about how our dialog tree is constructed. In short, the
    interaction between the player and an NPC is based off of a script. Each dialog
    text is basically a decision point in which can fork a path to different contextual
    interaction.

    If an NPC's dialog script is linear, that is, no decision paths to fork to, then
    our dialog tree, is basically a LinkedList of of single item lists. It's also
    worthy to mention that every dialog tree starts its context at an empty statement.

    [ "" ] <-- pointer
       |
    [Hello]
       |
    [See you tomorrow...]
       |
    [And, goodbye!]

    When the NPC interaction begins, we basically move the pointer in this list one
    forward, and set that text to be displayed.

    [ "" ]
       |
    [Hello] <-- pointer
       |
    [See you tomorrow...]
       |
    [And, goodbye!]

    We keep moving forward by listening to the SPACE bar invoked by the player until
    there are no more dialog statements to traverse to.

    Easy enough.

    This all however, gets complicated when we introduce forks within the dialog.

    For example, take a simple dialog of an NPC asking what the player's name is. We
    can basically model the dialog tree to be like this:

         [ "" ]
           |
    ["Hello. What is your name?"]
        /                 \
     ["Roger."]        ["Alexa."]
       /                   \
    ["Hello, Roger."]  ["Hello, Alexa."]
            \             /
     ["It is very nice to meet you."]

    We can see that the first opportunity to invoke an action ona decision path begins at
    the statement "Hello. What is your name?" where the forks are "Roger.", and "Alexa.".

    The statement "Hello. What is your name?" is then a key of our node, while the values "Roger."
    and "Alexa." are keys to neighboring nodes. Depending on where the player wants to fork to,
    we will traverse it through like a graph. How I have done it here is implement it as an adjacency
    list of Map<String, List<String>> where the nodes are simply string objects. (or, not so simply at all?)

    So, knowing this fact, we need to get a good representation of how we can model this dialog tree in
    plaintext sot hat we can deserialize it into a graph object.

    The above can be modeled as a JSON file like this:

    {
        "text": [
            {
                "key": "Hello. What is your name?",
                "neighbors": ["Roger.", "Alexa."]
            },
            {
                "key": "Roger.",
                "neighbors": ["Hello, Roger."]
            },
            {
                "key": "Alexa.",
                "neighbors": ["Hello, Alexa."]
            },
            ...
            {
                "key": "It is very nice to meet you."
                "neighbors": []
            }
        ]
    }

    You can see that interaction stops when the current node the pointer points to no longer has any
    neighbors to go to. In this case, the dialog ends at "It is very nice to meet you."

    Algorithmically, how can we actually traverse through this graph in consideration to actions which
    the player invokes?

    Whenever NPC interaction occurs, we just inspect the pointer which currently points to the current
    node our dialog tree.

    If the node contains just a single element for its collection of neighbors, we simply do a linear
    traversal and go to that node, and display the text value which this newly pointed value contains.

    Suppose if the node contains more than a single element in its collection of neighbors. Then, this is
    the case of palyer-to-NPC interaction where we must give the player the opportunity to fork into a
    new path. We need to set a few flags to

    We display the text value which the node contains, and then inspect the neighbors. We then set a
    few flags to let our renderer know that it must render out the choices, and override the
    LEFT, RIGHT, UP, DOWN buttons and transfer control to a new set of key-handlers to manipulate the
    cursor on the visible area of the screen.

    This is where the player then sees all the choices, we wait for manual input to allow the player to
    specify which node should be the new "current" node. This is their decision.

    Once we receive that input from the user, we then set that as the current node of the graph, and then
    proceed to display the text of the next neighbor. Basically the process repeats. If there is a single node,
    then display it, otherwise begin a new decision prompt.
 */

public class Graph {
    private Map<String, List<String>> theGraph;
    private List<String> currTextData;
    private String currKey;

    public Graph(String filename) {
        JsonReader reader = new JsonReader();
        JsonValue data = reader.parse(Gdx.files.internal(filename));

        JsonValue textData = data.get("text");

        this.theGraph = new LinkedHashMap<>();
        this.theGraph.put("", new LinkedList<>());

        for(int i = 0; i < textData.size; i++) {
            String key = textData.get(i).getString("key");
            if(i == 0) {
                this.theGraph.get("").add(key);
                this.theGraph.put(key, new LinkedList<>());
            } else if(!this.theGraph.containsKey(key)) {
                this.theGraph.put(key, new LinkedList<>());
            }
        }

        List<String> keys = new ArrayList<>(this.theGraph.keySet());
        for(int i = 1; i < keys.size(); i++) {
            String currKey = keys.get(i);
            for(int j = 0; j < textData.get(i - 1).get("neighbors").size; j++) {
                String neighbor = textData.get(i - 1).get("neighbors").getString(j);

                this.theGraph.get(currKey).add(neighbor);
            }
        }

        // Initialize the state of the graph to point to the initial node.
        if(this.theGraph.keySet().size() > 0) {
            this.currTextData = this.theGraph.get("");
            this.currKey = "";
        }
    }

    public List<String> currentDialog() {
        return this.currTextData;
    }

    public String text() {
        return this.currKey;
    }

    public void setNext(String currText) {
        this.currTextData = this.theGraph.get(currText);
        this.currKey = currText;
    }

    public void reset() {
        this.currTextData = this.theGraph.get("");
        this.currKey = "";
    }
}
