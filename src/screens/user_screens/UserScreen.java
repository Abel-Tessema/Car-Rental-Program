package screens.user_screens;

import models.User;
import screen_managers.UserScreenManager;
import screen_managers.UserScreenManagerImplementer;
import services.UserService;
import utility.Console;

import java.time.LocalDateTime;

public class UserScreen {
    private final UserScreenManager userScreenManager;
    private final UserService userService = new UserService();

    public UserScreen(UserScreenManager userScreenManager) {
        this.userScreenManager = userScreenManager;
    }

    public void display() {
        System.out.println();
        System.out.println("Welcome Screen > Log In > User Main Menu");
        int currentHour = LocalDateTime.now().getHour();
        if (currentHour >= 6 && currentHour < 12)
            System.out.print("Good morning, ");
        else if (currentHour >= 12 && currentHour < 18)
            System.out.print("Good afternoon, ");
        else
            System.out.print("Good evening, ");
        System.out.println(userService.getLoggedInUser().getName() + ".");
        System.out.println("What would you like to do?");
        System.out.println("1) Book a car");
        System.out.println("2) List all cars");
        System.out.println("3) List cars with filter");
        System.out.println("4) Search for a car");
        System.out.println("5) List all cars you've rented");
        System.out.println("6) Modify your user account");
        System.out.println("7) FAQ");
        System.out.println("8) Log out");
        System.out.println("9) Exit");

        int userMainMenuChoice = (int) Console.readNumber("Choice", 1, 9);

        switch (userMainMenuChoice) {
            case 1: userScreenManager.switchScreen("BookCarScreen"); break;
            case 2: userScreenManager.switchScreen("AllCarsScreen"); break;
            case 3: userScreenManager.switchScreen("CarFilterScreen"); break;
            case 4: userScreenManager.switchScreen("SearchCarScreen"); break;
            case 5: userScreenManager.switchScreen("RentedCarsScreen"); break;
            case 6: userScreenManager.switchScreen("AccountScreen"); break;
            case 7: userScreenManager.switchScreen("FaqScreen");
                    userScreenManager.switchScreen("UserScreen"); break;
            case 8: userScreenManager.switchScreen("LogoutScreen"); break;
            case 9: break;
        }
    }
}
