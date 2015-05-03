package f2.spw;

import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Color;

public class Main {

    private JPanel cards;
    private GamePanel gamePanel;
    private GameEngine gameEngine;
    private JButton btnStart;

    private void createAndShowGUI() {
        //Create and set up the window.
        final JFrame frame = new JFrame("I'm not sick");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        cards = new JPanel();
        cards.setLayout(new java.awt.CardLayout());

    
        JPanel panelHome = new JPanel();

        SpaceShip v = new SpaceShip(130, 500, 70, 70);
        gamePanel = new GamePanel();
        gameEngine = new GameEngine(gamePanel, v);

        gamePanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                doWhenShownGamePanel(); 
            }
        });

        btnStart = new JButton(new ImageIcon("f2/spw/image/startbutton.png"));
        btnStart.setBorder(BorderFactory.createEmptyBorder());

        // btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchCard("game"); 
            }

        });

        JLabel bg = new JLabel();
	 	bg.setIcon(new ImageIcon("f2/spw/image/introbg.png"));

        panelHome.setLayout(new java.awt.GridBagLayout());
        // panelHome.add(bg);
        panelHome.setBackground(new Color (84, 201, 198));
        panelHome.add(btnStart, new java.awt.GridBagConstraints());
        

        cards.add(panelHome, "home");
        cards.add(gamePanel, "game");

        frame.getContentPane().add(cards, java.awt.BorderLayout.CENTER);
        
        frame.setSize(400, 650);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void switchCard(String cardName) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, cardName);
    }

    private void doWhenShownGamePanel() {
        gamePanel.addKeyListener(gameEngine);
        gamePanel.requestFocus();
        gameEngine.start();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.createAndShowGUI();
    }
}
