package repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersistenceManager {

    public static <T extends Serializable> void save(String fileName, List<T> items) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(items);
            System.out.println("✓ Saved " + items.size() + " items to file: " + fileName);

        } catch (IOException e) {
            System.err.println("❌ Save error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> load(String fileName) {
        File file = new File(fileName);

        // Return empty list if file doesn't exist
        if (!file.exists()) {
            System.out.println("ℹ️  File " + fileName + " doesn't exist. Starting with empty list.");
            return new ArrayList<>();
        }

        try (FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            List<T> items = (List<T>) ois.readObject();
            System.out.println("✓ Loaded " + items.size() + " items from file: " + fileName);
            return items;

        } catch (IOException e) {
            System.err.println("❌ Read error: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Class not found: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
