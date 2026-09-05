import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BakeryWebsite extends JFrame implements ActionListener {

    private JTextField nameField, phoneField, maxItemsField;
    private JTextArea orderArea;
    private JButton addDessertButton, viewOrderButton, clearOrderButton, totalCostButton, removeDessertButton, searchDessertButton;
    private Order currentOrder;

    public BakeryWebsite() {
        setTitle("Bakery Order System");
        setSize(500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Input Fields
        add(new JLabel("Customer Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Phone Number:"));
        phoneField = new JTextField(20);
        add(phoneField);

        add(new JLabel("Max Items:"));
        maxItemsField = new JTextField(5);
        add(maxItemsField);

        // Buttons
        addDessertButton = new JButton("Add Dessert");
        addDessertButton.addActionListener(this);
        add(addDessertButton);

        viewOrderButton = new JButton("View Order");
        viewOrderButton.addActionListener(this);
        add(viewOrderButton);

        totalCostButton = new JButton("Total Cost");
        totalCostButton.addActionListener(this);
        add(totalCostButton);

        removeDessertButton = new JButton("Remove Dessert");
        removeDessertButton.addActionListener(this);
        add(removeDessertButton);

        searchDessertButton = new JButton("Search Dessert");
        searchDessertButton.addActionListener(this);
        add(searchDessertButton);

        clearOrderButton = new JButton("Clear Order");
        clearOrderButton.addActionListener(this);
        add(clearOrderButton);

        // Order Display
        orderArea = new JTextArea(15, 40);
        orderArea.setEditable(false);
        add(new JScrollPane(orderArea));

        // Load last saved order on startup
        currentOrder = Order.loadFromFile();
        if (currentOrder != null) {
            orderArea.setText(currentOrder.viewOrder());
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDessertButton) {
            handleAddDessert();
        } else if (e.getSource() == viewOrderButton) {
            handleViewOrder();
        } else if (e.getSource() == totalCostButton) {
            handleTotalCost();
        } else if (e.getSource() == removeDessertButton) {
            handleRemoveDessert();
        } else if (e.getSource() == searchDessertButton) {
            handleSearchDessert();
        } else if (e.getSource() == clearOrderButton) {
            handleClearOrder();
        }

        // Save the order automatically after every action
        if (currentOrder != null) {
            currentOrder.saveToFile();
        }
    }

    private void handleAddDessert() {
        if (currentOrder == null) {
            try {
                String name = nameField.getText();
                int phone = Integer.parseInt(phoneField.getText());
                int maxItems = Integer.parseInt(maxItemsField.getText());
                currentOrder = new Order(name, phone, maxItems);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for phone or max items.");
                return;
            }
        }

        if (currentOrder.getNumOfDesserts() >= currentOrder.getMaxItems()) {
            JOptionPane.showMessageDialog(this, "Cannot add more desserts! Maximum limit reached.");
            return;
        }

        String[] options = {"Cake", "Cookie", "Pastry", "Pastry with Offer"};
        String choice = (String) JOptionPane.showInputDialog(this, "Select a Dessert:", "Add Dessert",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == null) return;

        try {
            String flavor = JOptionPane.showInputDialog(this, "Enter flavor:");
            char size = JOptionPane.showInputDialog(this, "Enter size (S/M/L):").toUpperCase().charAt(0);

            if (choice.equals("Cake")) {
                int tiers = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of tiers:"));
                currentOrder.addDessert(new Cake(flavor, size, tiers));
            } else if (choice.equals("Cookie")) {
                int pieces = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of pieces:"));
                boolean hasToppings = JOptionPane.showConfirmDialog(this, "Toppings?", "Cookie", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                currentOrder.addDessert(new Cookie(flavor, size, pieces, hasToppings));
            } else if (choice.equals("Pastry")) {
                String filling = JOptionPane.showInputDialog(this, "Enter filling type:");
                currentOrder.addDessert(new Pastry(flavor, size, filling));
            } else if (choice.equals("Pastry with Offer")) {
                String filling = JOptionPane.showInputDialog(this, "Enter filling type:");
                String coffee = JOptionPane.showInputDialog(this, "Enter coffee type:");
                currentOrder.addDessert(new PastryOffer(flavor, size, filling, coffee));
            }

            JOptionPane.showMessageDialog(this, "Dessert added successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void handleViewOrder() {
        if (currentOrder == null) {
            orderArea.setText("No order placed.");
        } else {
            orderArea.setText(currentOrder.viewOrder());
        }
    }

    private void handleTotalCost() {
        if (currentOrder == null) {
            JOptionPane.showMessageDialog(this, "No order placed.");
        } else {
            JOptionPane.showMessageDialog(this, "Total Cost: " + currentOrder.getTotalCost() + " SAR");
        }
    }

    private void handleRemoveDessert() {
        if (currentOrder == null) {
            JOptionPane.showMessageDialog(this, "No order to modify.");
            return;
        }
        String flavor = JOptionPane.showInputDialog(this, "Enter the flavor of the dessert to remove:");
        if (flavor != null && currentOrder.removeDessert(flavor)) {
            JOptionPane.showMessageDialog(this, "Dessert removed successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "No dessert found with the given flavor.");
        }
    }

    private void handleSearchDessert() {
        if (currentOrder == null) {
            JOptionPane.showMessageDialog(this, "No order to search.");
            return;
        }
        String flavor = JOptionPane.showInputDialog(this, "Enter the flavor of the dessert to search:");
        BakeryItem found = currentOrder.searchDessert(flavor);
        if (found != null) {
            JOptionPane.showMessageDialog(this, "Found Dessert: " + found.toString());
        } else {
            JOptionPane.showMessageDialog(this, "No dessert found with the given flavor.");
        }
    }

    private void handleClearOrder() {
        if (currentOrder != null) {
            currentOrder.clearOrder();
            orderArea.setText("Order cleared.");
        }
    }

    public static void main (String[] args) {
        new BakeryWebsite();
    }
}
