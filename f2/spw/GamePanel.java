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
	private Image needle;

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(backgroundColor);

		try {
			File sourceimage = new File("f2/spw/image/smallredheart.png");
			heart = ImageIO.read(sourceimage);

			File sourceimage2 = new File("f2/spw/image/thpinkneedle.gif");
			needle = ImageIO.read(sourceimage2);
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

		if(reporter.getNumOfNeedle() == 1) {
			big.drawImage(needle, 10, 5, null);
		}
		else if (reporter.getNumOfNeedle() == 2) {
			big.drawImage(needle, 10, 5, null);
			big.drawImage(needle, 30, 5, null);
		}
		else if (reporter.getNumOfNeedle() == 3) {
			big.drawImage(needle, 10, 5, null);
			big.drawImage(needle, 30, 5, null);
			big.drawImage(needle, 50, 5, null);
		}
		
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	public void showMessage(String message){
		
		big.clearRect(0, 0, 400, 600);
		big.setColor(Color.WHITE);		
		big.drawString(message, 150, 250);
		
		repaint();
	}

	public void showDamage(GameReporter reporter){
		
		big.clearRect(reporter.getCurrentXOfSS() - 10, reporter.getCurrentYOfSS() - 10, 50, 10);
		big.setColor(Color.WHITE);		
		big.drawString(String.format("%02d", reporter.getDamage()), reporter.getCurrentXOfSS() + 10, reporter.getCurrentYOfSS());
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
