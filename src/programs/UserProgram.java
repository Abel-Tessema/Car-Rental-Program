package programs;

import screen_managers.UserScreenManager;
import screen_managers.UserScreenManagerImplementer;
import services.UserService;

public abstract class UserProgram {
    public static void main(String[] args) {
        
        UserScreenManager manager = new UserScreenManagerImplementer();
        /*
        ToDo: Read loggedInUser.txt, and
              - if it is empty, manager.switchScreen("WelcomeScreen");
              - else, manager.switchScreen("UserScreen")
         */
        UserService userService = new UserService();
        userService.checkRentedCars();

        if (userService.getLoggedInUser() == null)
            manager.switchScreen("WelcomeScreen");
        else
            manager.switchScreen("UserScreen");
        System.out.println();
        System.out.println("Thank you for using our program. See you soon!");
    }
}