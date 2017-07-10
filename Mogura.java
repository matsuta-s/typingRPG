//package mogura;

import java.awt.*;
import java.awt.image.*;
import javax.swing.JFrame;
import javax.imageio.*;
import java.net.*;
import java.io.*;

public class Mogura extends JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage moguraImage;

	public Mogura() {
		try {
			moguraImage = ImageIO.read(new File("sample4fps.gif"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.getContentPane().setLayout(null);
		this.getContentPane().add(new AnimatedCanvas(moguraImage));
		this.setBounds(0 ,0, 1000, 1000);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Mogura();
	}
}

class ImageCanvas extends Canvas
{
	private static final long serialVersionUID = 1L;
	Image presenImage;
	int w, h;

	public ImageCanvas(Image img) {
		presenImage = img;
		w = presenImage.getWidth(this);
		h = presenImage.getHeight(this);
		this.setSize(w, h);
	}
	public void paint(Graphics g) {
		g.drawImage(presenImage, 0, 0, this);
	}
}

class AnimatedCanvas extends ImageCanvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	Thread myTh;
	Color backColor = Color.pink;
	float visibleArea;

	public AnimatedCanvas(Image img)  {
		super(img);
		myTh = new Thread(this);
		myTh.start();
	}

	public void run() {
		Graphics cg = this.getGraphics();
		cg.setColor(backColor);
		for(visibleArea = 0; visibleArea <= 1.0; visibleArea += 0.1)  {
			cg.fillRect(0,0,w,h);
			cg.drawImage(presenImage, 0, (int)(h*(1-visibleArea)), this);
			try  {
				Thread.sleep(1000);
			}
			catch(Exception e)  {
				System.out.println(e.getMessage());
			}
		}
	}

	public void paint(Graphics g) {}
}
