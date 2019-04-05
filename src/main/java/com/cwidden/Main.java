package main.java.com.cwidden;

public class Main {

    public static void main(String[] args) {
        OvenControlSystemController ovenControlSystemController = OvenControlSystemController.getInstance();
        ovenControlSystemController.buildGui();
        ovenControlSystemController.startTime();
    }
}
