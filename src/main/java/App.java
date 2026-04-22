package PACKAGE_NAME;

public class App {
    public static void main(String[] args) {

            ArrayList<Product> inventory = getInventory();
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
