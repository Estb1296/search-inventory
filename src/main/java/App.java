import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        ArrayList<Inventory> inventory = new ArrayList<>();

        Scanner input = new Scanner(System.in);

        System.out.println("Enter the file name you want me to read from: ");
        String file = input.nextLine().trim();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please try again.");
            throw new RuntimeException(e);
        }

        boolean stopApp = true;
        System.out.println();

        while(stopApp) {
            int command = MenuPrompt(input);

            HashMap<String, ArrayList<Inventory>> inventoryByName = new HashMap<>();
            switch(command) {
                case 1 -> {
                    try {
                        fileInputReader(reader, inventory, input,inventoryByName);
                    } catch (IOException e) {
                        System.out.println("Error reading file: " + e.getMessage());
                    }
                }
                case 2 -> searchById(input, inventory);
                case 3 -> searchByPrice(input, inventory);
                case 4 -> addNewProduct(input, inventory);
                case 5 -> {
                    System.out.println("Thank you for using the application!");
                    stopApp = false;
                }
                case 6 ->searchByName(input,inventoryByName);
                default -> System.out.println("Invalid command. Please try again.");
            }
        }

        input.close();
    }

    public static void fileInputReader(BufferedReader reader, ArrayList<Inventory> inventory, Scanner input, HashMap<String, ArrayList<Inventory>> inventoryByName) throws IOException {
        String line;

        try {
            while((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim().toLowerCase();

                float price = Float.parseFloat(parts[2].trim());

                Inventory product = new Inventory(id, name, price);
                inventory.add(product);
                inventoryByName.put(name,inventory);
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        reader.close();

        System.out.println("\nDo you want to write to another file? (yes/no)");
        String answer = input.nextLine().trim();

        while(!(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no"))) {
            System.out.println("Invalid input! Please enter 'yes' or 'no'.");
            answer = input.nextLine().trim();
        }

        if(answer.equalsIgnoreCase("yes")) {
            displayOnAnotherFile(input, inventory);
        } else {
            System.out.println("Continuing with application...\n");
        }
    }

    public static void displayOnAnotherFile(Scanner input, ArrayList<Inventory> inventory) {
        try {
            System.out.println("What filename do you want to create?");
            String filename = input.nextLine().trim();

            if(filename.isEmpty()) {
                System.out.println("Filename cannot be empty.");
                return;
            }

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filename));

            // Sort inventory by name
            inventory.sort(Comparator.comparing(Inventory::getName));

            fileWriter.write("=== INVENTORY LIST ===\n");
            fileWriter.write("We carry the following inventory:\n\n");

            for(Inventory item : inventory) {
                if(item != null) {
                    String formattedLine = String.format("ID: %d | Name: %s | Price: $%.2f%n",
                            item.getId(), item.getName(), item.getPrice());
                    fileWriter.write(formattedLine);
                }
            }

            fileWriter.close();
            System.out.println("✓ File '" + filename + "' created successfully!\n");

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static int MenuPrompt(Scanner input) {
        System.out.println("""
=== STORE INVENTORY SYSTEM ===
What would you like to do?
1 - List all products
2 - Search product by ID
3 - Find products within price range
4 - Add a new product
5 - Quit the application
6 - Search by Name
Enter command (1-6):""");

        boolean validInput = false;
        int command = 0;

        while(!validInput) {
            try {
                command = input.nextInt();
                input.nextLine();  // Clear buffer

                if(command >= 1 && command <= 6) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a number between 1 and 5.");
                }
            } catch(InputMismatchException e) {
                input.nextLine();  // Clear invalid input
                System.out.println("Invalid input! Please enter a valid number (1-5).");
            }
        }

        return command;
    }

    public static void searchById(Scanner input, ArrayList<Inventory> inventory) {
        System.out.println("Enter the product ID to search for:");

        try {
            int searchId = input.nextInt();
            input.nextLine();

            boolean found = false;
            for(Inventory item : inventory) {
                if(item.getId() == searchId) {
                    System.out.printf("Found: ID: %d | Name: %s | Price: $%.2f%n%n",
                            item.getId(), item.getName(), item.getPrice());
                    found = true;
                    break;
                }
            }

            if(!found) {
                System.out.println("Product with ID " + searchId + " not found.\n");
            }
        } catch(InputMismatchException e) {
            input.nextLine();
            System.out.println("Invalid input! Please enter a valid ID.\n");
        }
    }

    public static void searchByPrice(Scanner input, ArrayList<Inventory> inventory) {
        System.out.println("Enter minimum price you are looking for:");
        float minPrice = input.nextFloat();

        System.out.println("Enter maximum price you are looking for:");
        float maxPrice = input.nextFloat();
        input.nextLine();

        System.out.println("\nProducts within price range $" + minPrice + " - $" + maxPrice + ":");
        boolean found = false;

        for(Inventory item : inventory) {
            if(item != null && item.getPrice() >= minPrice && item.getPrice() <= maxPrice) {
                System.out.printf("ID: %d | Name: %s | Price: $%.2f%n",
                        item.getId(), item.getName(), item.getPrice());
                found = true;
            }
        }

        if(!found) {
            System.out.println("No products found in that price range.");
        }
        System.out.println();
    }

    public static void addNewProduct(Scanner input, ArrayList<Inventory> inventory) {
        try {
            System.out.println("Enter product ID:");
            int id = input.nextInt();
            input.nextLine();

            System.out.println("Enter product name:");
            String name = input.nextLine().trim().toLowerCase();

            System.out.println("Enter product price:");
            float price = input.nextFloat();
            input.nextLine();

            Inventory newProduct = new Inventory(id, name, price);
            inventory.add(newProduct);

            System.out.println("✓ Product added successfully!\n");
        } catch(InputMismatchException e) {
            input.nextLine();
            System.out.println("Invalid input! Please try again.\n");
        }
    }
    public static void searchByName(Scanner input, HashMap<String, ArrayList<Inventory>> inventoryByName) {

        System.out.println("Enter product name:");
        String name = input.nextLine().trim().toLowerCase();
        ArrayList<Inventory> results = inventoryByName.get(name);
        System.out.println(results);

        if (results != null) {
            for (Inventory item : results) {
                System.out.printf("ID: %d | Name: %s | Price: $%.2f%n",
                        item.getId(), item.getName(), item.getPrice());
            }
            System.out.println();
        } else {
            System.out.println("Product not found.\n");
        }
    }
}

