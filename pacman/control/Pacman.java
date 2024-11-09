package pacman.control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class Pacman {

    private int x;
    private int y;
    private int dx;
    private int dy;
    private boolean started;

    private Image down;
    private Image up;
    private Image left;
    private Image right;
    private Image begin;
    private Image curr;

    public Pacman(int x, int y) {
        this.x = x*Control.GRID_SIZE;
        this.y = y*Control.GRID_SIZE;
        this.dx = 0;
        this.dy = 0;
        started = false;
        initImg();
        curr = begin;
    }

    private void initImg() {
        down = new ImageIcon("img/pacmanD.png").getImage();
        up = new ImageIcon("img/pacmanU.png").getImage();
        left = new ImageIcon("img/pacmanL.png").getImage();
        right = new ImageIcon("img/pacmanR.png").getImage();
        begin = new ImageIcon("img/pacmanC.png").getImage();

    }

    /**
     * Gets the Pressed key and updates the image that should be presented accordingly
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(!started){
            started = true;
        }
        switch (key) {
            case KeyEvent.VK_LEFT:
                dx = -Control.GRID_SIZE;
                dy = 0;
                curr = left;
                break;
            case KeyEvent.VK_RIGHT:
                dx = Control.GRID_SIZE;
                dy = 0;
                curr = right;
                break;
            case KeyEvent.VK_UP:
                dx = 0;
                dy = -Control.GRID_SIZE;
                curr = up;
                break;
            case KeyEvent.VK_DOWN:
                dx = 0;
                dy = Control.GRID_SIZE;
                curr = down;
                break;
        }
    }


    /**
     * Moves the pacman
     */
    public void move() {
        if (!started) {
            return;
        }

        int newX = x + dx;
        int newY = y + dy;

        if(validMove(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    /**
     * Checks the validity of the position in the map
     * @param newX the new X position
     * @param newY the new Y position
     * @return (x,y) is a valid location on the map
     */
    private boolean validMove(int newX, int newY){
        int posX = newX/Control.GRID_SIZE;
        int posY = newY/Control.GRID_SIZE;

        return posX > 0
                && posX < (Control.map[0].length)
                && posY > 0
                && posY < (Control.map.length)
                && Control.map[posY][posX] != 0;
    }

    /**
     * Draw the pacman at (x,y)
     * @param g Graphics
     */
    public void draw(Graphics g) {
        g.drawImage(curr, x, y, Control.GRID_SIZE,Control.GRID_SIZE, null);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isStarted(){
        return started;
    }
}
