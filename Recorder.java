package com.project.tankgame;

import java.io.*;
import java.util.Vector;

public class Recorder {

    //定义变量，记录我方机会敌人坦克数
    private static int allEnemyTankNum=0;
    //定义IO对象
    private static FileWriter fw=null;
    private static BufferedWriter bw=null;
    private static BufferedReader br=null;
    private static String RecordFile="src\\myRecord.txt";
    private  static Vector<EnemyTank> enemyTanks=null;

    //定义一个Node的Vector用于保存敌人的信息
    private static Vector<Node> nodes=new Vector<>();

    //增加一个方法，用于读取RecordFile,恢复相关信息
    public static Vector<Node> getNodesAndEnemyTankNumRec(){

        try {
            br=new BufferedReader(new FileReader(RecordFile));
            allEnemyTankNum=Integer.parseInt(br.readLine());
            //循环读取文件,生成nodes集合
            String line="";
            while((line=br.readLine())!=null){
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //增加一个方法当游戏退出时，将值保存到RecordFile
    //保存敌人坦克坐标和方向
    public static void keepRecord(){


        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(RecordFile));
            bw.write(allEnemyTankNum+"\r\n");
          //   bw.newLine();
            //遍历敌人坦克,根据情况保存即可
            //定义一个属性，然后通过set方法得到敌人的vector
            for(int i=0;i<enemyTanks.size();i++){

                EnemyTank enemyTank=enemyTanks.get(i);
                if(enemyTank.isLive){
                    String record=enemyTank.getX()+" "+enemyTank.getY()+" "+enemyTank.getDirect();
                    bw.write(record+"\r\n");
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }
    //当我方坦克击中敌方坦克allEnemyTankNum++
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }

}
