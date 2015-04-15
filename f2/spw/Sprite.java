package f2.spw;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Sprite {
	int x;
	int y;
	int width;
	int height;

	private Image image;
	
	public Sprite(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	abstract public void draw(Graphics2D g);

	// To set new Image 
	public void setImage(String imageAddress){
		try {
			File sourceImage = new File(imageAddress);
			image = ImageIO.read(sourceImage);
		}
		catch (IOException e) {
         	e.printStackTrace();
        }
	}

	public Image getImage(){
		return image;
	}
	
	public Double getRectangle() {
		return new Rectangle2D.Double(x, y, width, height);
	}
}
