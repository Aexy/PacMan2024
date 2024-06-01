package PacmanInit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Pacman {

    public int x;
    private int y;
    private int dx;
    private int dy;
    private final int PIXEL_SIZE = 15;
    private Image down;
    private Image up;
    private Image left;
    private Image right;
    private Image begin;
    private Image curr;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        initImg();
    }

    private void initImg() {
        down = new ImageIcon("img/pacmanD.png").getImage();
        up = new ImageIcon("img/pacmanU.png").getImage();
        left = new ImageIcon("img/pacmanL.png").getImage();
        right = new ImageIcon("img/pacmanR.png").getImage();
        begin = new ImageIcon("img/pacmanC.png").getImage();

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -PIXEL_SIZE;
            dy = 0;
            curr = left;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = PIXEL_SIZE;
            dy = 0;
            curr = right;
        }

        if (key == KeyEvent.VK_UP) {
            dx = 0;
            dy = -PIXEL_SIZE;
            curr = up;
        }

        if (key == KeyEvent.VK_DOWN) {
            dx = 0;
            dy = PIXEL_SIZE;
            curr = down;
        }
    }


    public void move() {
        int newX = x + dx;
        int newY = y + dy;

        if(validMove(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    public boolean validMove(int newX, int newY){
        return newX >= 0
                && newX < Control.GRID_SIZE * PIXEL_SIZE
                && newY >= 0
                && newY < Control.GRID_SIZE * PIXEL_SIZE
                && !Control.map[newY / PIXEL_SIZE][newX / PIXEL_SIZE];
    }

    public void draw(Graphics g) {
        if(curr != null) {
            g.drawImage(curr,x,y,PIXEL_SIZE,PIXEL_SIZE, null);
        }
        g.drawImage(begin,x,y,PIXEL_SIZE,PIXEL_SIZE,null);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
