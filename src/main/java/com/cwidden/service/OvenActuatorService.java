package main.java.com.cwidden.service;

public class OvenActuatorService {
    private static OvenActuatorService INSTANCE = new OvenActuatorService();
    private int temperature;
    private int ovenState = 0;

    /**
     * Simulates how the oven temperature would change depending on how hot the heaters are.
     */
    private OvenActuatorService() {
        temperature = 73;
    }

    public static OvenActuatorService getInstance() {
        return INSTANCE;
    }

    /**
     * 0 for off, 1 for low, 2 for med, 3 for high
     * Oven state is dependent on the difference between actual and intended temperature. It is set by the controller.
     */
    public void setOvenState(int state) {
        ovenState = state;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void updateOven() {
        if (ovenState == 0) {
            temperature = temperature - 5;
        } else if (ovenState == 1) {
            temperature = temperature - 2;
        } else if (ovenState == 2) {
            temperature = temperature + 2;
        } else {
            temperature = temperature + 5;
        }

        if (temperature < 73) {
            temperature = 73;
        }
    }

    public int getTemperature() {
        return temperature;
    }
}
