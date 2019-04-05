package main.java.com.cwidden.gui;

import main.java.com.cwidden.service.OvenActuatorService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OvenSystemGui extends JPanel implements ActionListener {
    private boolean isBuilt;
    private JLabel temperatureLabel;
    private int temperature;


    public OvenSystemGui() {
        super(new BorderLayout());

        isBuilt = false;

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        temperature = 73;

        this.add(new JLabel(" Oven temperature: "));

        temperatureLabel = new JLabel(temperature + " °F ");
        this.add(temperatureLabel);


        isBuilt = true;
    }

    /**
     *
     * @return
     */
    public int temperatureSensor() {
        return temperature;
    }

    public void displayTemperature(int temperature) {
        this.temperature = temperature;
        temperatureLabel.setText(temperature + " °F");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
