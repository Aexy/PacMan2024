package PacmanInit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Control extends JPanel implements ActionListener {

    //game map
    public static boolean[][] map = {
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
    };

    //images
    private Image blank;
    private Image wall;
    private Image food;
    public static int GRID_SIZE = 15;
    public final int PIXEL_SIZE = 20;
    public int score;
    public boolean gameOver;

    //control n of ghosts
    public Ghost[] ghosts;
    public Pacman pacman;
    public Timer timer;


    public Control() {
        initImg();
        //create pacman
        pacman = new Pacman(GRID_SIZE / 2, GRID_SIZE / 2);
        System.out.println("init pac" + pacman.getX() + "," + pacman.getY());
//PROBLEM        //create ghosts
        ghosts = new Ghost[3];
        for(int i = 0; i<3; i++){
            ghosts[i] = new Ghost(i*4, i*4);
        }
        setPreferredSize(new Dimension(GRID_SIZE * PIXEL_SIZE, GRID_SIZE * PIXEL_SIZE));
        setBackground(Color.BLACK);
        addKeyListener(new TAdapter());
        timer = new Timer(100, this);
        timer.start();
        score = 0;
        gameOver = false;
    }

    private void initImg() {
        food = new ImageIcon("img/food.png").getImage();
        blank = new ImageIcon("img/blank.png").getImage();
        wall = new ImageIcon("img/wall.png").getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                int drawX = x * PIXEL_SIZE;
                int drawY = y * PIXEL_SIZE;
                if(x == 0 || y == 0 || x == GRID_SIZE - 1 || y == GRID_SIZE - 1) {
                    g.drawImage(wall, drawX, drawY, PIXEL_SIZE, PIXEL_SIZE ,null);
                }else if (map[y][x]) {
                    g.drawImage(food, drawX, drawY, PIXEL_SIZE, PIXEL_SIZE, this);
                } else {
                    g.drawImage(blank, drawX, drawY, PIXEL_SIZE, PIXEL_SIZE, this);
                }
            }
        }
        //draw pacman
        pacman.draw(g);

        //draw ghosts
        for (Ghost ghost : ghosts) {
            ghost.draw(g);
        }
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 10);

        if (gameOver) {
            g.setColor(new Color(5,180,80));
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", 50, 150);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver){
            pacman.move();
            checkCollisions();
            for(Ghost ghost : ghosts){
                ghost.move();
            }
            checkGameOver();
            repaint();
        }
        repaint();
    }

    private void checkCollisions() {
        int x = pacman.getX() / PIXEL_SIZE;
        int y = pacman.getY() / PIXEL_SIZE;

        if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE && map[y][x]) {
            map[y][x] = false;
            score++;
        }
    }

    private void checkGameOver() {
        if(timer.getDelay() > 0){
            return;
        }

        int pacX = pacman.getX() / PIXEL_SIZE;
        int pacY = pacman.getY() / PIXEL_SIZE;

        for (Ghost ghost : ghosts) {
            int ghostX = ghost.getX() / PIXEL_SIZE;
            int ghostY = ghost.getY() / PIXEL_SIZE;
            if (pacX == ghostX && pacY == ghostY) {
                gameOver = true;
                timer.stop();
            }
        }
    }

    protected class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            pacman.keyPressed(e);
        }

    }
}
