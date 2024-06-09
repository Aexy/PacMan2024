package PacmanInit;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Control extends JPanel implements ActionListener {

    //game map
    //0 = wall; 1 = points; 2 = blank
    public static int[][] map = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0},
            {0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    public static int GRID_SIZE = map.length-2;
    public int score;
    public boolean gameOver;
    public int maxScore = 0;
    public String endText;
    public Random rand = new Random();

    //images
    private Image blank;
    private Image wall;
    private Image food;

    //control n of ghosts
    private final Ghost[] ghosts;
    private final Pacman pacman;
    public Timer timer;


    public Control() {
        //initialize images
        initImg();
        //create pacman
        this.pacman = new Pacman(GRID_SIZE/2, GRID_SIZE/ 2);
        //create ghosts
        this.ghosts = new Ghost[3];
        for(int i = 0; i<ghosts.length; i++){
            int signed = rand.nextInt(GRID_SIZE-1);

            //ghost placement
            while(signed == GRID_SIZE/2 || map[signed][signed] == 0){
                signed = rand.nextInt(GRID_SIZE);
            }
            ghosts[i] = new Ghost(signed, signed);
        }
        //set window
        setPreferredSize(new Dimension((GRID_SIZE * 20), (GRID_SIZE * 20)));
        setFocusable(true);
        //set key
        this.timer = new Timer(200, this);
        this.timer.start();
        //set modifiers
        this.maxScore = countMaxScore();
        this.score = 0;
        this.gameOver = false;
    }

    //init img
    private void initImg() {
        food = new ImageIcon("img/food.png").getImage();
        blank = new ImageIcon("img/blank.png").getImage();
        wall = new ImageIcon("img/wall.png").getImage();
    }

    /**
     * Paints the window in the given order:
     * -map (walls,food,blanks)
     * -pacman
     * -ghosts
     * -score
     * -game over check: if its over, update the g accordingly
     * @param g the Graphics object to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                int drawX = x * GRID_SIZE;
                int drawY = y * GRID_SIZE;
                if(map[y][x] == 0) {
                    g.drawImage(wall, drawX, drawY, GRID_SIZE, GRID_SIZE,this);
                } else if (map[y][x] ==1){
                    g.drawImage(food, drawX, drawY, GRID_SIZE, GRID_SIZE, this);
                } else if (map[y][x] == 2){
                    g.drawImage(blank, drawX, drawY, GRID_SIZE, GRID_SIZE, this);
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

        //game is over
        if (checkGameOver() || checkCollisions()) {
            g.setColor(Color.black);
            g.fillRect((GRID_SIZE*GRID_SIZE)/4, (GRID_SIZE*GRID_SIZE)/2-GRID_SIZE, 10*endText.length(), GRID_SIZE);
            g.setColor(new Color(97, 38, 231));
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString(endText, (GRID_SIZE*GRID_SIZE)/4,(GRID_SIZE*GRID_SIZE)/2 );
        }
    }

    /**
     * At each step repaint the graphics
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver){
            if(pacman.isStarted()){
                pacman.move();
                for(Ghost ghost : ghosts){
                    ghost.move();
                }
            }
            checkCollisions();
            checkGameOver();
            repaint();
        }
    }

    /**
     * Checks if pacman and ghosts have collided
     * Checks if the maxScore has been reached
     * Otherwise updates the map to '2' -> blank
     * @return collision occurred
     */
    private boolean checkCollisions() {
        int x = pacman.getX()/GRID_SIZE ;
        int y = pacman.getY()/GRID_SIZE ;

        for(Ghost ghost : ghosts){
            if(ghost.getX()/GRID_SIZE == x && ghost.getY()/GRID_SIZE == y){
                gameOver = true;
                return true;
            }
        }

        if(score == maxScore){
            gameOver = true;
            endText = "You Win!: " + score;
            timer.stop();
            return true;
        }

        if (x >= 0 && x < map[0].length && y >= 0 && y < map.length && map[y][x] == 1) {
            map[y][x] = 2;
            score++;
            return false;
        }
        return false;
    }

    /**
     * Checks if the game is over
     * @return game is over
     */
    public boolean checkGameOver() {

        if(checkCollisions()){  //if collision occurs --> game end
            gameOver = true;
            endText = "Game Over, Score: " + score;
            timer.stop();
            return true;
        }
        return false;
    }

    /**
     * Called once at the beginning to count max score that can be reached
     * @return max Score
     */
    private int countMaxScore(){
        for(int[] b : map){
            for(int i : b){
                if(i == 1){
                    maxScore++;
                }
            }
        }
        return maxScore;
    }

    /**
     * At each Key press, directs it to pacman and moves ghosts
     * @param e, The key pressed
     */
    public void controlKeyPressed(KeyEvent e) {
        if(checkCollisions()){
            return;
        }

        pacman.keyPressed(e);
        pacman.move();

        for(Ghost ghost : ghosts){
            ghost.move();
        }
    }


}
