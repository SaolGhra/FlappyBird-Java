// FlappyBird.java

// Imports
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel {
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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Draw the background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // Draw the bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }
}
