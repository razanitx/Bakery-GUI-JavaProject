 import java.io.*;

 class Cookie extends BakeryItem  implements Serializable {
    private int pieces;
    private boolean hasToppings;

    public Cookie(String flavor, char size, int pieces, boolean hasToppings) {
        super(flavor, size);
        this.pieces = pieces;
        this.hasToppings = hasToppings;
    }

    @Override
    public double calculatePrice() {
        double basePrice = (size == 'S') ? 10 : (size == 'M') ? 15 : 20;
        return basePrice + (hasToppings ? 5 : 0) + (pieces * 2);
    }

    @Override
    public String toString() {
        return "Cookie - " + super.toString() + ", Pieces: " + pieces + ", Toppings: " + (hasToppings ? "Yes" : "No")
                + ", Price: " + calculatePrice() + " SAR";
    }
}
