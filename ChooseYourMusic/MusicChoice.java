package ChooseYourMusic;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MusicChoice extends JFrame {

    private final JComboBox<String> dropdownMenu;
    public AudioChoices audioChosen;
    public boolean audioPicked = false;

    public MusicChoice() {
        // Set up JFrame
        setTitle("Welcome to Pacman");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the JFrame

        // Create mainpanel
        JPanel mainPanel = new JPanel();
        JLabel descriptionLabel = new JLabel("Please Pick a Song Type From the Drop Down Menu Below:");
        mainPanel.setLayout(new GridLayout(5 , 1));
        mainPanel.add(descriptionLabel);

        // Dropdown Menu
        JLabel dropdownLabel = new JLabel("Select an option:");
        String[] options = {"NO MUSIC", "PACMAN THEME", "SURPRISE THEME", "AUDACITY THEME", "SELF DEFINED"};
        dropdownMenu = new JComboBox<>(options);
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dropdownPanel.add(dropdownLabel);
        dropdownPanel.add(dropdownMenu);

        // Submit Button
        JPanel submitPanel = submitButton();

        // Add components to main panel
        mainPanel.add(dropdownPanel);
        mainPanel.add(submitPanel);

        // Add main panel to frame
        add(mainPanel);
        setVisible(true);
    }

    /**
     * Creates the submit button.
     * Once the choice has been made, saves the choice.
     * @return JPanel with submitButton that closes after 5 seconds.
     */
    private JPanel submitButton() {
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle submit button click
                int optionChosen = dropdownMenu.getSelectedIndex();
                // Handle self define absolute path
                if(optionChosen == AudioChoices.SELF_INPUT.getNumber()){
                    //Pop Up
                    new InputSelfDefined();

                }else{
                    //get AudioChoice
                    audioChosen = AudioChoices.getAudioChoiceByNumber(optionChosen);
                    //Display Message
                    JOptionPane.showMessageDialog(null,"Chosen number is: " + audioChosen.getNumber() + " with the absolute path " +audioChosen.getAbsolutePath());
                    //clear screen
                    dispose();
                }

            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        return buttonPanel;
    }

    /**
     * Calls pickAudio() and uses the obtained
     * AudioInputStream to create a clip.
     * @return Clip that has the chosen game song.
     */
    private Clip playAudio(){
        AudioInputStream chosen = pickAudio(audioChosen.getAbsolutePath());
        Clip clip = null;

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            JOptionPane.showMessageDialog(null, "LineUnavailableException, be sure to not be playing another audio");
            dispose();
            new MusicChoice();
        }

        try {
            assert clip != null;
            clip.open(chosen);
        } catch (LineUnavailableException e) {
            JOptionPane.showMessageDialog(null, "LineUnavailableException, be sure to not be playing another audio");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IOException, try restarting");
        }

        return clip;
    }
    private AudioInputStream pickAudio(String absolutePath){
        AudioInputStream inputAudio = null;

        //Keep asking for input until a valid audio path is provided
        while(!audioPicked){
            try {
                inputAudio = AudioSystem.getAudioInputStream(new File(absolutePath));
                audioPicked = true;

            } catch (UnsupportedAudioFileException u) {
                JOptionPane.showMessageDialog(null,"UnsupportedAudioFile, make sure that the audio file is in .wav format");
                dispose();
                new MusicChoice();

            } catch (IOException i) {
                JOptionPane.showMessageDialog(null,"IO Exception, make sure you have placed the file in the right folder");
                dispose();
                new MusicChoice();

            }catch(NullPointerException n){
                JOptionPane.showMessageDialog(null,"Null Pointer Exception, make sure the file contains any audio at all");
                dispose();
                new MusicChoice();
            }
        }
        return inputAudio;
    }
}
