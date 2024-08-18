package file_manager;

import java.io.*;
import java.nio.file.Paths;

public class Stream<T> implements Serializable {
    public boolean writer(T data, String filePath) {
        boolean operationSuccessful = true;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        } catch (IOException e) {
            operationSuccessful = false;
        }
        return operationSuccessful;
    }

    public T reader(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println(">>>>>>>>>>>>>File does not exist: " + filePath);
                return null;
            } else {
                System.out.println("File exists!!!!!!!!!!!!!!!!!");
            }
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean deleter(String filePath) {
        boolean operationSuccessful = true;
        File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {
            if (!fileToDelete.delete())
                operationSuccessful = false;
        }
        else
            operationSuccessful = false;
        return operationSuccessful;
    }
}
