import ChooseYourMusic.MusicChoice;
import PacmanInit.*;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.CountDownLatch;


public class App {

    public static void main(String[] args) {

        //Init latch
        CountDownLatch latch = new CountDownLatch(1);

        //Music Runnable
        Runnable music = new Runnable() {
            @Override
            public void run() {
                MusicChoice musicChoice = new MusicChoice(latch);
            }
        };


        //Game Runnable
        Runnable game = new Runnable() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    Control gp = new Control();
                    initPacman(gp);
                });
            }
        };

        //Start Music
        Thread musicThread = new Thread(music);
        musicThread.start();

        //wait for music thread to end
        try {
            while(musicThread.isAlive()) {
                latch.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Start Game
        Thread gameThread = new Thread(game);
        gameThread.start();


    }

    public static void initPacman(Control gp) {
        JFrame frame = new JFrame("Pac-Man");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gp);

        //focus
        frame.setFocusable(true);
        frame.requestFocus();
        frame.requestFocusInWindow();

        //sizing
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //update the frame after every key press
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(!gp.checkGameOver()) {
                    gp.controlKeyPressed(e);
                    frame.repaint();
                }
            }
        });
    }

}
