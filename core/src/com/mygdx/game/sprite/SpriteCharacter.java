package com.mygdx.game.sprite;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class SpriteCharacter implements Disposable {
    private TreeMap<SpriteDirection, ArrayList<SpriteUnitLocationNode>> graph;
    private SpriteHandler sh;

    private HashMap<SpriteDirection, SpriteUnitLocationNode> initialNodesMap;

    public SpriteCharacter(String filename, int unitWidth, int unitHeight) {
        this.sh = new SpriteHandler(filename, unitWidth, unitHeight);
        this.graph = new TreeMap<SpriteDirection, ArrayList<SpriteUnitLocationNode>>();
        this.initialNodesMap = new HashMap<SpriteDirection, SpriteUnitLocationNode>();
        this.initializeGraph();
    }

    public SpriteHandler getSpriteHandler() {
        return this.sh;
    }

    public TreeMap<SpriteDirection, ArrayList<SpriteUnitLocationNode>> getGraph() {
        return this.graph;
    }

    @Override
    public void dispose() {
        this.sh.dispose();
    }

    private Direction getDirectionFromKey(SpriteDirection key) throws Exception {
        if(key.toString().contains("UP")) {
            return Direction.UP;
        } else if(key.toString().contains("DOWN")) {
            return Direction.DOWN;
        } else if(key.toString().contains("LEFT")) {
            return Direction.LEFT;
        } else if(key.toString().contains("RIGHT")) {
            return Direction.RIGHT;
        } else {
            throw new Exception("Unrecognized direction given.");
        }
    }

    private int getSpriteRowFromKey(SpriteDirection key) {
        int row = 0;

        try {
            if(getDirectionFromKey(key) == Direction.UP) {
                row = 3;
            } else if(getDirectionFromKey(key) == Direction.DOWN) {
                row = 0;
            } else if(getDirectionFromKey(key) == Direction.LEFT) {
                row = 1;
            } else if(getDirectionFromKey(key) == Direction.RIGHT) {
                row = 2;
            }
        } catch(Exception e) {
            row = 0;
        }

        return row;
    }

    private int getSpriteColumnFromKey(SpriteDirection key) {
        int col = 0;

        if (key.toString().contains("-0")) {
            col = 0;
        } else if(key.toString().contains("-1")) {
            col = 1;
        } else if(key.toString().contains("-2")) {
            col = 2;
        } else if(key.toString().contains("-3")) {
            col = 3;
        }

        return col;
    }

    private SpriteDirection getSpriteDirectionFromColumn(int column, Direction dir) {
        if(column == 0) {
            if(dir == Direction.UP) {
                return SpriteDirection.UP_0;
            } else if(dir == Direction.DOWN) {
                return SpriteDirection.DOWN_0;
            } else if(dir == Direction.LEFT) {
                return SpriteDirection.LEFT_0;
            } else if(dir == Direction.RIGHT) {
                return SpriteDirection.RIGHT_0;
            }
        } else if(column == 1) {
            if(dir == Direction.UP) {
                return SpriteDirection.UP_1;
            } else if(dir == Direction.DOWN) {
                return SpriteDirection.DOWN_1;
            } else if(dir == Direction.LEFT) {
                return SpriteDirection.LEFT_1;
            } else if(dir == Direction.RIGHT) {
                return SpriteDirection.RIGHT_1;
            }
        } else if(column == 2) {
            if(dir == Direction.UP) {
                return SpriteDirection.UP_2;
            } else if(dir == Direction.DOWN) {
                return SpriteDirection.DOWN_2;
            } else if(dir == Direction.LEFT) {
                return SpriteDirection.LEFT_2;
            } else if(dir == Direction.RIGHT) {
                return SpriteDirection.RIGHT_2;
            }
        } else if(column == 3) {
            if(dir == Direction.UP) {
                return SpriteDirection.UP_3;
            } else if(dir == Direction.DOWN) {
                return SpriteDirection.DOWN_3;
            } else if(dir == Direction.LEFT) {
                return SpriteDirection.LEFT_3;
            } else if(dir == Direction.RIGHT) {
                return SpriteDirection.RIGHT_3;
            }
        } else {
            throw new IndexOutOfBoundsException("Column too large.");
        }

        return SpriteDirection.UP_0;
    }

    private final void initializeGraph() {
        for(SpriteDirection key : SpriteDirection.values()) {
            if(key == SpriteDirection.START) {
                continue;
            }

            graph.put(key, new ArrayList<SpriteUnitLocationNode>());

            int skipCol = 0;
            int row = 0;

            row = getSpriteRowFromKey(key);
            skipCol = getSpriteColumnFromKey(key);

            for(int col = skipCol; col <= skipCol + 1 && col <= 3; col++) {
                SpriteUnitLocationNode n = new SpriteUnitLocationNode();

                if(key.toString().contains("UP")) {
                    n.dir = Direction.UP;
                } else if(key.toString().contains("DOWN")) {
                    n.dir = Direction.DOWN;
                } else if(key.toString().contains("LEFT")) {
                    n.dir = Direction.LEFT;
                } else if(key.toString().contains("RIGHT")) {
                    n.dir = Direction.RIGHT;
                }

                n.spriteDir = getSpriteDirectionFromColumn(col, n.dir);

                n.loc = this.sh.getSpriteUnitLocation(row, col);

                if(n.dir == Direction.UP && col == 0) {
                    initialNodesMap.put(SpriteDirection.UP_0, n);
                } else if(n.dir == Direction.DOWN && col == 0) {
                    initialNodesMap.put(SpriteDirection.DOWN_0, n);
                } else if(n.dir == Direction.LEFT && col == 0) {
                    initialNodesMap.put(SpriteDirection.LEFT_0, n);
                } else if(n.dir == Direction.RIGHT && col == 0) {
                    initialNodesMap.put(SpriteDirection.RIGHT_0, n);
                }

                if(col != skipCol) {
                    this.graph.get(key).add(n);
                }
                if(key.toString().contains("-3")) {
                    if(key == SpriteDirection.UP_3 && !this.graph.get(key).contains(initialNodesMap.get(SpriteDirection.UP_0))) {
                        this.graph.get(key).add(initialNodesMap.get(SpriteDirection.UP_0));
                    } else if(key == SpriteDirection.DOWN_3  && !this.graph.get(key).contains(initialNodesMap.get(SpriteDirection.DOWN_0))) {
                        this.graph.get(key).add(initialNodesMap.get(SpriteDirection.DOWN_0));
                    } else if(key == SpriteDirection.LEFT_3  && !this.graph.get(key).contains(initialNodesMap.get(SpriteDirection.LEFT_0))) {
                        this.graph.get(key).add(initialNodesMap.get(SpriteDirection.LEFT_0));

                    } else if(key == SpriteDirection.RIGHT_3  && !this.graph.get(key).contains(initialNodesMap.get(SpriteDirection.RIGHT_0))) {
                        this.graph.get(key).add(initialNodesMap.get(SpriteDirection.RIGHT_0));

                    }
                }
            }
        }

        for(SpriteDirection key : this.graph.keySet()) {
            if(key.toString().contains("UP")) {
                // add down 0, left 0, right 0
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.DOWN_0));
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.LEFT_0));
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.RIGHT_0));
            } else if(key.toString().contains("DOWN")) {
                // add up 0, left 0, right 0
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.UP_0));
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.LEFT_0));
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.RIGHT_0));
            } else if(key.toString().contains("LEFT")) {
                // add up 0, down 0, right 0
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.UP_0));
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.DOWN_0));
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.RIGHT_0));
            } else if(key.toString().contains("RIGHT")) {
                // add up 0, down 0, left 0
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.UP_0));
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.DOWN_0));
                this.graph.get(key).add(initialNodesMap.get(SpriteDirection.LEFT_0));
            }
        }

        this.graph.put(SpriteDirection.START, new ArrayList<SpriteUnitLocationNode>());
        this.graph.get(SpriteDirection.START).add(initialNodesMap.get(SpriteDirection.UP_0));
        this.graph.get(SpriteDirection.START).add(initialNodesMap.get(SpriteDirection.DOWN_0));
        this.graph.get(SpriteDirection.START).add(initialNodesMap.get(SpriteDirection.LEFT_0));
        this.graph.get(SpriteDirection.START).add(initialNodesMap.get(SpriteDirection.RIGHT_0));
    }
}
