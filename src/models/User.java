package models;

import file_manager.SchemaId;
import file_manager.Stream;
import utility.Directory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User extends Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient Stream<User> stream = new Stream<>();
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.stream = new Stream<>();
    }
    // ====================== Fields ======================
    private List<Car> rentedCars;
    private List<Integer> rentedCarsQuantities;
    private List<LocalDate> rentedCarsEndDates;
    private List<Double> rentedCarsTotalPrices;

    // ====================== Constructors ======================
    public User() {}

    public User(String name, String phoneNumber, String email, String password) {
        super(name, phoneNumber, email, password);
    }

    // ====================== Getters ======================
    public List<Car> getRentedCars() {
        return rentedCars;
    }

    public List<Integer> getRentedCarsQuantities() {
        return rentedCarsQuantities;
    }

    public List<LocalDate> getRentedCarsEndDates() {
        return rentedCarsEndDates;
    }

    public List<Double> getRentedCarsTotalPrices() {
        return rentedCarsTotalPrices;
    }

    // ====================== Setters ======================
    private final String className = "User";
    private final String classPath = className + "/";

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void write() {
        SchemaId database = new SchemaId();
        database.incrementSize(className);
        id = database.getTableLatestId(className);

        Stream<User> stream = new Stream<>();
        stream.writer(this, Directory.TableDirectory + classPath + id);
    }

    public void delete() {
        SchemaId database = new SchemaId();
        database.decrementSize(className);

        Stream<User> stream = new Stream<>();
        stream.deleter(Directory.TableDirectory + classPath + id);

    }

    public User read(int id) {
        Stream<User> stream = new Stream<>();
        return stream.reader(Directory.TableDirectory + classPath + id);
    }

    private static User read(String filePath) {
        Stream<User> stream = new Stream<>();
        return stream.reader(filePath);
    }

    public List<User> readAll() {
        Stream<User> stream = new Stream<>();
        List<User> list = new ArrayList<>();

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
