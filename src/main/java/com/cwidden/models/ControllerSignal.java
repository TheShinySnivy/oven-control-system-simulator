package main.java.com.cwidden.models;

public class ControllerSignal {
    private int secondsLeft;
    private boolean countingDown;
    private int intendedTemperature;
    private boolean ovenOn;

    /**
     *
     * @param secondsLeft
     * @param countingDown
     * @param intendedTemperature
     */
    public ControllerSignal(int secondsLeft, boolean countingDown, int intendedTemperature, boolean ovenOn) {
        this.secondsLeft = secondsLeft;
        this.countingDown = countingDown;
        this.intendedTemperature = intendedTemperature;
        this.ovenOn = ovenOn;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public boolean isCountingDown() {
        return countingDown;
    }

    public int getIntendedTemperature() {
        return intendedTemperature;
    }

    public boolean isOvenOn() {
        return ovenOn;
    }
}
