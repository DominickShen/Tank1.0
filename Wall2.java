package com.project.tankgame;

import java.util.Vector;

public class Wall2 extends Wall {
    Vector<Wall2> walls2=new Vector<>();
    public Wall2(int x, int y) {
        super(x, y);
    }

    public void setWalls2(Vector<Wall2> walls2) {
        this.walls2 = walls2;
    }
}
