package freiman.museum;

import javax.swing.*;
import java.awt.*;

public class ImageFrame extends JFrame {

    public ImageFrame(String title, Image image) {

        setSize(800, 800);
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        setContentPane(main);

        Image scaledImage = image.getScaledInstance(800, -1, Image.SCALE_DEFAULT);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(imageIcon);

        JScrollPane scrollPane = new JScrollPane(label);
        main.add(scrollPane, BorderLayout.CENTER);
    }
}