package screens.owner_screens;

import models.Car;
import screen_managers.OwnerScreenManager;
import services.CarService;
import utility.Console;

import java.util.regex.Pattern;

public class ChangeBaseRateScreen {
    private final OwnerScreenManager ownerScreenManager;
    private final CarService carService = new CarService();

    public ChangeBaseRateScreen(OwnerScreenManager ownerScreenManager) {
        this.ownerScreenManager = ownerScreenManager;
    }

    public void display() {
        System.out.println();
        System.out.println("... > Modify Car > Base Rate");

        // ToDo (Done): Fetch car id from CarToBeModified.txt or something, and assign it to car
        int toBeModifiedCarId = carService.fetchToBeModifiedCarId();
        if (toBeModifiedCarId == -1) {
            System.out.println("Could not fetch the ID number of the car to be modified. Please try again.");
        }

        else {
            Car car = carService.fetchCarById(toBeModifiedCarId);

            System.out.println("The previous base rate is: " + car.getBaseRate() + ".");
            double newBaseRate = Console.readNumber("Enter the new base rate", 1, 10_000_000);
            System.out.println("The base rate of this car will henceforth be: Br. " + newBaseRate + ".");
            System.out.println("Are you sure you want to change it to that?");
            String choice = Console.readText("Choice (Y/N)", Pattern.compile("[YyNn]"), "Invalid choice. Please try again.");
            if (choice.equalsIgnoreCase("y")) {
                car.setBaseRate(newBaseRate);
                // ToDo: Update the file containing this specific car
                System.out.println("The base rate has been successfully changed to Br. " + newBaseRate + ".");
            } else {
                System.out.println("You have chosen not to change the base rate.");
            }
        }
        Console.continueOnEnter();
        ownerScreenManager.switchScreen("ModifyCarScreen");
    }
}
