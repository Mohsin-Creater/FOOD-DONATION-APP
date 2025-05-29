package food.donation.app;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ViewDonationsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private String currentUser;
    private static final String DONATION_FILE = "donations.dat";
    private ArrayList<Donation> donations;
    private Image backgroundImage;

    public ViewDonationsPanel(String currentUser) {
        this.currentUser = currentUser;
        donations = loadDonations();

        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("src/food/donation/app/icons/p1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }

        setLayout(new BorderLayout());
        setOpaque(false); // Allow background image to show

        // Title label
        JLabel title = new JLabel("MY DONATIONS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setOpaque(false);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Table and model
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Donor Name", "Phone", "Food Type", "Specific Food", "Quantity", "Location", "Notes"
        });

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setRowHeight(24);
        table.setOpaque(false);
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void refreshTable() {
        donations = loadDonations();
        model.setRowCount(0);
        for (Donation d : donations) {
            if (d.getUsername().equals(currentUser)) {
                model.addRow(new Object[]{
                        d.getDonorName(),
                        d.getPhone(),
                        d.getFoodType(),
                        d.getSpecificFood(),
                        d.getQuantity(),
                        d.getLocation(),
                        d.getNotes()
                });
            }
        }
    }

    private ArrayList<Donation> loadDonations() {
        File file = new File(DONATION_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Donation>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
