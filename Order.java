import java.io.*;

class Order implements Serializable {
    private String name;
    private int phoneNumber;
    private BakeryItem[] desserts;
    private int numOfDesserts;

    public Order(String name, int phoneNumber, int maxItems) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.desserts = new BakeryItem[maxItems];
        this.numOfDesserts = 0;
    }

    public boolean addDessert(BakeryItem dessert) {
        if (numOfDesserts < desserts.length) {
            desserts[numOfDesserts++] = dessert;
            return true;
        }
        return false;
    }

    public boolean removeDessert(String flavor) {
        for (int i = 0; i < numOfDesserts; i++) {
            if (desserts[i].getFlavor().equalsIgnoreCase(flavor)) {
                for (int j = i; j < numOfDesserts - 1; j++) {
                    desserts[j] = desserts[j + 1];
                }
                desserts[--numOfDesserts] = null;
                return true;
            }
        }
        return false;
    }

    public BakeryItem searchDessert(String flavor) {
        for (int i = 0; i < numOfDesserts; i++) {
            if (desserts[i].getFlavor().equalsIgnoreCase(flavor)) {
                return desserts[i];
            }
        }
        return null;
    }

    public double getTotalCost() {
        double total = 0;
        for (int i = 0; i < numOfDesserts; i++) {
            total += desserts[i].calculatePrice();
        }
        return total;
    }

    public void clearOrder() {
        numOfDesserts = 0;
        desserts = new BakeryItem[desserts.length];
    }

    public String viewOrder() {
        if (numOfDesserts == 0) {
            return "No desserts in the order.";
        }
        StringBuilder sb = new StringBuilder("Order Details:\n");
        sb.append("Customer Name: ").append(name).append("\n");
        sb.append("Phone Number: ").append(phoneNumber).append("\n");
        sb.append("Desserts:\n");
        for (int i = 0; i < numOfDesserts; i++) {
            sb.append("- ").append(desserts[i].toString()).append("\n");
        }
        return sb.toString();
    }

    public int getNumOfDesserts() {
        return numOfDesserts;
    }

    public int getMaxItems() {
        return desserts.length;
    }

    // Save the order to a file
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("orderData.ser"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error saving the order: " + e.getMessage());
        }
    }

    // Load an order from a file
    public static Order loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("orderData.ser"))) {
            return (Order) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading the order: " + e.getMessage());
            return null;
        }
    }
}
