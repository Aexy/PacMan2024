package pacman.music;

import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.CountDownLatch;

public class InputSelfDefined extends JFrame{

    private final CountDownLatch latch;
    public boolean selfAudioPicked = false;
    String absolutePath;

    InputSelfDefined(CountDownLatch latch){
        this.latch = latch;
        //set up and center JFrame
        setTitle("Pick Your Own Music");
        setSize(400,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //MainJFrame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //Descriptions
        JPanel descriptions = descriptions();

        //Input
        JTextField absoluteInput = new JTextField(40);
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(absoluteInput);

        //submit Button
        JPanel submitPanel = submitButton();

        //Init
        mainPanel.add(descriptions);
        mainPanel.add(inputPanel);
        mainPanel.add(submitPanel);
        //mainPanel.add(goBack());

        add(mainPanel);
        setVisible(true);
        latch.countDown();
    }

    /**
     * Creates a submit button on the frame, which displays the updated
     * Absolute path of the Self Input Audio Choice, once pressed upon.
     * @return JPanel that contains the submit button.
     **/
    private JPanel submitButton(){
        JButton submitButton = new JButton("Submit a .wav file to be used");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //set Self Input Path
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV files", "wav");
                fileChooser.setFileFilter(filter);
                int returnVal = fileChooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    //get file and absolute path
                    File file = fileChooser.getSelectedFile();
                    absolutePath = file.getAbsolutePath();
                    //set absolute path and inform the user
                    AudioChoices.SELF_INPUT.setAbsolutePath(absolutePath);
                    JOptionPane.showMessageDialog(null, "The absolute path of Self Input has been updated to: " + AudioChoices.SELF_INPUT.getAbsolutePath() + " \n Game will start now");
                    dispose();
                    selfAudioPicked = true;
                }

                //play the audio
                AudioPlayer pa = new AudioPlayer(absolutePath, true, false);
                Clip clip = pa.getClip();
                clip.start();
                latch.countDown();
            }
        });

        //add submitButton and return
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(submitButton);
        return submitPanel;
    }

    /**
     * Creates three lines on the frame, which instructs the user on how
     * they can use their own music in the background.
     * @return JPanel that contains the descriptions.
     */
    private JPanel descriptions(){
        JPanel descriptions = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel infoLabel1 = new JLabel("To use your own song, do the following: ");
        JLabel infoLabel2 = new JLabel("1. Place your .wav file in the sources package within this project ");
        JLabel infoLabel3 = new JLabel("2. Enter your .wav file name below: ");

        descriptions.add(infoLabel1);
        descriptions.add(infoLabel2);
        descriptions.add(infoLabel3);

        return descriptions;
    }

}
