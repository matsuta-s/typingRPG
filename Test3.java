import java.awt.*;

public class Test3 extends Canvas implements Runnable{

  private final int MAX_IMAGE = 28;
  private static int WIDTH;
  private static int HIGHT;
  private Image[] image = new Image[MAX_IMAGE];
  private MediaTracker tracker;

  private Graphics offg;
  private Image offImage;
  private int x = 0;
  private int y = 0;
  private int imageNum = 0;
  private int imageCount = 1;

  public Test3() {
    java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
    java.awt.DisplayMode displayMode = env.getDefaultScreenDevice().getDisplayMode();
    WIDTH = (int)(displayMode.getWidth() / 1.3);
    HIGHT = (int)(displayMode.getHeight() / 1.3);
    setSize(WIDTH,HIGHT);
    setBackground(Color.white);

    Toolkit tk = Toolkit.getDefaultToolkit();

    tracker = new MediaTracker(this);

    for(int i = 0;i < MAX_IMAGE;i++){
      image[i] = tk.getImage("yuusyaSample/swordA-x_" + String.format("%05d",i) + ".png");
      tracker.addImage(image[i],i);
    }

    try {
      tracker.waitForAll();
    }catch(InterruptedException e){
      System.err.println("tracker error");
    }
  }

  public void run() {
    while(true){
      try {
        Thread.sleep(100);
      }catch(InterruptedException e){
        System.err.println(e);
      }

      repaint();
    }
  }

  public void update(Graphics g){
    System.out.println(x + "," +y);
    offg.setColor(Color.pink);
    offg.fillRect(0,0,WIDTH,HIGHT);
    imageNum++;
    imageNum%=MAX_IMAGE;
    offg.drawImage(image[imageNum],x,y,this);

    g.drawImage(offImage,0,0,this);
  }

  public void paint(Graphics g) {
    if(offg == null){
      offImage = createImage(getSize().width,getSize().height);
      System.out.println(getSize().width + "," + getSize().height);
      offg = offImage.getGraphics();
    }

    g.drawImage(image[imageNum],x,y,this);

  }

  public static void main(String[] args){
    Test3 canvas = new Test3();
    Frame frame = new Frame("Animation1");
    frame.add(canvas);
    frame.pack();
    frame.setVisible(true);

    new Thread(canvas).start();
  }
}
