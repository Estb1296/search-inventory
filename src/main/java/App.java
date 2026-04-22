

package PACKAGE_NAME;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ArrayList<Product> inventory = new ArrayList<>();  // Initialize it!
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
            for (int i = 0; i < inventory.size(); i++) {
                Product p = inventory.get(i);
                System.out.printf("id: %d %s - Price: $%.2f%n",
                        p.getId(), p.getName(), p.getPrice());
            }
        }
        public static ArrayList<Product> getInventory() {
            ArrayList<Product> inventory = new ArrayList<Product>();
            // this method loads product objects into inventory
            // and its details are not shown
            return inventory;
        }
    }
