import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.util.Timer;

class GameManage extends JFrame implements ActionListener , KeyListener{
  public static Charcter[] hostTeam = new Charcter[GameConst.TEAMCHARCTERS];
  public static Charcter[] gestTeam = new Charcter[GameConst.TEAMCHARCTERS];
  public ArrayList<Charcter> battleJoin = new ArrayList<Charcter>();
  public int strIndex;
  public String typedStr;
  public int viewPointer;

  public JPanel p;
  public GraphicsDevice device;
  public boolean isHost = false;
  public boolean isGest = false;

  public int selectChange = 0;

  public static void main(String args[]){
    GameManage frame;
    Timer t  = new Timer();
    RealTime rt = new RealTime();
    Charcter.setup();
    frame = new GameManage();
    frame.addKeyListener(frame);
    t.schedule(rt,0,500);
    frame.setVisible(true);
  }

  public GameManage(){
    int i;

    for(i = 0;i < GameConst.TEAMCHARCTERS;i++){
      hostTeam[i] = Charcter.hostCharcters.get(i);
    }

    for(i = 0;i < GameConst.TEAMCHARCTERS;i++){
      gestTeam[i] = Charcter.gestCharcters.get(i);
    }

    strIndex = 0;
    typedStr = new String("helloworld");

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    device = ge.getDefaultScreenDevice();
    GraphicsConfiguration gc = device.getDefaultConfiguration();

    setTitle("typing rpg");
    setSize(GameConst.DISP_WIDTH,GameConst.DISP_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    p = new JPanel();

    p.addKeyListener(this);

    device.setFullScreenWindow(this);

    viewTrans(GameConst.TITLE);
  }

  public void typing(int keyCode){
    System.out.println(strIndex);
    if(keyCodeToChar(keyCode) == typedStr.charAt(strIndex)){
      strIndex++;
    }
    if(strIndex == typedStr.length()){
      strIndex = 0;
    }
  }

  public char keyCodeToChar(int keyCode){
    return (char)(keyCode - 65 + 'a');//keycode:65 A
  }

  public void selectHostChar(int n){
    hostTeam[selectChange] = Charcter.hostCharcters.get(n);
  }

  public void selectGestChar(int n){
    gestTeam[selectChange] = Charcter.gestCharcters.get(n);
  }

  public void battle(){
    int i;
    for(i = 0;i < GameConst.TEAMCHARCTERS;i++){
      battleJoin.add(hostTeam[i]);
      battleJoin.add(gestTeam[i]);
    }
    Collections.sort(battleJoin,new CharcterComparator());

    for(i = 0;i < battleJoin.size();i++){
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

  public void viewTrans(int viewState){
    p.removeAll();
    viewPointer = viewState;
    switch(viewPointer){
      case GameConst.TITLE:
      p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
      JLabel title = new JLabel("Survival Colosseum with typing");
      JButton makeRoom = new JButton("make room");
      JButton joinRoom = new JButton("join room");
      JButton setting =  new JButton("  setting  ");
      title.setAlignmentX(0.5f);
      makeRoom.setAlignmentX(0.5f);
      joinRoom.setAlignmentX(0.5f);
      setting.setAlignmentX(0.5f);
      title.setFont(new Font("Century", Font.BOLD, 100));
      makeRoom.setFont(new Font("Century", Font.BOLD, 100));
      joinRoom.setFont(new Font("Century", Font.BOLD, 100));
      setting.setFont(new Font("Century", Font.BOLD, 100));
      makeRoom.setPreferredSize(new Dimension(200, 100));
      joinRoom.setPreferredSize(new Dimension(200, 100));
      setting.setPreferredSize(new Dimension(200, 100));
      joinRoom.setMargin(new Insets(0, 50, 0, 50));
      setting.setMargin(new Insets(0, 45, 0, 45));


      makeRoom.addKeyListener(this);
      joinRoom.addKeyListener(this);
      setting.addKeyListener(this);


      p.add(Box.createRigidArea(new Dimension(1,100)));
      p.add(title);
      p.add(Box.createRigidArea(new Dimension(1,200)));
      p.add(makeRoom);
      p.add(Box.createRigidArea(new Dimension(1,30)));
      p.add(joinRoom);
      p.add(Box.createRigidArea(new Dimension(1,30)));
      p.add(setting);
      break;

      case GameConst.MATCHING_GEST:
      isGest = true;
      break;

      case GameConst.MATCHING_HOST:
      System.out.println("maching now");
      isHost = true;
      p.updateUI();
      p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
      JLabel msg = new JLabel("just a moment");
      JButton start = new JButton("battle start");
      msg.setFont(new Font("Century", Font.BOLD, 100));
      start.setFont(new Font("Century", Font.BOLD, 100));
      start.addKeyListener(this);
      msg.setAlignmentX(0.5f);
      start.setAlignmentX(0.5f);
      p.add(msg);
      p.add(start);
      start.requestFocus();

      break;

      case GameConst.CHAR_SELECT:
      p.updateUI();
      p.setLayout(null);

      if(isHost == true){
        int x;
        int y;
        int count = 0;

        for(int i = 0;i < GameConst.TEAMCHARCTERS;i++){
          JLabel label = hostTeam[i].getLabel();
          x = GameConst.DISP_WIDTH*(i + 2)/6;
          y = GameConst.DISP_HEIGHT*1/6;
          label.setBounds(x,y,300,300);

          JButton selectChar = new JButton(String.valueOf(i));
          y = GameConst.DISP_HEIGHT*2/6;
          selectChar.setBounds(x,y,100,30);

          selectChar.addKeyListener(this);
          selectChar.addActionListener(this);
          selectChar.setActionCommand("s" + i);
          p.add(label);
          p.add(selectChar);

        }

        for(Charcter charReview : Charcter.hostCharcters){
          JLabel label = new JLabel(charReview.getName());
          x = GameConst.DISP_WIDTH*(count + 1)/8;
          y = GameConst.DISP_HEIGHT*4/6;
          label.setBounds(x,y,300,300);

          JButton selectChar = new JButton(label.getText());
          y = GameConst.DISP_HEIGHT*5/6;
          selectChar.setBounds(x,y,100,30);

          selectChar.addKeyListener(this);
          selectChar.addActionListener(this);
          selectChar.setActionCommand(count + "");

          p.add(label);
          p.add(selectChar);
          selectChar.requestFocus();
          count++;
        }



      }else{

      }
      break;
      case GameConst.BATTLE:
      break;
      case GameConst.TYPING:
      break;
      case GameConst.RESULT:
      break;
      default :
      System.out.println("error");
      System.exit(0);
      break;
    }

    getContentPane().add(p, BorderLayout.CENTER);

    repaint();
    System.out.println("repaint now");
  }

  public void actionPerformed(ActionEvent e){
    System.out.println("hello hoge");
    String cmd = e.getActionCommand();
    System.out.println(cmd);

    switch(viewPointer){
      case GameConst.TITLE:
      break;
      case GameConst.MATCHING_GEST:
      break;
      case GameConst.MATCHING_HOST:
      break;
      case GameConst.CHAR_SELECT:
      if(cmd.charAt(0) == 's'){
        selectChange = Integer.parseInt(cmd.charAt(1) + "");
      }else{
        if(isHost == true){
          int index = Integer.parseInt(cmd);
          selectHostChar(index);
          JLabel label = (JLabel)p.getComponent(selectChange * 2);
          label.setText(Charcter.hostCharcters.get(index).getName());
        }else{

        }
      }
      break;
      case GameConst.BATTLE:
      break;
      case GameConst.TYPING:
      break;
      case GameConst.RESULT:
      break;
      default :
      System.out.println("error");
      System.exit(0);
      break;

    }
  }

  public void keyPressed(KeyEvent e){
    //27 == Esc
    if(e.getKeyCode() == 27){
      System.exit(0);
    }
    System.out.println("hello world");
  }

  public void keyReleased(KeyEvent e){
    switch(viewPointer){
      case GameConst.TITLE:
      viewTrans(GameConst.MATCHING_HOST);
      break;
      case GameConst.MATCHING_GEST:
      viewTrans(GameConst.CHAR_SELECT);
      break;
      case GameConst.MATCHING_HOST:
      viewTrans(GameConst.CHAR_SELECT);
      break;
      case GameConst.CHAR_SELECT:
      break;
      case GameConst.BATTLE:
      break;
      case GameConst.TYPING:
      break;
      case GameConst.RESULT:
      break;
      default :
      System.out.println("error");
      System.exit(0);
      break;
    }
  }

  public void keyTyped(KeyEvent e){
  }
}

class GameConst{
  private GameConst(){
  }
  public static final int TEAMCHARCTERS = 3;
  public static final int BUFFSIZE = 2048;
  public static final int DISP_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
  public static final int DISP_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
  public static final int TITLE = 0;
  public static final int MATCHING_GEST = 1;
  public static final int MATCHING_HOST = 2;
  public static final int CHAR_SELECT = 3;
  public static final int BATTLE = 4;
  public static final int TYPING = 5;
  public static final int RESULT = 6;

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
  private boolean _isHostTeam;
  private boolean _isGestTeam;
  private JLabel label;
  private ImageIcon standingImg;
  private ImageIcon motion[][];


  private Charcter(){

  }

  public Charcter(String name,int hp,int atk,int def,int spd){
    _name = name;
    _hp = hp;
    _hpMax = _hp;
    _atk = atk;
    _def = def;
    _spd = spd;
    label = new JLabel(_name);
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

  public boolean isHostTeam(){
    return _isHostTeam;
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
    if(battleJoin.get(index).isLive() == false){
    }
  }

  public JLabel getLabel(){
    return label;
  }
}

class RealTime extends TimerTask {
  public void run(){
  }
}
