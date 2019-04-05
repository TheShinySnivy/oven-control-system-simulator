package main.java.com.cwidden;

import main.java.com.cwidden.gui.OvenControllerGui;
import main.java.com.cwidden.gui.OvenSystemGui;
import main.java.com.cwidden.models.ControllerSignal;
import main.java.com.cwidden.service.OvenActuatorService;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class OvenControlSystemController {
    private boolean isGuiInitialized = false;
    private static final OvenControlSystemController INSTANCE = new OvenControlSystemController();

    private static final OvenActuatorService ovenActuatorService = OvenActuatorService.getInstance();

    private OvenControllerGui ovenControllerGui;
    private OvenSystemGui ovenSystemGui;
    private Timer timer;

    public static OvenControlSystemController getInstance() {
        return INSTANCE;
    }

    private OvenControlSystemController() {

    }

    public void buildGui(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e){
            System.out.println("Oops");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Oven Simulator");
        ovenControllerGui = new OvenControllerGui();
        ovenSystemGui = new OvenSystemGui();
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, ovenControllerGui, ovenSystemGui);

        frame.add(jSplitPane);
        frame.pack();
        frame.setVisible(true);

        isGuiInitialized = true;
    }

    public void startTime(){
        timer = new Timer();
        TimerTask task = new Helper();

        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    private void stepTime(ControllerSignal controllerSignal){
        if (controllerSignal.isCountingDown()) {
            if (controllerSignal.getSecondsLeft() == 1) {
                System.out.println("Oven off");
                ovenControllerGui.setOvenOn(false);
            }
            ovenControllerGui.updateTimer(controllerSignal.getSecondsLeft() - 1);
        }
        updateOven(controllerSignal);
    }

    private void updateOven(ControllerSignal controllerSignal){
        ovenActuatorService.setTemperature(ovenSystemGui.temperatureSensor());
        //System.out.println("Current temperature: " + ovenSystemGui.temperatureSensor());

        if (!controllerSignal.isOvenOn()) {
            ovenActuatorService.setOvenState(0);
        } else if (controllerSignal.getIntendedTemperature() < ovenActuatorService.getTemperature()) {
            ovenActuatorService.setOvenState(1);
        } else {
            int temperatureDifference = controllerSignal.getIntendedTemperature() - ovenActuatorService.getTemperature();
            if (temperatureDifference < 50) {
                ovenActuatorService.setOvenState(2);
            } else {
                ovenActuatorService.setOvenState(3);
            }
        }

        ovenActuatorService.updateOven();

        ovenSystemGui.displayTemperature(ovenActuatorService.getTemperature());
    }

    private class Helper extends TimerTask {

        @Override
        public void run() {
            stepTime(ovenControllerGui.getSignal());
        }

    }
}
