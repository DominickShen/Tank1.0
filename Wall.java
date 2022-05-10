package com.project.tankgame;

import java.util.Vector;

public class Wall {

     int x;
    int y;
    Vector<Wall> walls=new Vector<>();
    boolean isLive=true;

    public void setWalls(Vector<Wall> walls) {
        this.walls = walls;
    }

    public Wall(int x, int y) {
        this.x = x;

        this.y = y;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
