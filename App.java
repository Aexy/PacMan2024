import PacmanInit.*;
import javax.swing.*;


public class App {

    public static void main(String[] args) {

        //Music Section
//        MusicChoice game = new MusicChoice();
//        if(game.playAudio() != null){
//            Clip song = game.playAudio();
//            song.setMicrosecondPosition(0);
//            song.loop(Clip.LOOP_CONTINUOUSLY);
//        }

        SwingUtilities.invokeLater(() -> {
            Control gp = new Control();
            initPacman(gp);
        });
    }

    public static void initPacman(Control gp) {
        JFrame frame = new JFrame("Pac-Man");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gp);
        frame.setFocusable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
