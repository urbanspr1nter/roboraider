package com.mygdx.game.map;

import com.mygdx.game.map.enums.Action;

import java.awt.*;

public class EnvironmentTrigger {
    private int locationTolerance;
    private Point cellLocation;
    private Point mapLocation;
    private Action action;

    public EnvironmentTrigger(Point cellLocation, Action action) {
        this.cellLocation = cellLocation;
        this.mapLocation = new Point(
        this.cellLocation.x * 16,
        this.cellLocation.y * 16
        );
        this.action = action;
        this.locationTolerance = 1;
    }

    public void setCellLocation(Point cellLocation) {
        this.cellLocation = cellLocation;
        this.mapLocation = new Point(
        this.cellLocation.x * 16,
        this.cellLocation.y * 16
        );
    }

    public void setLocationTolerance(int tolerance) {
        this.locationTolerance = tolerance;
    }

    public boolean canTrigger(Point playerCellLocation) {
        if((playerCellLocation.x - this.locationTolerance) == this.cellLocation.x
            || (playerCellLocation.x + this.locationTolerance) == this.cellLocation.x
            && (playerCellLocation.y - this.locationTolerance) == this.cellLocation.y
            || (playerCellLocation.y + this.locationTolerance) == this.cellLocation.y) {
            return true;
        }

        return false;
    }

    public int getLocationTolerance() {
        return this.locationTolerance;
    }

    public Point getCellLocation() {
        return this.cellLocation;
    }

    public Point getMapLocation() {
        return this.mapLocation;
    }

    public Action getAction() {
        return this.action;
    }

    public String key() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "CELL-" + this.cellLocation.x + "-" + this.cellLocation.y;
    }
}
