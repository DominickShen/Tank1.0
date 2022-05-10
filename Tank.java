package com.project.tankgame;

import java.awt.*;

public class Tank {
    private int x;
    private int y;
    private int direct;
    private int speed=2;
    boolean isLive=true;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void moveUp(){
        if( y>0) {
            y -= speed;
        }

    }
    public void moveRight(){
        if(x+60<1000 ) {
            x += speed;
        }
    }
    public void moveDown(){
        if(y+60<750) {
            y += speed;
        }
    }
    public void moveLeft(){
        if(x>0) {
            x -= speed;
        }
    }

    public Tank(int x, int y) {
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }
}
