package freiman.museum;

import com.andrewoid.ApiKey;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class MuseumFrame extends JFrame {

    private MuseumService service = new MuseumServiceFactory().getService();
    private ArtObjects collection;

    private ApiKey apiKey = new ApiKey();
    private int pageNum;
    private JTextField searchField;
    private JPanel imagesPanel;

    //private JFrame imageFrame;
    //private JPanel imageFrameMain;

    public MuseumFrame() {
        setSize(1300, 800);
        setTitle("Rijks Museum");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        setContentPane(main);

        // Create panel with buttons and search bar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton prevPageButton = new JButton("Previous Page");
        JButton nextPageButton = new JButton("Next Page");
        pageNum = 0;

        prevPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pageNum > 0) {
                    pageNum--;
                    LoadImages();
                }
            }
        });

        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageNum++;
                LoadImages();
            }
        });

        searchField = new JTextField(40);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                LoadImages();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                LoadImages();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                LoadImages();
            }
        });

        // Add components to the top panel
        topPanel.add(prevPageButton);
        topPanel.add(searchField);
        topPanel.add(nextPageButton);

        // Add the top panel to the main panel
        main.add(topPanel, BorderLayout.NORTH);

        // Create the panel for displaying images with spacing
        imagesPanel = new JPanel();
        imagesPanel.setLayout(new GridLayout(2, 5, 10, 10)); // 2 rows, 5 columns, 10px horizontal and vertical gaps
        main.add(new JScrollPane(imagesPanel), BorderLayout.CENTER);

        // Load the initial set of images
        LoadImages();
/*
        //set up displayImage
        imageFrame = new JFrame();
        imageFrame.setSize(800, 600);
        imageFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        imageFrameMain = new JPanel();
        imageFrameMain.setLayout(new BorderLayout());
        setContentPane(imageFrameMain);

 */

    }

    private void LoadImages() {
        // Clear previous images
        imagesPanel.removeAll();


        // Get the list of artObjects
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
                        label.setToolTipText(artObject.title + " by " + artObject.principalOrFirstMaker);
                        imagesPanel.add(label);
/*
                        label.addMouseListener(new MouseAdapter()
                        {
                            @Override
                            public void mouseClicked(MouseEvent e)
                            {
                                imageFrame.setTitle(artObject.title + " by " + artObject.principalOrFirstMaker);
                                Image frameScaleImage = image.getScaledInstance(800, -1, Image.SCALE_DEFAULT);
                                ImageIcon fImageIcon = new ImageIcon(frameScaleImage);
                                JLabel lab = new JLabel(fImageIcon);
                                imageFrameMain.add(lab);
                                imageFrame.setVisible(true);
                            }
                        });

 */
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
