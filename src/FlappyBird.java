// FlappyBird.java

// Imports
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
// import javax.swing.Timer;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
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

    // Pipes (Top and Bottom)
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    // Game logic
    Bird bird;

    // Pipe moving speed
    int velocityX = -4;

    // Bird gravity
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    // Timers
    Timer gameLoop;
    Timer placePipesTimer;

    // Game over
    boolean isGameOver = false;

    // Score
    double score = 0;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // setBackground(Color.cyan);

        setFocusable(true);
        addKeyListener(this);

        // Load images
        backgroundImg = new ImageIcon(getClass().getResource("./assets/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./assets/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./assets/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./assets/bottompipe.png")).getImage();

        // Bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        // Pipes placement
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();

        // Game loop timer
        gameLoop = new Timer(1000 / 60, this); // 60 FPS (1 second / 60) = 16.6666666667 milliseconds per frame (rounded
                                               // to 17)
        gameLoop.start(); // Start the game loop
    }

    public void placePipes() {
        // Create a random y position
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2)); // Random number between 0
                                                                                             // and 512
        int openingSpace = boardHeight / 4;

        // Create a new array list of pipes
        Pipe topPipe = new Pipe(topPipeImg);

        // add the random y position to the pipe
        topPipe.y = randomPipeY;

        // Add the top pipe to the pipes array list
        pipes.add(topPipe);

        // Bottom pipe
        Pipe bottomPipe = new Pipe(bottomPipeImg);

        // Calculate the opening space between the pipes
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;

        // Add the bottom pipe to the pipes array list
        pipes.add(bottomPipe);
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

        // Draw the pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Draw the score
        g.setColor(Color.white);

        // 8 Bit Font
        g.setFont(new Font("Press Start 2P", Font.PLAIN, 20));

        if (isGameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }

    }

    public void move() {
        // Apply gravity to the bird's velocity
        velocityY += gravity;

        // Move the bird's Y position
        bird.y += velocityY;

        // Adding a ceiling to the bird
        bird.y = Math.max(bird.y, 0);

        // Pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            // if the bird has passed the right edge of the pipe then increase the score
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5;
            }

            if (colission(bird, pipe)) {
                isGameOver = true;
            }
        }

        // Game over
        if (bird.y > boardHeight) {
            isGameOver = true;
        }
    }

    public boolean colission(Bird a, Pipe b) {
        return a.x < b.x + b.width && // a's left edge is to the left of b's right edge
                a.x + a.width > b.x && // a's right edge is to the right of b's left edge
                a.y < b.y + b.height && // a's top edge is above b's bottom edge
                a.y + a.height > b.y; // a's bottom edge is below b's top edge
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update the position of the bird 60 times per second
        move();

        // Move the bird
        repaint();

        // As game is over
        if (isGameOver) {
            gameLoop.stop();
            placePipesTimer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // If the space key is pressed
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Move the bird up
            velocityY = -9;

            // If the game is over
            if (isGameOver) {
                // Reset the game
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                isGameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
