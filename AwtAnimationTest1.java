import java.awt.*;

public class AwtAnimationTest1 extends Canvas
			implements Runnable {

	private final int MAX_IMAGE = 7;
        private final int WIDTH = 500;
        private final int HIGHT = 400;
        private Image[] image = new Image[MAX_IMAGE];
        private MediaTracker tracker;

	private Graphics offg;
	private Image offImage;
	private int x = 0;
	private int y = 100;
	private int imageNum = 0;
	private int imageCount = 1;
	private int moveCount = 20;

	public AwtAnimationTest1() {
		setSize(WIDTH, HIGHT);
		 setBackground(Color.black);

                Toolkit tk = Toolkit.getDefaultToolkit();

                tracker = new MediaTracker(this);

		 for(int i=0; i<MAX_IMAGE; i++) {
                        image[i] = tk.getImage("char" + i + ".gif");
                        tracker.addImage(image[i], i);
                }

                try {
                        tracker.waitForAll();
                }
                catch (InterruptedException e) {
                        System.err.println("tracker error");
                }
        }

	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.err.println(ex);
			}

			repaint();
		}
	}


	public void update (Graphics g) {

		offg.setColor(Color.black);
		offg.fillRect(0, 0, WIDTH, HIGHT);

		if (imageNum >= (MAX_IMAGE-1)) {
			imageCount = -1;
		}
		else if (imageNum <= 0) {
			imageCount = 1;
		}
		imageNum+=imageCount;

		if (x+image[0].getWidth(this) >= WIDTH) {
			moveCount = -10;
		}
		else if (x <= 0 ) {
			moveCount = 10;
		}
		x+=moveCount;

		offg.drawImage(image[imageNum], x, y, this);

		 Font font = new Font("Serif", Font.ITALIC, 48);
                offg.setFont(font);
		offg.setColor(Color.yellow);
                offg.drawString("sample is showed", 10, 200);

		g.drawImage(offImage, 0, 0, this);
	}

	public void paint(Graphics g) {

		if (offg == null) {

			offImage = createImage(getSize().width, getSize().height);

			offg = offImage.getGraphics();
		}

		g.drawImage(image[imageNum], x, y, this);

		Font font = new Font("Serif", Font.ITALIC, 48);
		g.setFont(font);
		g.setColor(Color.yellow);
		g.drawString("sample is showed", 10, 200);
	}

	public static void main(String[] args) {

		AwtAnimationTest1 canvas = new AwtAnimationTest1();
		Frame frame = new Frame("Animation1");
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		new Thread(canvas).start();
	}
}
