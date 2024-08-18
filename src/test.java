import models.Car;
import models.User;
import services.UserService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        ArrayList<Car> cars = new ArrayList<>();
        // Add cars to the list
        Car car1 = new Car(1, "a", "b", "c", "d", 2000, 5, 500);
        Car car2 = new Car(2, "e", "f", "g", "h", 2001, 3, 600);
        cars.addAll(List.of(car1, car2));
        try {
            FileOutputStream fileOut = new FileOutputStream("cars.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(cars);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in cars.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
        ArrayList<Car> carsFromFile = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("cars.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            carsFromFile = (ArrayList<Car>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Deserialized data from cars.ser:");
            Car tempCar = new Car();
            for (Car car : carsFromFile) {
                if (car.getId() == 1) {
                    tempCar = car;
                    break;
                }
            }
            tempCar.setYear(2005);
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Car class not found");
            c.printStackTrace();
        }

        try {
            FileOutputStream fileOut = new FileOutputStream("cars.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(carsFromFile);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in cars.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

        try {
            FileInputStream fileIn = new FileInputStream("cars.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<Car> carsFromFile1 = (ArrayList<Car>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Deserialized data from cars.ser:");
            for (Car car : carsFromFile1) {
                if (car.getId() == 1) {
                    System.out.println(car.getYear());
                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Car class not found");
            c.printStackTrace();
        }
//        UserService userService = new UserService();
//        List<User> users = userService.getUsers();
//        System.out.println(users.size());
//        System.out.println(users.get(0).getName());
    }
}
