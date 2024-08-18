package services;

import file_manager.SchemaId;
import file_manager.Stream;
import models.Car;
import utility.Directory;

import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class CarService {
    // ====================== Fields ======================
    private List<Car> cars = new ArrayList<>();
    private List<String> carBrands = new ArrayList<>();
    private List<String> carTypes = new ArrayList<>();
    private List<Integer> carYears = new ArrayList<>();
    private final String className = "Car";
    private final String classPath = className + "/";
    private final SchemaId database = new SchemaId();
    private final Stream<Car> stream = new Stream<>();

    // ====================== Initialize cars List ======================
    private boolean initializeLists() {
        // ToDo (Done): Read from files into the cars List
        boolean operationSuccessful = true;
        try {
            Files.walk(Paths.get(Directory.TableDirectory + classPath), Integer.MAX_VALUE, FileVisitOption.FOLLOW_LINKS)
                    .filter(Files::isRegularFile)
                    .forEach(path -> cars.add(stream.reader(path.toString())));
        } catch (IOException e) {
            operationSuccessful = false;
        }

        for (Car car : cars) {
            carBrands.add(car.getBrand());
            carTypes.add(car.getType());
            carYears.add(car.getYear());
        }
        carBrands = sortAndRemoveDuplicate(carBrands);
        carTypes = sortAndRemoveDuplicate(carTypes);
        carYears = sortAndRemoveDuplicate(carYears);

        // ToDo: Utilize this return value at the method call
        return operationSuccessful;
    }

    // ====================== Displaying ======================
    private void displayCarListHeaders() {
        System.out.printf("%-5s", "ID");
        System.out.printf("%-20s", "Brand");
        System.out.printf("%-20s", "Model");
        System.out.printf("%-20s", "Type");
        System.out.printf("%-20s", "Color");
        System.out.printf("%-15s", "Year");
        System.out.printf("%-25s", "Quantity Available");
        System.out.println("Base Rate");
    }

    private void displayCar(Car car) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        if (car.getQuantityAvailable() != 0) {
            System.out.printf("%-5s", car.getId());
            System.out.printf("%-20s", car.getBrand());
            System.out.printf("%-20s", car.getModel());
            System.out.printf("%-20s", car.getType());
            System.out.printf("%-20s", car.getColor());
            System.out.printf("%-15s", car.getYear());
            System.out.printf("%-25s", car.getQuantityAvailable());
            System.out.println("Br. " + numberFormat.format(car.getBaseRate()));
        }
    }

    public void displayCarProperties(Car car) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        System.out.println("Brand: " + car.getBrand());
        System.out.println("Model: " + car.getModel());
        System.out.println("Type: " + car.getType());
        System.out.println("Color: " + car.getColor());
        System.out.println("Year: " + car.getYear());
        System.out.println("Quantity available: " + car.getQuantityAvailable());
        System.out.println("Base rate: Br. " + numberFormat.format(car.getBaseRate()));
    }

    // ====================== Listing ======================
    public void listAllCarsAscending(String sortBy) {
        initializeLists();
        List<Car> sortedCars = new ArrayList<>();
        switch (sortBy) {
            case "id" ->
                    sortedCars = cars.stream().sorted(Comparator.comparing(Car::getId)).collect(Collectors.toList());
            case "brand" ->
                    sortedCars = cars.stream().sorted(Comparator.comparing(Car::getBrand).thenComparing(Car::getModel)).collect(Collectors.toList());
            case "type" ->
                    sortedCars = cars.stream().sorted(Comparator.comparing(Car::getType).thenComparing(Car::getBrand).thenComparing(Car::getModel)).collect(Collectors.toList());
            case "year" ->
                    sortedCars = cars.stream().sorted(Comparator.comparing(Car::getYear).thenComparing(Car::getBrand).thenComparing(Car::getModel)).collect(Collectors.toList());
            case "quantityAvailable" ->
                    sortedCars = cars.stream().sorted(Comparator.comparing(Car::getQuantityAvailable).thenComparing(Car::getBrand).thenComparing(Car::getModel)).collect(Collectors.toList());
            case "baseRate" ->
                    sortedCars = cars.stream().sorted(Comparator.comparing(Car::getBaseRate).thenComparing(Car::getBrand).thenComparing(Car::getModel)).collect(Collectors.toList());
            default -> {
                System.out.println("Invalid sorting choice. Please contact the company about it.");
                exit(1);
            }
        }
        displayCarListHeaders();
        for (Car car : sortedCars)
            displayCar(car);
    }

    public void listAllCarsDescending(String sortBy) {
        initializeLists();
        List<Car> sortedCars = new ArrayList<>();
        switch (sortBy) {
            case "id":
                sortedCars = cars.stream().sorted(Comparator.comparing(Car::getId).reversed()).collect(Collectors.toList());
                break;
            case "brand":
                sortedCars = cars.stream().sorted(Comparator.comparing(Car::getBrand).thenComparing(Car::getModel).reversed()).collect(Collectors.toList());
                break;
            case "type":
                sortedCars = cars.stream().sorted(Comparator.comparing(Car::getType).thenComparing(Car::getBrand).thenComparing(Car::getModel).reversed()).collect(Collectors.toList());
                break;
            case "year":
                sortedCars = cars.stream().sorted(Comparator.comparing(Car::getYear).thenComparing(Car::getBrand).thenComparing(Car::getModel).reversed()).collect(Collectors.toList());
                break;
            case "quantityAvailable":
                sortedCars = cars.stream().sorted(Comparator.comparing(Car::getQuantityAvailable).thenComparing(Car::getBrand).thenComparing(Car::getModel).reversed()).collect(Collectors.toList());
                break;
            case "baseRate":
                sortedCars = cars.stream().sorted(Comparator.comparing(Car::getBaseRate).thenComparing(Car::getBrand).thenComparing(Car::getModel).reversed()).collect(Collectors.toList());
                break;
            default:
                System.out.println("Invalid sorting choice. Please contact the company about it.");
                exit(1);
        }
        displayCarListHeaders();
        for (Car car : sortedCars)
            displayCar(car);
    }

    public void listAllCarBrands() {
        initializeLists();
        displayCarListHeaders();
        for (int i = 0; i < carBrands.size(); i++)
            System.out.println((i + 1) + ") " + carBrands.get(i));
    }

    public void listAllCarTypes() {
        initializeLists();
        displayCarListHeaders();
        for (int i = 0; i < carTypes.size(); i++)
            System.out.println((i + 1) + ") " + carTypes.get(i));
    }

    public void listAllCarYears() {
        initializeLists();
        displayCarListHeaders();
        for (int i = 0; i < carYears.size(); i++)
            System.out.println((i + 1) + ") " + carYears.get(i));
    }

    public void listCarsByBrand(String brand) {
        initializeLists();
        displayCarListHeaders();
        for (Car car : cars)
            if (brand.equals(car.getBrand()))
                displayCar(car);
    }

    public void listCarsByType(String type) {
        initializeLists();
        displayCarListHeaders();
        for (Car car : cars)
            if (type.equals(car.getType()))
                displayCar(car);
    }

    public void listCarsByYear(int year) {
        initializeLists();
        displayCarListHeaders();
        for (Car car : cars)
            if (year == car.getYear())
                displayCar(car);
    }

    public void searchCar(String key) {
        initializeLists();
        displayCarListHeaders();
        Pattern pattern = Pattern.compile(key.toLowerCase());
        for (Car car : cars)
            if (pattern.matcher(Integer.toString(car.getId())).find() ||
                    pattern.matcher(car.getBrand().toLowerCase()).find() ||
                    pattern.matcher(car.getModel().toLowerCase()).find() ||
                    pattern.matcher(car.getType().toLowerCase()).find() ||
                    pattern.matcher(Integer.toString(car.getYear())).find() ||
                    pattern.matcher(car.getColor().toLowerCase()).find()
            )
                displayCar(car);
    }

    public boolean addCar(String brand, String model, String type, String color, int year, int quantityAvailable, double baseRate) {
        Car car = new Car();
        database.incrementSize(className);

        car.setId(database.getTableLatestId(className));
        car.setBrand(brand);
        car.setModel(model);
        car.setType(type);
        car.setColor(color);
        car.setYear(year);
        car.setQuantityAvailable(quantityAvailable);
        car.setBaseRate(baseRate);
        // ToDo (Done): Auto-generate id for the car, and write the car into file
        // ToDo: Utilize this return value at the method call
        return stream.writer(car, Directory.TableDirectory + classPath + car.getId());
    }

    public boolean deleteCar(Car car) {
        // ToDo (Done): Write the logic for deleting a car
        database.decrementSize(className);
        // ToDo: Utilize this return value at the method call
        return stream.deleter(Directory.TableDirectory + classPath + car.getId());
    }

    public Car fetchCarById(int id) {
        // ToDo: Check to see if the returned Car is null or not at the method call
        return stream.reader(Directory.TableDirectory + classPath + id);
    }

    // ====================== Getters ======================

    public List<Car> getCars() {
        initializeLists();
        return cars;
    }

    public List<String> getCarBrands() {
        initializeLists();
        return carBrands;
    }

    public List<String> getCarTypes() {
        initializeLists();
        return carTypes;
    }

    public List<Integer> getCarYears() {
        return carYears;
    }

    public int getLastCarId() {
        return database.getTableLatestId(className) - 1;
    }

    public String getClassPath() {
        return classPath;
    }

    public boolean saveToBeModifiedCarId(int id) {
        boolean operationSuccessful = true;
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(Directory.DatabaseDirectory + "ToBeModifiedCarId"))) {
            dataOutputStream.writeInt(id);
        } catch (IOException e) {
            operationSuccessful = false;
        }
        // ToDo: Utilize this return value at the method call
        return operationSuccessful;
    }

    public int fetchToBeModifiedCarId() {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(Directory.DatabaseDirectory + "ToBeModifiedCarId"))) {
            return dataInputStream.readInt();
        } catch (IOException e) {
            return -1;
        }
    }

    // ====================== Private Utilities ======================
    private <T> List<T> sortAndRemoveDuplicate(List<T> list) {
        return list.stream().sorted().distinct().collect(Collectors.toList());
    }
}
