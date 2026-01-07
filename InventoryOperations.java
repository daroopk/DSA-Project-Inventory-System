// Save this as InventoryOperations.java
import java.io.*;
import java.util.ArrayList;

public class InventoryOperations {
    private ArrayList<Item> inventory;
    private final String FILE_NAME = "inventory.txt";

    public InventoryOperations() {
        inventory = new ArrayList<>();
        loadData(); // Load file automatically when created
    }

    public ArrayList<Item> getList() {
        return inventory;
    }

    // --- CRUD OPERATIONS ---

    public void addItem(int id, String name, double price, int qty) {
        inventory.add(new Item(id, name, price, qty));
    }

    // *** NEW UPDATE METHOD ADDED HERE ***
    public void updateItem(int id, String name, double price, int qty) {
        for (int i = 0; i < inventory.size(); i++) {
            // Find the item by ID
            if (inventory.get(i).getId() == id) {
                // Replace the old item with the new updated data
                inventory.set(i, new Item(id, name, price, qty));
                return; // Stop loop once found
            }
        }
    }

    public void deleteItem(int id) {
        inventory.removeIf(item -> item.getId() == id);
    }

    // --- DSA ALGORITHMS ---

    // Bubble Sort by ID
    public void sortById() {
        int n = inventory.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (inventory.get(j).getId() > inventory.get(j + 1).getId()) {
                    swap(j, j + 1);
                }
            }
        }
    }

    // Algorithm: Bubble Sort by Name (Required for Binary Search)
    public void sortByName() {
        int n = inventory.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (inventory.get(j).getName().compareToIgnoreCase(inventory.get(j + 1).getName()) > 0) {
                    swap(j, j + 1);
                }
            }
        }
    }

    private void swap(int i, int j) {
        Item temp = inventory.get(i);
        inventory.set(i, inventory.get(j));
        inventory.set(j, temp);
    }

    // Algorithm: Binary Search
    public int binarySearchByName(String targetName) {
        int left = 0;
        int right = inventory.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midName = inventory.get(mid).getName();
            int comparison = midName.compareToIgnoreCase(targetName);

            if (comparison == 0) return mid;
            if (comparison < 0) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    // --- FILE I/O ---

    public void saveData() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Item item : inventory) {
                writer.write(item.toString());
                writer.newLine();
            }
        }
    }

    private void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    inventory.add(new Item(
                            Integer.parseInt(parts[0]), parts[1],
                            Double.parseDouble(parts[2]), Integer.parseInt(parts[3])
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}