package food.donation.app;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    public MapPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Food Donation Map");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(60, 179, 113));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        ImageIcon mapIcon = new ImageIcon(getClass().getResource("/Map/Map.png"));

        JLabel mapLabel = new JLabel(mapIcon);
        mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(mapLabel, BorderLayout.CENTER);
    }
}


