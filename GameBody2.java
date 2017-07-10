import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GameBody2 extends JFrame implements KeyListener{
  public int keyCode;
  public GameManage gm;

  public static void main(String args[]){
    GameBody2 gb;
    Charcter.setup();
    gb = new GameBody2();

    while(true){
      if(gb.gm.isLiveHost() == false || gb.gm.isLiveGest() == false){
        break;
      }
    }

  }

  public GameBody2(){
    gm = new GameManage();
    addKeyListener(this);
  }

  public void keyPressed(KeyEvent e){
    keyCode = e.getKeyCode();
  }

  public void keyReleased(KeyEvent e){
    if(gm.isLiveHost() == false || gm.isLiveGest() == false){
      System.out.println("finish");
    }else{
      gm.battle();
    }
  }

  public void keyTyped(KeyEvent e){
  }

}

class GameManage{
  public static Charcter[] hostTeam = new Charcter[GameConst.TEAMCHARCTERS];
  public static Charcter[] gestTeam = new Charcter[GameConst.TEAMCHARCTERS];
  public ArrayList<Charcter> battleJoin = new ArrayList<Charcter>();

  public GameManage(){
    int i;

    for(i = 0;i < GameConst.TEAMCHARCTERS;i++){
      hostTeam[i] = Charcter.hostCharcters.get(i);
    }

    for(i = 0;i < GameConst.TEAMCHARCTERS;i++){
      gestTeam[i] = Charcter.gestCharcters.get(i+3);
    }

  }

  public void selectHostChar(int n){
    int index = 0;
    hostTeam[n] = Charcter.hostCharcters.get(index);
  }

  public void selectGestChar(int n){
    int index = 0;
    gestTeam[n] = Charcter.gestCharcters.get(index);
  }

  public void battle(){
    int i;
    for(i = 0;i < GameConst.TEAMCHARCTERS;i++){
        battleJoin.add(hostTeam[i]);
        battleJoin.add(gestTeam[i]);
    }
    Collections.sort(battleJoin,new CharcterComparator());

    for(i = 0;i < battleJoin.size();i++){
      //battleJoin.get(i).dispStatus();
      if(battleJoin.get(i).isLive() == true){
        battleJoin.get(i).action(battleJoin);
      }
    }
  }

  public boolean isLiveHost(){
    boolean _isLive = false;
    for(int i = 0;i < GameConst.TEAMCHARCTERS;i++){
      if(hostTeam[i].isLive() == true){
        _isLive = true;
      }
    }
    return _isLive;
  }

  public boolean isLiveGest(){
    boolean _isLive = false;
    for(int i = 0;i < GameConst.TEAMCHARCTERS;i++){
      if(gestTeam[i].isLive() == true){
        _isLive = true;
      }
    }
    return _isLive;
  }
}

class GameConst{
  private GameConst(){
  }
  public static final int TEAMCHARCTERS = 3;
  public static final int BUFFSIZE = 2048;
}

class CharcterComparator implements Comparator<Charcter>{
  public int compare(Charcter c1,Charcter c2){
    int result = 1;
    int select;
    Random rand;
    if(c1.getSpd() > c2.getSpd()){
      result = -1;
    }else if(c1.getSpd() == c2.getSpd()){
      rand = new Random();
      select = rand.nextInt(2);
      if(select == 0){
        result = -1;
      }
    }

    return result;
  }
}
class Charcter{
  public static ArrayList<Charcter> hostCharcters;
  public static ArrayList<Charcter> gestCharcters;
  private String _name;
  private int _hp,_atk,_def,_spd;
  private int _hpMax;


    private Charcter(){

    }

    public Charcter(String name,int hp,int atk,int def,int spd){
      _name = name;
      _hp = hp;
      _hpMax = _hp;
      _atk = atk;
      _def = def;
      _spd = spd;
    }

  public static void setup(){
    hostCharcters = new ArrayList<Charcter>();
    gestCharcters = new ArrayList<Charcter>();
    char buff[] = new char[GameConst.BUFFSIZE];
    try{
      FileReader rd = new FileReader("hostCharcters.txt");
      int hoge = rd.read(buff);
      rd.close();
      String str = new String(buff,0,hoge);
      String crlf = System.getProperty("line.separator");

      Pattern p = Pattern.compile("[," + crlf + "]+");
      String[] status = p.split(str);
      for(int i = 0;i < status.length;i++){
        hostCharcters.add(new Charcter(status[i++],Integer.parseInt(status[i++]),Integer.parseInt(status[i++]),Integer.parseInt(status[i++]),Integer.parseInt(status[i])));
      }

      rd = new FileReader("gestCharcters.txt");
      hoge = rd.read(buff);
      rd.close();
      str = new String(buff,0,hoge);
      status = p.split(str);
      for(int i = 0;i < status.length;i++){
        gestCharcters.add(new Charcter(status[i++],Integer.parseInt(status[i++]),Integer.parseInt(status[i++]),Integer.parseInt(status[i++]),Integer.parseInt(status[i])));
      }
    }catch(IOException e){
      System.out.println("error" + e);
    }

  }


  public void dispStatus(){
    System.out.println("name is " + _name);
    System.out.println("hp = " + _hp);
    System.out.println("atk = " + _atk);
    System.out.println("def = " + _def);
    System.out.println("spd = " + _spd);
    }

    public boolean isLive(){
      boolean _isLive = true;
      if(_hp <= 0){
        _isLive = false;
      }
      return _isLive;
    }

    public int getSpd(){
      return _spd;
    }

    public String getName(){
      return _name;
    }

    public void damaged(int damage){
      damage -= _def;
      if(damage < 0){
        damage = 0;
      }
      _hp -= damage;
    }

    public void action(ArrayList<Charcter> battleJoin){
      Random rand;
      int index;
      rand = new Random();
      index = rand.nextInt(battleJoin.size());
      battleJoin.get(index).damaged(_atk);
      System.out.println(_name + " attacked " + battleJoin.get(index).getName());
    }
}
