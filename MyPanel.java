package com.project.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener,Runnable{


    MyHero hero=null;
    Vector<Wall> walls=new Vector<>();
    Vector<Wall2> walls2=new Vector<>();
    Vector<EnemyTank> enemyTanks=new Vector<>();
    Vector<Node> nodes=new Vector<>();
    Vector<Bomb>  bombs=new Vector<>();
    Image image1=null;
    Image image2=null;
    Image image3=null;



//     int enemyTankSize=3;


    Random random=new Random();
    int enemycount=random.nextInt(10);

     int WallSize=30;
     int WallSize2=20;

     public MyPanel(String key){
//         nodes=Recorder.getNodesAndEnemyTankNumRec();
         Recorder.setEnemyTanks(enemyTanks);
         hero=new MyHero(480,650);
         hero.setSpeed(5);
         //创建墙循环显示
         for (int i=0;i<WallSize;i++){
             Wall wall = new Wall((12 * (i + 1)),200);
             wall.setWalls(walls);
             walls.add(wall);


         }
         for (int i=0;i<WallSize2;i++){
             Wall2 wall2 = new Wall2((20* (i + 1)),400);
             wall2.setWalls2(walls2);
             walls2.add(wall2);


         }
         switch (key){
             case "1":
                 for (int i=0;i<enemycount;i++) {


                     //创建一个敌人坦克 设置方向
                     EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 10);
                     enemyTank.setEnemyTanks(enemyTanks);
                     enemyTank.setDirect((int) (Math.random() * (4 - 1) + 1));
                     //启动敌人坦克线程
                     new Thread(enemyTank).start();
                     Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                     enemyTank.shots.add(shot);
                     new Thread(shot).start();
                     enemyTanks.add(enemyTank);

                 }
                 break;

             case "2":

                 for (int i=0;i<nodes.size();i++) {
                     Node node = nodes.get(i);
                     //创建一个敌人坦克 设置方向
                     EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                     enemyTank.setEnemyTanks(enemyTanks);
                     enemyTank.setDirect(node.getDirect());
                     //启动敌人坦克线程
                     new Thread(enemyTank).start();

                     Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                     enemyTank.shots.add(shot);
                     new Thread(shot).start();
                     enemyTanks.add(enemyTank);

                 }
                     break;
             default:
                 System.out.println("输入错误");
         }


         image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
         image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
         image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));


    }


    //显示我方击毁坦克信息
    public void showInfo(Graphics g){
         g.setColor(Color.black);
         Font font=new Font("宋体",Font.BOLD,25);
         g.setFont(font);
         g.drawString("您累计击毁敌方坦克",1020,30);
         drawTank(1020,60,g,0,1);
         g.setColor(Color.BLACK);
         g.drawString(Recorder.getAllEnemyTankNum()+"",1080,100);
//         g.drawString(enemycount);

//         if(hero.isLive=false ){
//             System.out.println("11");
////             JOptionPane.showMessageDialog(null, "GAME OVER","GAME OVER",JOptionPane.INFORMATION_MESSAGE);
//         }

    }

    @Override
    public void paint(Graphics g) {


        super.paint(g);

        g.fillRect(0,0,1000,750);
        showInfo(g);
        if(hero.isLive==false|| enemyTanks.isEmpty()){
            g.setColor(Color.BLUE);
            Font font=new Font("宋体",Font.BOLD,60);
            g.setFont(font);
            g.drawString("游戏结束",400,400);

        }
        if(hero!=null &&hero.isLive){

            drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),0);
        }
        //画墙
//        for(int i=0;i<walls.size();i++){
//
//            Wall wall=walls.get(i);
//            if (wall.isLive ) {
//                drawWall(wall.getX(),wall.getY(),g,0);
//
//
//            }
//        }
//        for(int i=0;i<walls2.size();i++){
//
//            Wall2 wall2=walls2.get(i);
//            if (wall2.isLive ) {
//
//                drawWall(wall2.getX(),wall2.getY(),g,1);
//
//            }
//        }
        //将hero的子弹集合shots遍历取出绘制
        for(int i=0;i<hero.shots.size();i++){
            Shot shot = hero.shots.get(i);
            if(shot!=null && shot.isLive==true){
//                g.draw3DRect(shot.x,shot.y,2,1,false);
                g.fillOval(shot.x ,shot.y,5,5);
            }else{
                hero.shots.remove(shot);
            }
        }

