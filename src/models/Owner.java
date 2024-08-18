package models;

import file_manager.SchemaId;
import file_manager.Stream;
import utility.Directory;

import java.io.IOException;
import java.io.Serial;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Owner extends Admin {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Stream<Owner> stream = new Stream<>();
    private final String className = "Owner";
    private final String classPath = className + "/";

    private String companyName;
    /** The maximum number of days between today and the starting date of the rent */
    private int maxDaysBetweenTodayAndStartDate = 180;
    /** The maximum number of days between the starting and ending date of the rent */
    private int maxDaysBetweenStartAndEndDate = 60;

    public Owner() {}
    public Owner(String name, String phoneNumber, String email, String password) {
        super(name, phoneNumber, email, password);
    }

    public void createAdminAccount() {}
    public void removeAdminAccount() {}
    public void removeUserAccount() {}
    public void listAllAdmins() {}
    private void initializeFields() {
        // ToDo: Read and assign fields from file
    }
    // ====================== Getters ======================
    public String getCompanyName() {
        initializeFields();
        companyName = "our car rental company"; // ToDo: Delete this line
        return companyName;
    }

    public int getMaxDaysBetweenTodayAndStartDate() {
        initializeFields();
        return maxDaysBetweenTodayAndStartDate;
    }

    public int getMaxDaysBetweenStartAndEndDate() {
        initializeFields();
        return maxDaysBetweenStartAndEndDate;
    }

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

    public boolean setCompanyName(String companyName) {
        this.companyName = companyName;
        // ToDo: Write the updated field to file
        return stream.writer(this, Directory.TableDirectory + classPath + id);
    }

    public boolean setMaxDaysBetweenTodayAndStartDate(int maxDaysBetweenTodayAndStartDate) {
        this.maxDaysBetweenTodayAndStartDate = maxDaysBetweenTodayAndStartDate;
        return stream.writer(this, Directory.TableDirectory + classPath + id);
    }

    public boolean setMaxDaysBetweenStartAndEndDate(int maxDaysBetweenStartAndEndDate) {
        this.maxDaysBetweenStartAndEndDate = maxDaysBetweenStartAndEndDate;
        return stream.writer(this, Directory.TableDirectory + classPath + id);
    }

    public void write() {
        SchemaId database = new SchemaId();
        database.incrementSize(className);
        id = database.getTableLatestId(className);


        Stream<Owner> stream = new Stream<>();
        stream.writer(this, Directory.TableDirectory + classPath + id);
    }

    public void delete() {
        SchemaId database = new SchemaId();
        database.decrementSize(className);

        Stream<Owner> stream = new Stream<>();
        stream.deleter(Directory.TableDirectory + classPath + id);

    }

    public Owner read(int id) {
        Stream<Owner> stream = new Stream<>();
        return stream.reader(Directory.TableDirectory + classPath + id);
    }

    private static Owner read(String filePath) {
        Stream<Owner> stream = new Stream<>();
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
