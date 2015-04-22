package f2.spw;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import java.awt.Component;

public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 650);
		frame.getContentPane().setLayout(new BorderLayout());
		
		SpaceShip v = new SpaceShip(180, 550, 30, 30);
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v);
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);

		ScrollingBackground bg = new ScrollingBackground();
        // ((Component)bg).setFocusable(true);
        // frame.getContentPane().add(bg);

		frame.setVisible(true);
		
		engine.start();
	}
}
