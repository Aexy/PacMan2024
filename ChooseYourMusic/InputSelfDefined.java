package ChooseYourMusic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class InputSelfDefined extends JFrame{

    private JTextField absoluteInput;
    InputSelfDefined(){
        //set up and center JFrame
        setTitle("Enter Absolute Path");
        setSize(300,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //MainJFrame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5,2));

        //Descriptions
        JPanel descriptions = descriptions();

        //Input
        absoluteInput = new JTextField(40);
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(absoluteInput);

        //submit Button
        JPanel submitPanel = submitButton();

        //Init
        mainPanel.add(descriptions);
        mainPanel.add(inputPanel);
        mainPanel.add(submitPanel);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Creates a submit button on the frame, which displays the updated
     * Absolute path of the Self Input Audio Choice, once pressed upon.
     * @return JPanel that contains the submit button.
     **/
    private JPanel submitButton(){
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //set Self Input Path
                AudioChoices.SELF_INPUT.setAbsolutePath("sources/"+absoluteInput.getText());
                JOptionPane.showMessageDialog(null, "The absolute path of Self Input has been updated to: " + AudioChoices.SELF_INPUT.getAbsolutePath() + " \n Game will start now");
                dispose();
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
