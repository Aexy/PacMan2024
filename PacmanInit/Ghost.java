package PacmanInit;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private Image ghost;
    private Random rdm;
    private final int PIXEL_SIZE = 20;

    public Ghost(int x, int y){
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.rdm = new Random();
        initImg();
    }

    private void initImg() {
        ghost = new ImageIcon("img/ghost.png").getImage();
    }

    private void randomDir(){
        int dir = rdm.nextInt(4);
        //0 -> move right
        //1 -> move left
        //2 -> move up
        //3 -> move down
        switch (dir){
            case 0:
                dx = PIXEL_SIZE;
                dy = 0;
                break;
            case 1:
                dx = -PIXEL_SIZE;
                dy = 0;
                break;
            case 2:
                dx = 0;
                dy = PIXEL_SIZE;
                break;
            case 3:
                dx = 0;
                dy = -PIXEL_SIZE;
                break;
        }
    }

    public void move(){
        //x + dx must be bigger than 1 --> walls at 0
        //AND x + dx must be smaller than grid size*pixel

        //y + dx must be bigger than 1 --> walls at 0
        //AND y + dx must be smaller than grid size*pixel
        if ((x + dx >= 1 && x + dx < PIXEL_SIZE * Control.GRID_SIZE) &&
                (y + dy >= 1 && y + dy < PIXEL_SIZE * Control.GRID_SIZE) &&
                !Control.map[(y + dy) / PIXEL_SIZE][(x + dx) / PIXEL_SIZE]) {
            x += dx;
            y += dy;
        } else {
            randomDir();
        }
    }

    public void draw(Graphics g){
        g.drawImage(ghost, x, y, PIXEL_SIZE,PIXEL_SIZE,null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
