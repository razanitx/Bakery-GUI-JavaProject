 import java.io.*;

 class Pastry extends BakeryItem  implements Serializable {
    private String fillingType;

    public Pastry(String flavor, char size, String fillingType) {
        super(flavor, size);
        this.fillingType = fillingType;
    }

    @Override
    public double calculatePrice() {
        return (size == 'S') ? 10 : (size == 'M') ? 15 : 20;
    }

    @Override
    public String toString() {
        return "Pastry - " + super.toString() + ", Filling: " + fillingType + ", Price: " + calculatePrice() + " SAR";
    }
}