//爆炸
        for (int i=0;i<bombs.size();i++){
            Bomb bomb = bombs.get(i);
            if(bomb.life>6){
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
            }else if(bomb.life>3){
                g.drawImage(image2,bomb.x,bomb.y,60,60,this);

            }else{
                g.drawImage(image3,bomb.x,bomb.y,60,60,this);
            }
            bomb.lifeDown();
            if(bomb.life==0){
                bombs.remove(bomb);
            }

        }


        for(int i=0;i<enemyTanks.size();i++){

           EnemyTank enemyTank=enemyTanks.get(i);
            if (enemyTank.isLive ) {
                drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirect(),1);
                for( int j=0;j<enemyTank.shots.size();j++){
                    Shot shot=enemyTank.shots.get(j);
                    if(shot.isLive){
//                        g.draw3DRect(shot.x ,shot.y,5,5,false);

                        g.fillOval(shot.x ,shot.y,5,5);

                    }else{
                        enemyTank.shots.remove(shot);
                    }
                }

           }


        }


        //drawTank(hero.getX()+60,hero.getY(),g,0,1);
    }
//    //画墙
//    public void drawWall(int x,int y,Graphics g,int type){
//
//         switch (type){
//             case 0:
//                 g.setColor(Color.orange);
//                 break;
//             case 1:
//                 g.setColor(Color.LIGHT_GRAY);
//                 break;
//         }
//         g.fillRect(x,y,10,20);
//
//
//    }


    //draw 坦克

    public void drawTank(int x,int y,Graphics g,int direction,int type){

        switch(type){
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.orange);
                break;

        }
        switch (direction){
            case 0:
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 1:
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+20,y+20,x+60,y+20);
                break;
            case 2:
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3:
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;
             default:
                 System.out.println("暂不处理");
        }



    }

    public void hitEnemyTank(){
       // 遍历我们的子弹
        for(int j=0;j<hero.shots.size();j++){
            Shot shot = hero.shots.get(j);
            if (shot != null && shot.isLive) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);

                }
            }
        }
        if (hero.shot != null && hero.shot.isLive) {
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                hitTank(hero.shot, enemyTank);

            }
        }

    }
  //判断敌人坦克是否击中我们的坦克
    public void hitMyHero(){
         for(int i=0;i<enemyTanks.size();i++){
             EnemyTank enemyTank=enemyTanks.get(i);
             for(int j=0;j<enemyTank.shots.size();j++){
                 Shot shot = enemyTank.shots.get(j);
                 if(hero.isLive&&shot.isLive){
                     hitTank(shot,hero);
                 }

             }
         }
    }
    //敌人坦克击中了墙
