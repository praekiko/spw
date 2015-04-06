package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;
	private BufferedImage bi2;	

	Graphics2D big;
	Graphics2D big2;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	Color backgroundColor = new Color (160, 160, 160);

	private Image heart;

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(backgroundColor);

		bi2 = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big2 = (Graphics2D) bi2.getGraphics();
		big2.setBackground(backgroundColor);

		try {
			File sourceimage = new File("f2/spw/image/heart.gif");
			heart = ImageIO.read(sourceimage);
		}
		catch (IOException e) {
         	e.printStackTrace();
        }
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		// heart image
		big.drawImage(heart, 325, 9, null);
		
		big.setColor(Color.WHITE);		
		big.drawString(String.format("%08d", reporter.getScore()), 170, 20);
		big.drawString(String.format("%01d", reporter.getHeartScore()), 350, 20);
		
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}



	public void showDamage(GameReporter reporter){
		System.out.println("showDamage");
		// System.out.println(reporter.getDamage() + " " + reporter.getCurrentXOfSS() + " " + reporter.getCurrentYOfSS());
		// big2.clearRect(0, 0, 400, 600);
		
		big2.setColor(Color.WHITE);		
		big2.drawString(String.format("%02d", reporter.getDamage()), reporter.getCurrentXOfSS(), reporter.getCurrentYOfSS());

		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
		g2d.drawImage(bi2, null, 0, 0);
	}

}
