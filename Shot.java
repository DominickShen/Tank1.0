package com.project.tankgame;

public class Shot implements Runnable{
    int x;
    int y;
    int direct=0;
    int speed=3;
    boolean isLive=true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {

       while(true){

           try {
               Thread.sleep(50);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           switch (direct){
               case 0:
                   y-=speed;
                   break;
               case 1:
                   x+=speed;
                   break;
               case 2:
                   y+=speed;
                   break;
               case 3:
                   x-=speed;
                   break;
           }

           int WallSize=30;
           for (int i=0;i<WallSize;i++){
               Wall wall = new Wall((12 * (i + 1)),200);
               if(x==wall.getX()&& y==wall.getY()){
                   isLive=false;
                   break;
               }

           }
           if(!(x>=0&&x<=1000 && y>=0 &&y<=750 && isLive )){
               isLive=false;
               break;

           }


       }

    }
}
