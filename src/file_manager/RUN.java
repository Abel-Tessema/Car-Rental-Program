package file_manager;

import services.CarService;
import services.UserService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RUN
{
    public static void main(String[] args)
    {

//        Stream<Foo> stream = new Stream<>();

//        Foo item = new Foo("goo", 12.34);
//        List<Foo> item = Foo.readAll();
//        for (Foo i : item)
//        {
//            i.display();
//        }

//        item.write();

        SchemaId Database = new SchemaId();
//        Database.resetDatabase();
//        Database.display();

//        File directory = new File("");
//        System.out.println(directory.isDirectory());

//        Database.addTable("Admin");
//        Database.addTable("Car");
//        Database.addTable("Owner");
//        Database.addTable("User");
        Database.resetDatabase();
//        UserService userService = new UserService();
//        System.out.println(userService.getLoggedInUser());
//        CarService carService = new CarService();
//        carService.addCar("a", "b", "c", "d", 2000, 5, 500);



        Database.display();
    }
}
