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

	public static final int WIDTH = 400;
	public static final int HEIGHT = 600;

	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	Color backgroundColor = new Color (160, 160, 160);
	Color bonusColor = new Color (64, 64, 64);

	private Image heart;
	private Image needle;
	private Image bigNeedle;
	private Image pill;

	public GamePanel() {
		bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(backgroundColor);

		try {
			heart = ImageIO.read(new File("f2/spw/image/smallredheart.png"));

			needle = ImageIO.read(new File("f2/spw/image/thpinkneedle.gif"));
			bigNeedle = ImageIO.read(new File("f2/spw/image/bigneedle.gif"));

			pill = ImageIO.read(new File("f2/spw/image/pill.png"));
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
		// if (reporter != null) 
		big.setBackground(backgroundColor);
		big.drawString(String.format("%08d", reporter.getScore()), 170, 20);
		big.drawString(String.format("%01d", reporter.getHeartScore()), 350, 20);

		///////////// Needle
		for(int i = 0; i < reporter.getNumOfNeedle(); i++){
			big.drawImage(needle, 10 + (20 * i), 5, null);
			big.drawString(String.format("%01d", reporter.getTimePerOneNeedle()), 20, 50);
		}
		// when needle == 3 -> you got BIGNEEDLE
		if(reporter.getNumOfNeedle() == 3){
			if(!(reporter.getEnableGenerateBulletForBigNeedle())){
				big.drawImage(bigNeedle, 10, 60, 80, 20, null);
				big.drawString("Press B", 40, 100);
			}	
			big.drawString(String.format("%01d", reporter.getTimeForBigNeedle()), 10, 110);		
		}

		///////////// When enter Sicktime
		if (isEnableBonusTime) {
			big.setBackground(bonusColor);
			if(reporter.getCountBonusTime() > 15){
				big.drawString("Sick Time", WIDTH / 2 - 33, HEIGHT / 2);
			}
			else if(reporter.getCountBonusTime() < 15){
				big.drawString("Collect the PILLS", WIDTH / 2 - 50, HEIGHT / 2);
			}
			big.drawString(String.format("%01d",reporter.getCountBonusTime()), WIDTH / 2 - 20, 320);
			big.drawImage(pill, WIDTH / 2 - 30, 330, null);
			big.drawString(String.format("%01d", reporter.getPillCount()), WIDTH / 2, 338);
		}
			
		
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	private boolean isEnableBonusTime = false;

	public void setEnableBonusTime(boolean isEnableBonusTime){
		this.isEnableBonusTime = isEnableBonusTime;
	}

	public void showDamage(GameReporter reporter){		
		big.clearRect(reporter.getCurrentXOfSS() - 10, reporter.getCurrentYOfSS() - 10, 50, 10);
		big.setColor(Color.WHITE);		
		big.drawString(String.format("%02d", reporter.getDamage()), reporter.getCurrentXOfSS() + 10, reporter.getCurrentYOfSS() - 5);
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
