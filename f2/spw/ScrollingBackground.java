package f2.spw;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
 
public class ScrollingBackground extends Canvas implements Runnable {
 
    // Two copies of the background image to scroll
    protected Background bgOne;
    protected Background bgTwo;
 
    protected BufferedImage bg;
 
    public ScrollingBackground() {
        bgOne = new Background();
        bgTwo = new Background(0, bgOne.getImageHeight());
 
        new Thread(this).start();
        setVisible(true);
    }
 
    @Override
    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(5);
                repaint();
            }
        }
        catch (Exception e) {}
    }
 
   
    public void update(Graphics g, GamePanel gp) {
        gp.paint(g);
    }            
 
}