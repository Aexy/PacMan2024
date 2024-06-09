package ChooseYourMusic;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.concurrent.CountDownLatch;

public class MusicChoice extends JFrame {

    private final JComboBox<String> dropdownMenu;
    public AudioChoices enumAudioChosen;
    public boolean audioPicked = false;
    public CountDownLatch latch;

    public MusicChoice(CountDownLatch latch) {
        //Set up latch
        this.latch = latch;
        // Set up JFrame
        setTitle("Welcome to Pacman Music Wizard");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the JFrame

        // Create main panel
        JPanel mainPanel = new JPanel();
        JLabel descriptionLabel = new JLabel("Please Pick a Song Type From the Drop Down Menu Below:");
        mainPanel.setLayout(new GridLayout(5, 1));
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
     * releases the CountDown latch after submitted
     *
     * @return JPanel with submitButton.
     */
    private JPanel submitButton() {
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle submit button click
                int optionChosen = dropdownMenu.getSelectedIndex();
                // Handle self define absolute path
                if (optionChosen == AudioChoices.SELF_INPUT.getNumber()) {
                    //Pop Up
                    latch.countDown();
                    InputSelfDefined selfDefined = new InputSelfDefined(latch);
                    if (selfDefined.selfAudioPicked) {
                        JOptionPane.showMessageDialog(null, "Chosen number is: " + AudioChoices.getAudioChoiceByNumber(optionChosen).getNumber() + " with the absolute path " + AudioChoices.getAudioChoiceByNumber(optionChosen).getAbsolutePath());
                    }

                } else {
                    //get AudioChoice
                    enumAudioChosen = AudioChoices.getAudioChoiceByNumber(optionChosen);
                    //Display Message
                    JOptionPane.showMessageDialog(null, "Chosen number is: " + enumAudioChosen.getNumber() + " with the absolute path " + enumAudioChosen.getAbsolutePath());
                    //clear screen
                    dispose();
                    //audio selected
                    audioPicked = true;
                    if(enumAudioChosen == AudioChoices.NO_MUSIC){
                        latch.countDown();
                        return;
                    }
                    //play the audio
                    AudioPlayer pa = new AudioPlayer(enumAudioChosen.getAbsolutePath(), audioPicked, false);
                    Clip clip = pa.getClip();
                    clip.start();
                    latch.countDown();

                }

            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        return buttonPanel;
    }

}


