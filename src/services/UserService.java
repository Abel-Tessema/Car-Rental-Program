package services;

import file_manager.SchemaId;
import file_manager.Stream;
import models.Car;
import models.User;
import utility.Directory;

import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // ==============                                                                                                                           `======== Fields ======================
    private final CarService carService = new CarService();
    private List<User> users = new ArrayList<>();
    private final String className = "User";
    private final String classPath = className + "/";
    private final SchemaId database = new SchemaId();
    private final Stream<User> userStream = new Stream<>();
    private final Stream<Car> carStream = new Stream<>();

    // ====================== Log In and Out ======================
    public boolean logIn(String emailOrPhoneNumber, String password) {
        // ToDo: Delete the following two lines after implementing file read/write
//        User user1 = new User("Abel", "0999999999", "a@b.c", "11111111");
//        users.add(user1);
        initializeUsersList();
        for (User user : users)
            if (emailOrPhoneNumber.equals(user.getEmail()) ||
                    emailOrPhoneNumber.equals(user.getPhoneNumber()))
                if (password.equals(user.getPassword())) {
                    saveLoggedInUser(user.getId());
                    return true;
                }
        return false;
    }
    public boolean logOut() {
        boolean operationSuccessful = true;
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(Directory.DatabaseDirectory + "LoggedInUserId"))) {
            dataOutputStream.writeInt(-1);
        } catch (IOException e) {
            operationSuccessful = false;
        }
        // ToDo: Utilize this return value at the method call
        return operationSuccessful;
    }

    // ====================== Sign Up ======================
    public void signUp(String name, String phoneNumber, String email, String password) {
        User user = new User();
        database.incrementSize(className);
        user.setId(database.getTableLatestId(className));
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setPassword(password);

        user.write();
        saveLoggedInUser(user.getId());

//        return saveRegisteredUser(user) && saveLoggedInUser(user.getId());
    }

    // ====================== Book a Car ======================
    public boolean bookCar(Car car, int carQuantity, LocalDate startDate, int numberOfRentingDays) {
        /*
        ToDo (Done):
          - User user = getLoggedInUser;
          - Add the car to user.getRentedCars()
          - Add the carQuantity to user.getRentedCarsQuantities()
          - Add carQuantity * car.getBaseRate() to user.getRentedCarsTotalPrices()
          - Add the endDate to user.getRentedUsersEndDates()
          - Save updated user
          - Decrease the car.getQuantityAvailable() by carQuantity
          - Save updated car
         */
        User user = getLoggedInUser();
        user.getRentedCars().add(car);
        user.getRentedCarsQuantities().add(carQuantity);
        user.getRentedCarsEndDates().add(startDate.plusDays(numberOfRentingDays));
        user.getRentedCarsTotalPrices().add(car.getBaseRate() * numberOfRentingDays);

        car.setQuantityAvailable(car.getQuantityAvailable() - carQuantity);

        // ToDo: Check if both stream operations work. If one of them fails, revert both objects back
        return userStream.writer(user, Directory.TableDirectory + classPath + user.getId()) &&
                carStream.writer(car, Directory.TableDirectory + carService.getClassPath() + car.getId());
    }

    // ====================== List Rented Cars ======================
    public void listRentedCars() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        User user = getLoggedInUser();
        displayRentedCarsListHeaders();
        for (int i = 0; i < user.getRentedCars().size(); i++) {
            System.out.printf("%-5s", user.getRentedCars().get(i).getId());
            System.out.printf("%-20s", user.getRentedCars().get(i).getBrand());
            System.out.printf("%-20s", user.getRentedCars().get(i).getModel());
            System.out.printf("%-20s", user.getRentedCars().get(i).getType());
            System.out.printf("%-20s", user.getRentedCars().get(i).getColor());
            System.out.printf("%-15s", user.getRentedCars().get(i).getYear());
            System.out.printf("%-20s", user.getRentedCarsQuantities().get(i));
            System.out.printf("%-20s", numberFormat.format(user.getRentedCarsTotalPrices().get(i)));
            System.out.println(user.getRentedCarsEndDates().get(i).format(dateTimeFormatter));
        }
    }

    // ====================== Getters ======================
    public User fetchUserById(int id) {
        // ToDo: Check to see if the returned User is null or not at the method call
        System.out.println(Directory.TableDirectory + classPath + id);
        return userStream.reader(Directory.TableDirectory + classPath + id);
    }

    public User getLoggedInUser() {
        // ToDo (Done): Fetch user from loggedInUser.txt file
        // ToDo: Check to see if the returned User is null or not at the method call
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(Directory.DatabaseDirectory + "LoggedInUserId"))) {
            User user = new User();
            return user.read(dataInputStream.readInt());
        } catch (IOException e) {
            return null;
        }
    }

    public List<User> getUsers() {
        initializeUsersList();
        return users;
    }

    // ====================== Reading ======================

    private boolean initializeUsersList() {
        // ToDo (Done): Initialize users from files
        boolean operationSuccessful = true;
        try {
            Files.walk(Paths.get(Directory.TableDirectory + classPath), Integer.MAX_VALUE, FileVisitOption.FOLLOW_LINKS)
                    .filter(Files::isRegularFile)
                    .forEach(path -> userStream.reader(path.toString()));
        } catch (Throwable e) {
            operationSuccessful = false;
        }
        // ToDo: Utilize this return value at the method call
        return operationSuccessful;
    }

    // ====================== Writing ======================
    private boolean saveLoggedInUser(int id) {
        // ToDo: Write logged in user into loggedInUser.txt file
        boolean operationSuccessful = true;
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(Directory.DatabaseDirectory + "LoggedInUserId"))) {
            dataOutputStream.writeInt(id);
        } catch (IOException e) {
            operationSuccessful = false;
        }
        // ToDo: Utilize this return value at the method call
        return operationSuccessful;
    }

    private boolean saveRegisteredUser(User user) {
        // ToDo (Done): Add registered user to registeredUsers.txt file
        // ToDo (Done): Write registered user into loggedInUser.txt file
        // ToDo: Utilize this return value at the method call
        return userStream.writer(user, Directory.TableDirectory + classPath + user.getId());
    }

    public void displayRentedCarsListHeaders() {
        System.out.printf("%-5s", "ID");
        System.out.printf("%-20s", "Brand");
        System.out.printf("%-20s", "Model");
        System.out.printf("%-20s", "Type");
        System.out.printf("%-20s", "Color");
        System.out.printf("%-15s", "Year");
        System.out.printf("%-20s", "Quantity");
        System.out.printf("%-20s", "Total Price");
        System.out.println("Rented Until");
    }

    public boolean checkRentedCars() {
//        List<Car> cars = carService.getCars();
//        for (User user : users) {
//            for (var carDateMap : user.getRentedCars().entrySet())
//                if (carDateMap.getValue().isAfter(LocalDate.now()))
//                    /*
//                    ToDo (Done): Return the rented car to cars List, i.e.,
//                      - add the carQuantity of the rented car to quantityAvailable in cars List
//                      - add the VINs of the rented car to the List of VINs of the car in cars
//                      - remove the car from rentedCars
//                     */
//
//        }
        initializeUsersList();
        boolean operationSuccessful = true;
        try {
            for (User user : users) {
                for (int i = user.getRentedCars().size(); i >= 0; i--) {
                    if (user.getRentedCarsEndDates().get(i).isAfter(LocalDate.now())) {
                        Car car = user.getRentedCars().remove(i);
                        int carQuantity = user.getRentedCarsQuantities().remove(i);
                        user.getRentedCarsEndDates().remove(i);
                        user.getRentedCarsTotalPrices().remove(i);

                        car.setQuantityAvailable(car.getQuantityAvailable() + carQuantity);

                        operationSuccessful =
                                userStream.writer(user, Directory.TableDirectory + classPath + user.getId()) &&
                                        carStream.writer(car, Directory.TableDirectory + carService.getClassPath() + car.getId());
                    }
                    if (!operationSuccessful) break;
                }
                if (!operationSuccessful) break;
            }
        } catch (Throwable e) {
            operationSuccessful = false;
        }
        return operationSuccessful;
    }
}
