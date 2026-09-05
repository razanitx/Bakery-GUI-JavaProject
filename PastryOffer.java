import java.io.*;

 class PastryOffer extends Pastry  implements Serializable{
    private String coffeeType;

    public PastryOffer(String flavor, char size, String fillingType, String coffeeType) {
        super(flavor, size, fillingType);
        this.coffeeType = coffeeType;
    }

    @Override
    public String toString() {
        return "Pastry with Offer - " + super.toString() + ", Coffee: " + coffeeType + " (Free)";
    }
}
