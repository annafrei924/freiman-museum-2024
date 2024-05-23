package freiman.museum;

import com.andrewoid.ApiKey;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

public class MuseumFrame extends JFrame {

    private final MuseumService service = new MuseumServiceFactory().getService();
    private final ApiKey apiKey = new ApiKey();
    private int pageNum;
    private final JTextField searchField;
    private JPanel imagesPanel;


    public MuseumFrame() {
        setSize(1300, 800);
        setTitle("Rijks Museum");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        setContentPane(main);

        // Create panel with buttons and search bar, and add listener
        JButton prevPageButton = new JButton("Previous Page");
        JButton nextPageButton = new JButton("Next Page");

        pageNum = 0;

        prevPageButton.addActionListener(e -> {
            if (pageNum > 0) {
                pageNum--;
                loadImages();
            }
        });

        nextPageButton.addActionListener(e -> {
            pageNum++;
            loadImages();
        });

        searchField = new JTextField(40);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadImages();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadImages();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadImages();
            }
        });

        // Add components to the top panel and then to main panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(prevPageButton);
        topPanel.add(searchField);
        topPanel.add(nextPageButton);
        main.add(topPanel, BorderLayout.NORTH);

        // create panel to display art with grid spacing
        imagesPanel = new JPanel();
        imagesPanel.setLayout(new GridLayout(2, 5, 10, 10)); // 2 rows, 5 columns, 10px horizontal and vertical gaps
        main.add(new JScrollPane(imagesPanel), BorderLayout.CENTER);

        loadImages();

    }

    private void loadImages() {
        imagesPanel.removeAll();

        ArtObjects collection;

        //get artObjects
        try {
            if (searchField.getText().isEmpty()) {
                collection = service.page(apiKey.get(), pageNum).blockingGet();
                System.out.println("Length:" + collection.artObjects.length);
            } else {
                collection = service.query(apiKey.get(), pageNum, searchField.getText()).blockingGet();
            }

            // Display the images
            for (int i = 0; i < collection.artObjects.length && i < 10; i++) {
                ArtObject artObject = collection.artObjects[i];
                try {
                    URL url = new URL(artObject.webImage.url);
                    Image image = ImageIO.read(url);
                    if (image != null) {
                        Image scaledImage = image.getScaledInstance(200, -1, Image.SCALE_DEFAULT);
                        ImageIcon imageIcon = new ImageIcon(scaledImage);
                        JLabel label = new JLabel(imageIcon);
                        String title = artObject.title + " by " + artObject.principalOrFirstMaker;
                        label.setToolTipText(title);
                        imagesPanel.add(label);

                        //on click, open other frame
                        label.addMouseListener(new MouseAdapter()
                        {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                ImageFrame imageFrame = new ImageFrame(title, image);
                                imageFrame.show();

                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Refresh the display
        imagesPanel.revalidate();
        imagesPanel.repaint();
    }
}
