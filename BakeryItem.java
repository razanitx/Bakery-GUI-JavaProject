import java.io.*;

abstract class BakeryItem  implements Serializable {
    protected String flavor;
    protected char size;

    public BakeryItem(String flavor, char size) {
        this.flavor = flavor;
        this.size = size;
    }

    public abstract double calculatePrice();

    public String getFlavor() {
        return flavor;
    }

    @Override
    public String toString() {
        return "Flavor: " + flavor + ", Size: " + size;
    }
}
