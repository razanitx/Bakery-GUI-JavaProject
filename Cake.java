 import java.io.*;

 class Cake extends BakeryItem  implements Serializable {
    private int numOfTiers;

    public Cake(String flavor, char size, int numOfTiers) {
        super(flavor, size);
        this.numOfTiers = numOfTiers;
    }

    @Override
    public double calculatePrice() {
        double basePrice = (size == 'S') ? 50 : (size == 'M') ? 100 : 130;
        return basePrice + (numOfTiers > 1 ? 30 * (numOfTiers - 1) : 0);
    }

    @Override
    public String toString() {
        return "Cake - " + super.toString() + ", Tiers: " + numOfTiers + ", Price: " + calculatePrice() + " SAR";
    }
}
