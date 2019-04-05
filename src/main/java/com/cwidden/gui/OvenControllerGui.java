package main.java.com.cwidden.gui;

import main.java.com.cwidden.OvenControlSystemController;
import main.java.com.cwidden.models.ControllerSignal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OvenControllerGui extends JPanel implements ActionListener {
    private boolean isBuilt;

    private ButtonGroup onSwitchRadioButtons;
    private JComboBox temperatureComboBox;
    private JTextField timerField;
    private JButton startButton;
    private JButton stopButton;
    private JLabel timeLeftLabel;
    private boolean ovenOn = false;
    private boolean countingDown;
    private int timeLeft;
    private int intendedTemperature = 250;
    private JRadioButton onButton;
    private JRadioButton offButton;

    private OvenControlSystemController ovenControlSystemController;

    public OvenControllerGui() {
        super(new BorderLayout());

        isBuilt = false;
        countingDown = false;

        //this.setBackground(Color.white);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        onButton = new JRadioButton("On");
        onButton.setActionCommand("On");
        onButton.setSelected(false);

        offButton = new JRadioButton("Off");
        offButton.setActionCommand("Off");
        offButton.setSelected(true);

        onSwitchRadioButtons = new ButtonGroup();
        onSwitchRadioButtons.add(onButton);
        onSwitchRadioButtons.add(offButton);


        JPanel temperatureFieldContainer = new JPanel(new FlowLayout());

        temperatureFieldContainer.add(new JLabel("Temperature: "));

        String[] temperatureArray = new String[13];
        for (int i = 0; i < 13; i++){
            temperatureArray[i] = String.valueOf((250 + 25 * i));
        }
        temperatureComboBox = new JComboBox(temperatureArray);
        temperatureComboBox.setSelectedIndex(0);
        temperatureComboBox.setActionCommand("TemperatureBox");
        temperatureFieldContainer.add(temperatureComboBox);


        JPanel timerFieldContainer = new JPanel(new FlowLayout());

        timerFieldContainer.add(new JLabel("Timer: "));

        timerField = new JTextField();
        timerField.setPreferredSize(new Dimension(60, 25));
        timerFieldContainer.add(timerField);

        timerFieldContainer.add(new JLabel("seconds"));


        JPanel timerButtonContainer = new JPanel(new FlowLayout());

        startButton = new JButton("Start");
        timerButtonContainer.add(startButton);

        stopButton = new JButton("Stop");
        timerButtonContainer.add(stopButton);


        timeLeftLabel = new JLabel("Time left: 00:00:00");


        onButton.addActionListener(this);
        offButton.addActionListener(this);
        temperatureComboBox.addActionListener(this);
        startButton.addActionListener(this);
        stopButton.addActionListener(this);

        this.add(onButton);
        this.add(offButton);
        this.add(temperatureFieldContainer);
        this.add(timerFieldContainer);
        this.add(timerButtonContainer);
        this.add(timeLeftLabel);

        isBuilt = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("On")) {
            System.out.println("Oven on.");
            ovenOn = true;
        }
        if (e.getActionCommand().equals("Off")) {
            System.out.println("Oven off.");
            ovenOn = false;
        }
        if (e.getSource().equals(temperatureComboBox)) {
            System.out.println("Temperature changed.");
        }
        if (e.getSource().equals(startButton)) {
            try {
                int seconds = Integer.parseInt(timerField.getText());
                if (seconds < 1) {
                    throw new NumberFormatException();
                }
                timeLeft = seconds;
                timeLeftLabel.setText("Time left: " + secondsToClockDisplay(seconds));
                countingDown = true;
                System.out.println("Start timer " + seconds + " seconds.");

            } catch (NumberFormatException n) {
                System.out.println("Invalid number.");
            }
        }
        if (e.getSource().equals(stopButton)) {
            countingDown = false;
            System.out.println("Stop timer.");
        }
    }

    public void updateTimer(int seconds) {
        timeLeft = seconds;
        timeLeftLabel.setText("Time left: " + secondsToClockDisplay(seconds));
        if (seconds == 0) {
            countingDown = false;
        }
    }

    private String secondsToClockDisplay(int seconds) {
        String hours = String.valueOf(seconds / 3600);
        seconds = seconds % 3600;
        String minutes = String.valueOf(seconds / 60);
        seconds = seconds % 60;
        String secondsString = String.valueOf(seconds);
        if (minutes.length() < 2){
            minutes = "0" + minutes;
        }
        if (secondsString.length() < 2){
            secondsString = "0" + secondsString;
        }
        return (hours + ":" + minutes + ":" + secondsString);
    }

    public ControllerSignal getSignal() {
        intendedTemperature = 0;
        if (ovenOn) {
            intendedTemperature = Integer.parseInt((String) temperatureComboBox.getSelectedItem());
        }
        return new ControllerSignal(timeLeft, countingDown, intendedTemperature, ovenOn);
    }

    public void setOvenOn(boolean ovenOn) {
        this.ovenOn = ovenOn;
        offButton.setSelected(true);
    }
}