//    public void EnemyHitWall(){
//        for(int i=0;i<enemyTanks.size();i++){
//            EnemyTank enemyTank=enemyTanks.get(i);
//            for(int j=0;j<enemyTank.shots.size();j++){
//                Shot shot = enemyTank.shots.get(j);
//                if(shot.isLive){
//                    for (int k = 0; k < walls.size(); k++) {
//                        Wall wall= walls.get(k);
//                        EHitWall(shot,wall);
//                    }
//                    for (int m=0;m<walls2.size();m++){
//                        Wall2 wall2=walls2.get(m);
//                        EHitGaryWall(shot,wall2);
//                    }
//                }
//
//            }
//        }
//    }
//    //判断我方坦克是否击中墙
//    public void myhitWall(){
//        // 遍历我们的子弹
//        for(int j=0;j<hero.shots.size();j++){
//            Shot shot = hero.shots.get(j);
//            if (shot != null && shot.isLive) {
//                for (int i = 0; i < walls.size(); i++) {
//                    Wall wall= walls.get(i);
//                    hitWall(shot, wall);
//
//
//                }
//                for (int m=0;m<walls2.size();m++){
//                    Wall2 wall2=walls2.get(m);
//                    HitGrayWall(shot, wall2);
//                }
//            }
//        }
//
//    }
//
//    //敌方坦克击中墙
//    public void EHitWall(Shot s,Wall wall){
//        if(s.x>wall.getX() && s.x<wall.getX()+10 &&s.y>wall.getY() && s.y<wall.getY()+20){
//            s.isLive=false;
//        }
//    }
//    public void EHitGaryWall(Shot s,Wall2 wall2){
//        if(s.x>wall2.getX() && s.x<wall2.getX()+10 &&s.y>wall2.getY() && s.y<wall2.getY()+20){
//            s.isLive=false;
//        }
//    }
//    public void hitWall(Shot s,Wall wall) {
//
//        if (s.x > wall.getX() && s.x < wall.getX() + 10 && s.y > wall.getY() && s.y < wall.getY() + 20) {
//
//            s.isLive = false;
//            wall.isLive = false;
//            walls.remove(wall);
////                    Bomb bomb = new Bomb(wall.getX(), wall.getY());
////                    bombs.add(bomb);
//
//        }
//    }
//    //击中灰墙
//    public void HitGrayWall(Shot s,Wall wall){
//
//            if(s.x>wall.getX() && s.x<wall.getX()+10 &&s.y>wall.getY() && s.y<wall.getY()+20){
//
//                s.isLive=false;
//
//            }
//
//
//
//    }
    //我方坦克与碰撞检测
//    public void CollisionWall(){
//        if(hero.getX()){
//            System.out.println("!111");
//        }
//
////           if( >wall.getX() &&hero.getX()<wall.getX()+40&&hero.getY()>wall.getY()&&hero.getY()<wall.getY()+60){
////               System.out.println("AAA");
////           }
//    }


    public  void hitTank(Shot s,Tank tank){

         switch (tank.getDirect()){
             case 0:
             case 2:
                 if(s.x>tank.getX() && s.x<tank.getX()+40 &&s.y>tank.getY() && s.y<tank.getY()+60){

                     s.isLive=false;
                     tank.isLive=false;
                     enemyTanks.remove(tank);
                     if(tank instanceof  EnemyTank){
                         Recorder.addAllEnemyTankNum();
                     }
                     Bomb bomb = new Bomb(tank.getX(), tank.getY());
                     bombs.add(bomb);


                 }
                 break;
             case 1:
             case 3:
                 if(s.x>tank.getX() && s.x<tank.getX()+60 &&s.y>tank.getY() && s.y<tank.getY()+40){

                     s.isLive=false;
                     tank.isLive=false;
                     enemyTanks.remove(tank);
                     if(tank instanceof  EnemyTank){
                         Recorder.addAllEnemyTankNum();
                     }
                     Bomb bomb = new Bomb(tank.getX(), tank.getY());
                     bombs.add(bomb);

                 }
                 break;
         }

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_ENTER){

        }
        if(e.getKeyCode()==KeyEvent.VK_W){
                hero.setDirect(0);
                if(hero.getY()>0){
                    hero.moveUp();
                }



        }else if(e.getKeyCode()==KeyEvent.VK_D){
            hero.setDirect(1);
            if(hero.getX()+60<1000){
                hero.moveRight();
            }


        }else if(e.getKeyCode()==KeyEvent.VK_S){
            hero.setDirect(2);
            if(hero.getY()+60<750) {
                hero.moveDown();
            }


        }else if(e.getKeyCode()==KeyEvent.VK_A){
            hero.setDirect(3);
            if(hero.getX()>0) {
                hero.moveLeft();
            }



        }
        if(e.getKeyCode()==KeyEvent.VK_J){
           // System.out.println("用户按下了");
//        //    if(hero.shot==null||hero.shot.isLive==false){
//                hero.shotEnemyTank();
//          //  }
            //发射多颗子弹
            hero.shotEnemyTank();

        }
        hitEnemyTank();
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//判断是否击中敌人坦克
            hitEnemyTank();
            hitMyHero();
//            myhitWall();
//            EnemyHitWall();
//            heroCollisionWall();
            this.repaint();


        }


    }
}
