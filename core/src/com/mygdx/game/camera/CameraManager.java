/*
 * CameraManager.java
 *
 * Author: Roger Ngo
 * Copyright 2018
 *
 * This class is used to manage the camera throughout a shared.
 *
 * It provides scroll handling within the current map to ensure the character
 * stays within the viewport in an orthographic view.
 *
 * It works ok.
 */

package com.mygdx.game.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Direction;
import com.mygdx.game.configuration.Configuration;
import com.mygdx.game.configuration.ConfigurationBuilder;
import com.mygdx.game.util.structures.Tuple;

import java.awt.*;

public class CameraManager {
    private static int MOVEMENT_DELTA;
    private final int EDGE_PADDING = 4;

    private Point initialTranslation;
    private Point mapDimensions;
    private Point currentDisplacement;
    private Point initialPosition;
    private Point initialViewPort;

    private OrthographicCamera orthographicCamera;

    public CameraManager(OrthographicCamera camera, Point mapDimensions, Point initialPosition, Point initialViewport) {
        Configuration configuration = ConfigurationBuilder.config();

        MOVEMENT_DELTA = configuration.Sprite.StepLength;

        this.orthographicCamera = camera;
        this.orthographicCamera.setToOrtho(false, initialViewport.x, initialViewport.y);

        this.mapDimensions = mapDimensions;
        this.initialViewPort = initialViewport;

        this.setInitialPosition(initialPosition);
    }

    public final void setInitialPosition(Point initialPosition) {
        this.initialPosition = initialPosition;

        // calculate the x and y translation
        int translateX = 0;
        int translateY = 0;

        int deltaX = this.initialPosition.x - (initialViewPort.x / 2);
        if(deltaX > 0) {
            translateX = deltaX;
        }

        int deltaY = this.initialPosition.y - (initialViewPort.y / 2);
        if(deltaY > 0) {
            translateY = deltaY;
        }

        this.initialTranslation = new Point(translateX, translateY);

        this.orthographicCamera.translate(this.initialTranslation.x, this.initialTranslation.y);
        this.currentDisplacement = new Point(this.initialPosition.x, this.initialPosition.y);

        this.orthographicCamera.update();
    }

    public void updateInputDirection(Direction direction) {
        if(this.currentDisplacement.x - MOVEMENT_DELTA < 0 && direction == Direction.LEFT) {
            return;
        } else if(this.currentDisplacement.x + MOVEMENT_DELTA
                > this.mapDimensions.x - MOVEMENT_DELTA && direction == Direction.RIGHT) {
            return;
        } else if(this.currentDisplacement.y - MOVEMENT_DELTA < 0 && direction == Direction.DOWN) {
            return;
        } else if(this.currentDisplacement.y + MOVEMENT_DELTA
                > this.mapDimensions.y - MOVEMENT_DELTA && direction == Direction.UP) {
            return;
        }

        if(direction == Direction.LEFT) {
            this.currentDisplacement.x -= MOVEMENT_DELTA;
        } else if(direction == Direction.RIGHT) {
            this.currentDisplacement.x += MOVEMENT_DELTA;
        } else if(direction == Direction.UP) {
            this.currentDisplacement.y += MOVEMENT_DELTA;
        } else if(direction == Direction.DOWN) {
            this.currentDisplacement.y -= MOVEMENT_DELTA;
        }

        this.adjustCamera(direction);
    }

    public OrthographicCamera getCamera() {
        return this.orthographicCamera;
    }

    public Point getCurrentDisplacement() {
        return this.currentDisplacement;
    }

    private void adjustCamera(Direction direction) {
        if(!this.needScroll(direction)) {
            return;
        }

        Point scrollPoints = this.computeScrollPoints();

        if(this.currentDisplacement.x >= scrollPoints.x && direction == Direction.RIGHT) {
            this.translate(Direction.RIGHT);
        } else if(this.currentDisplacement.x < scrollPoints.x && direction == Direction.LEFT) {
            this.translate(Direction.LEFT);
        }

        if(this.currentDisplacement.y >= scrollPoints.y && direction == Direction.UP) {
            this.translate(Direction.UP);
        } else if(this.currentDisplacement.y < scrollPoints.y && direction == Direction.DOWN) {
            this.translate(Direction.DOWN);
        }

        this.orthographicCamera.update();
    }

    private void translate(Direction direction) {
        if(direction == Direction.RIGHT) {
            this.orthographicCamera.translate(MOVEMENT_DELTA, 0);
            this.initialTranslation.x += MOVEMENT_DELTA;
        } else if(direction == Direction.LEFT) {
            this.orthographicCamera.translate(-MOVEMENT_DELTA, 0);
            this.initialTranslation.x -= MOVEMENT_DELTA;
        } else if(direction == Direction.UP) {
            this.orthographicCamera.translate(0, MOVEMENT_DELTA);
            this.initialTranslation.y += MOVEMENT_DELTA;
        } else if(direction == Direction.DOWN) {
            this.orthographicCamera.translate(0, -MOVEMENT_DELTA);
            this.initialTranslation.y -= MOVEMENT_DELTA;
        }
    }

    private boolean needScroll(Direction direction) {
        Point halfViewPortDimensions = new Point(
        (int)this.orthographicCamera.viewportWidth / 2,
        (int)this.orthographicCamera.viewportHeight / 2
        );

        if((this.orthographicCamera.position.x + halfViewPortDimensions.x)
                >= (this.mapDimensions.x - EDGE_PADDING)
                && direction == Direction.RIGHT) {
            return false;
        } else if(this.orthographicCamera.position.x
                <= (halfViewPortDimensions.x + EDGE_PADDING)
                && direction == Direction.LEFT) {
            return false;
        } else if((this.orthographicCamera.position.y + halfViewPortDimensions.y)
                >= (this.mapDimensions.y - EDGE_PADDING)
                && direction == Direction.UP) {
            return false;
        } else if(this.orthographicCamera.position.y
                <= (halfViewPortDimensions.y + EDGE_PADDING)
                && direction == Direction.DOWN) {
            return false;
        }

        return true;
    }

    private Point computeScrollPoints() {
        Point halfViewPortDimensions = new Point(
            (int)this.orthographicCamera.viewportWidth / 2,
            (int)this.orthographicCamera.viewportHeight / 2
        );

        int cameraX = (int)(this.orthographicCamera.position.x);
        int cameraY = (int)(this.orthographicCamera.position.y);

        Tuple<Integer> xEdge = new Tuple((cameraX- halfViewPortDimensions.x), (cameraX + halfViewPortDimensions.x));
        Tuple<Integer> yEdge = new Tuple((cameraY - halfViewPortDimensions.y), (cameraY + halfViewPortDimensions.y));

        Point scrollPoints = new Point(xEdge.first + halfViewPortDimensions.x, yEdge.first + halfViewPortDimensions.y);

        return scrollPoints;
    }
}
