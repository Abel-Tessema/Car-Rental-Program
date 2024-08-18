package models;

import file_manager.SchemaId;
import file_manager.Stream;
import utility.Directory;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Car implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String className = "Car";
    private final String classPath = className + "/";
    // ====================== Fields ======================
    private int id;
    private String brand;
    private String model;
    private String type;
    private String color;
    private int year;
    private int quantityAvailable;
    private double baseRate;

    // ====================== Constructors ======================
    public Car() {}

    public Car(int id, String brand, String model, String type, String color, int year, int quantityAvailable, double baseRate) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.color = color;
        this.year = year;
        this.quantityAvailable = quantityAvailable;
        this.baseRate = baseRate;
    }

    // ====================== Getters ======================
    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public int getYear() {
        return year;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public double getBaseRate() {
        return baseRate;
    }

    // ====================== Setters ======================
    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public void setBaseRate(double baseRate) {
        this.baseRate = baseRate;
    }

    public void write() {
        SchemaId database = new SchemaId();
        database.incrementSize(className);
        id = database.getTableLatestId(className);

        Stream<Car> stream = new Stream<>();
        stream.writer(this, Directory.TableDirectory + classPath + id);
    }

    public void delete() {
        SchemaId database = new SchemaId();
        database.decrementSize(className);

        Stream<Car> stream = new Stream<>();
        stream.deleter(Directory.TableDirectory + classPath + id);
    }

    public static Car read(String filePath) {
        Stream<Car> stream = new Stream<>();
        return stream.reader(filePath);
    }

    public List<Car> readAll() {
        List<Car> list = new ArrayList<>();

        try {
            Files.walk(Paths.get(Directory.TableDirectory + classPath), Integer.MAX_VALUE, FileVisitOption.FOLLOW_LINKS)
                    .filter(Files::isRegularFile)
                    .forEach(path -> list.add(read(path.toString())));
        } catch (IOException e) {
            return null;
        }
        return list;
    }
}
