package pacman.control;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

class Ghost {
    //mapping
    private int x;
    private int y;
    private int dx;
    private int dy;

    private Image ghost;
    private Random rdm;


    public Ghost(int x, int y){
        this.x = x*Control.GRID_SIZE;
        this.y = y*Control.GRID_SIZE;
        this.dx = 0;
        this.dy = 0;
        this.rdm = new Random();
        initImg();
    }

    //load images
    private void initImg() {
        ghost = new ImageIcon("img/ghost.png").getImage();
    }

    /**
     * Randomly picks a number between 0-4, updates the dx and dy accordingly
     * 0 -> move right
     * 1 -> move left
     * 2 -> move up
     * 3 -> move down
     */
    public void randomDir(){
        int dir = rdm.nextInt(4);

        switch (dir){
            case 0:
                dx = Control.GRID_SIZE;
                dy = 0;
                break;
            case 1:
                dx = -Control.GRID_SIZE;
                dy = 0;
                break;
            case 2:
                dx = 0;
                dy = -Control.GRID_SIZE;
                break;
            case 3:
                dx = 0;
                dy = Control.GRID_SIZE;
                break;
        }
    }

    /**
     * Moves the ghosts:
     * -Checks if the obtained random is a valid location on the map
     * -Updates the x and y coordinates accordingly
     */
    public void move(){
        //x + dx must be bigger than 0 --> walls at 0
        //AND x + dx must be smaller than grid size*pixel

        //y + dx must be bigger than 0 --> walls at 0
        //AND y + dx must be smaller than grid size*pixel
        randomDir();
        int findX = x +dx;
        int findY = y +dy;

        if(validMove(findX,findY)){
            x+=dx;
            y+= dy;
        }
    }

    /**
     *
     * @param gX, the new x coordinate
     * @param gY, the new y coordinate
     * @return (gX,gY) is a valid location
     */
    private boolean validMove(int gX, int gY){
        int posX = gX/Control.GRID_SIZE;
        int posY = gY/Control.GRID_SIZE;

        return posX > 0
                && posX < (Control.map[0].length)
                && posY > 0
                && posY < (Control.map.length)
                && (Control.map[posY][posX] != 0)
                ;

    }

    /**
     * Draws the ghost at (x,y)
     * @param g, the Graphics to be updated
     */
    public void draw(Graphics g){
        g.drawImage(ghost, x, y, Control.GRID_SIZE,Control.GRID_SIZE,null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
