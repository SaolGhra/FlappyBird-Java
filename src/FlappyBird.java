// FlappyBird.java

// Imports
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener {
    int boardWidth = 360;
    int boardHeight = 640;

    // Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Add Bird
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // Game logic
    Bird bird;

    int velocityY = -6;
    int gravity = 1;

    Timer gameLoop;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.cyan);

        // Load images
        backgroundImg = new ImageIcon(getClass().getResource("./assets/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./assets/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./assets/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./assets/bottompipe.png")).getImage();

        // Bird
        bird = new Bird(birdImg);

        // Game loop timer
        gameLoop = new Timer(1000 / 60, this); // 60 FPS (1 second / 60) = 16.6666666667 milliseconds per frame (rounded
                                               // to 17)
        gameLoop.start(); // Start the game loop
    }

    // Paint the game
    public void paintComponent(Graphics g) {
        // Call the super class
        super.paintComponent(g);

        // Draw the game objects using the draw method
        draw(g);
    }

    // Draw the game objects on the screen
    public void draw(Graphics g) {
        // System.out.println("Drawing");

        // Draw the background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // Draw the bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }

    public void move() {
        // Apply gravity to the bird's velocity
        velocityY += gravity;

        // Move the bird's Y position
        bird.y += velocityY;

        // Adding a ceiling to the bird
        bird.y = Math.max(bird.y, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update the position of the bird 60 times per second
        move();

        // Move the bird
        repaint();
    }
}
