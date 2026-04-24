public class Inventory {
        private final int id;
        private final String name;
        private final float price;

    public Inventory(int id, String name, float price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
        public int getId() {
            return this.id;
        }
        public String getName() {
            return this.name;
        }
        public float getPrice() {
            return this.price;
        }
        public String toString(){
        return "Id:"+id +"Product Name:"+name+"Price:"+price+"\n";
        }
    }

