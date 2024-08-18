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

public class Admin extends Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Stream<Admin> stream = new Stream<>();
    private final String className = "Admin";
    private final String classPath = className + "/";
    // ====================== Constructors ======================
    public Admin() {}

    public Admin(String name, String phoneNumber, String email, String password) {
        super(name, phoneNumber, email, password);
    }

    // ====================== Modifying ======================
    public void modifyCar() {}

    // ====================== Adding ======================
    public void addCar() {}
    public void addCarBrand() {}
    public void addCarType() {}

    // ====================== Removing ======================
    public void removeCar() {}
    public void removeCarBrand() {}
    public void removeCarType() {}

    // ====================== Listing ======================
    public void listAllUsers() {}
    public void listAllCarBrands() {}
    public void listAllCarTypes() {}
    public void listAllRentedCars() {}

    // ====================== Setters ======================
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


        Stream<Admin> stream = new Stream<>();
        stream.writer(this, Directory.TableDirectory + classPath + id);
    }

    public void delete() {
        SchemaId database = new SchemaId();
        database.decrementSize(className);

        Stream<Admin> stream = new Stream<>();
        stream.deleter(Directory.TableDirectory + classPath + id);

    }

    public Admin read(int roll) {
        Stream<Admin> stream = new Stream<>();
        return stream.reader(Directory.TableDirectory + classPath + roll);
    }

    private static Admin read(String filePath) {
        Stream<Admin> stream = new Stream<>();
        return stream.reader(filePath);
    }

    public List<Admin> readAll() {
        List<Admin> list = new ArrayList<>();

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
