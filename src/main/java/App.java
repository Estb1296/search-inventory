

package PACKAGE_NAME;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.InputMismatchException;

public class App {
    public static void main(String[] args) {
        ArrayList<Inventory> inventory = new ArrayList<>();  // Initialize it!
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the file name you want me to read from");
        String file = input.nextLine();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;

            while((line = reader.readLine()) != null) {
                // Parse the line with | delimiter
                String[] parts = line.split("\\|");  // Split by pipe character

                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                float price = Float.parseFloat(parts[2].trim());

                Product product = new Product(id, name, price);
                inventory.add(product);  // Use inventory, not products

                System.out.println(line);
            }

            reader.close();
            System.out.println("What is the filename you want to create?");
            String doc = input.nextLine().trim();
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter(doc));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

// SORT HERE - before the loop
            inventory.sort(Comparator.comparing(Product::getName));

            System.out.println("We carry the following inventory: ");


            for (Product p : inventory) {
                if(p != null) {
                    try {
                        String formattedLine = String.format("id: %d %s - Price: $%.2f%n",
                                p.getId(), p.getName(), p.getPrice());
                        writer.write(formattedLine);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            writer.close();
//        for (Employee employee : employees) {
//            if (employee != null) {
//                writer.write(employee.toprintString() + "\n");
//            }

    /*public static ArrayList<Product> getInventory() {  // Add <Product>
        ArrayList<Product> inventory = new ArrayList<>();  // Add <Product>
        inventory.add(new Product(1, "Laptop", 999.99f));
        inventory.add(new Product(2, "Mouse", 29.99f));
        inventory.add(new Product(3, "Keyboard", 79.99f));
        inventory.add(new Product(4, "Monitor", 349.99f));
        inventory.add(new Product(5, "USB Cable", 9.99f));
        inventory.add(new Product(6, "Headphones", 149.99f));
        inventory.add(new Product(7, "Webcam", 89.99f));
        inventory.add(new Product(8, "External Hard Drive", 119.99f));
        inventory.add(new Product(9, "Desk Lamp", 59.99f));
        inventory.add(new Product(10, "Desk Chair", 299.99f));
        inventory.add(new Product(11,"eggs",7.50f));
        inventory.add(new Product(12,"Tooth Brush",14.5f));
        return inventory;*/

    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

