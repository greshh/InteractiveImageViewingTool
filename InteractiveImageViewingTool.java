import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JMenu;
import java.awt.event.MouseMotionListener;

public class InteractiveImageViewingTool extends JFrame implements ActionListener, MouseMotionListener {
    JMenuItem openItem, quitItem;
    BufferedImage image = null;

    JPanel buttonsPanel = new JPanel();
    JPanel valuesPanel = new JPanel();
    JPanel imagePanel = new Image();

    double scaleX = 1.0;
    double scaleY = 1.0;

    JTextField xField = new JTextField(3);
    JTextField yField = new JTextField(3);
    JTextField rField = new JTextField(3);
    JTextField gField = new JTextField(3);
    JTextField bField = new JTextField(3);
    JTextField aField = new JTextField(3);

    boolean flippedX = false; 
    boolean flippedY = false;
    boolean flippedXY = false;
    boolean negated = false;

    public static void main(String[] args) {
        new InteractiveImageViewingTool();
    }

    public InteractiveImageViewingTool() {
        super("Interactive Image Viewing Tool");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        

        this.setLayout(new GridBagLayout());
        //this.setLayout(new GridLayout(4, 0));

        /* creating menu */

        JMenuBar menu = new JMenuBar();
        this.setJMenuBar(menu);

        JMenu fileMenu = new JMenu("File");
        menu.add(fileMenu);

        openItem = new JMenuItem("Open");
        openItem.addActionListener(this);

        quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(quitItem);

        /* creating buttons panel */

        JButton oButton = new JButton("O");
        oButton.addActionListener(e->{
            if (image != null) {
                if (flippedX) {
                    scaleX*= -1;
                    flippedX = false;
                }
                if (flippedY) {
                    scaleY*= -1;
                    flippedY = false;
                }
                if (flippedXY) {
                    scaleX*= -1;
                    scaleY*= -1;
                    flippedXY = false;
                }
                if (negated) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        for (int y = 0; y < image.getHeight(); y++) {
                            int rgb = image.getRGB(x, y);
                            rgb =  rgb ^ 0x00ffffff;
                            image.setRGB(x, y, rgb);
                        }
                    }
                    negated = false;
                }
                imagePanel.repaint();
            }
        });

        JButton flipXButton = new JButton("flipX");
        flipXButton.addActionListener(e-> {
            if (image != null) {
                scaleX*= -1;
                imagePanel.repaint();
                if (flippedX) {
                    flippedX = false;
                } else {
                    flippedX = true;
                }
            }
        });

        JButton flipYButton = new JButton("flipY");
        flipYButton.addActionListener(e-> {
            if (image != null) {
                scaleY*= -1;
                imagePanel.repaint();
                if (flippedY) {
                    flippedY = false;
                } else {
                    flippedY = true;
                }
            }
        });

        JButton flipXYButton = new JButton("flipXY");
        flipXYButton.addActionListener(e-> {
            if (image != null) {
                scaleX*= -1;
                scaleY*= -1;
                imagePanel.repaint();
                if (flippedXY) {
                    flippedXY = false;
                } else {
                    flippedXY = true;
                }
            }
        });

        JButton negateButton = new JButton("negate");
        negateButton.addActionListener(e-> {
            if (image != null) {
                for (int x = 0; x < image.getWidth(); x++) {
                    for (int y = 0; y < image.getHeight(); y++) {
                        int rgb = image.getRGB(x, y);
                        rgb =  rgb ^ 0x00ffffff;
                        image.setRGB(x, y, rgb);
                    }
                }
                imagePanel.repaint();
                if (negated) {
                    negated = false;
                } else {
                    negated = true;
                }
            }
        });

        buttonsPanel.add(oButton);
        buttonsPanel.add(flipXButton);
        buttonsPanel.add(flipYButton);
        buttonsPanel.add(flipXYButton);
        buttonsPanel.add(negateButton);

        /* creating values panel */

        JLabel xLabel = new JLabel("X");
        JLabel yLabel = new JLabel("Y");
        JLabel rLabel = new JLabel("R");
        JLabel gLabel = new JLabel("G");
        JLabel bLabel = new JLabel("B");
        JLabel aLabel = new JLabel("A");

        xField.setEditable(false);
        yField.setEditable(false);
        rField.setEditable(false);
        gField.setEditable(false);
        bField.setEditable(false);
        aField.setEditable(false);

        valuesPanel.add(xLabel); valuesPanel.add(xField);
        valuesPanel.add(yLabel); valuesPanel.add(yField);
        valuesPanel.add(rLabel); valuesPanel.add(rField);
        valuesPanel.add(gLabel); valuesPanel.add(gField);
        valuesPanel.add(bLabel); valuesPanel.add(bField);
        valuesPanel.add(aLabel); valuesPanel.add(aField);

        imagePanel.addMouseMotionListener(this);

        this.setSize(600, 600);        
        this.add(buttonsPanel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2), 2, 2));
        this.add(valuesPanel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2), 2, 2));
        this.add(imagePanel, new GridBagConstraints(0, 2, 1, 1, 100, 100, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2,2,2,2), 100, 100));
        this.setResizable(true);  
        this.setVisible(true);
    }

    class Image extends JPanel {
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(new Color(255, 255, 255, 255));
            g2.fillRect(0, 0, imagePanel.getWidth(), imagePanel.getHeight());
            if (image != null) {
                g2.scale(scaleX, scaleY);
                if (scaleX < 0) {
                    g2.translate(-image.getWidth(), 0);
                }
                if (scaleY < 0) {
                    g2.translate(0, -image.getHeight());
                }
                g2.drawImage(image, 0, 0, null);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        JComponent clicked = (JComponent)e.getSource();

        if (clicked == openItem) {
            JFileChooser chooser = new JFileChooser("./");
            int choose = chooser.showOpenDialog(this);

            if (choose == JFileChooser.APPROVE_OPTION) {
                try {
                    image = ImageIO.read(new File(chooser.getSelectedFile().getPath()));
                    imagePanel.repaint();
                    scaleX = 1.0;
                    scaleY = 1.0;
                    flippedX = false;
                    flippedY = false;
                    flippedXY = false;
                    negated = false;
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        } else if (clicked == quitItem) {
            System.exit(10);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) { mouseMoved(e); }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        xField.setText(Integer.toString(x));
        yField.setText(Integer.toString(y));
        if ((image != null) && (x > 0) && (x < image.getWidth()) && (y > 0) && (y < image.getHeight())) {
            Color rgbValue = new Color(image.getRGB(x, y), true);
            int r = rgbValue.getRed();
            int g = rgbValue.getGreen();
            int b = rgbValue.getBlue();
            int a = rgbValue.getAlpha();
            rField.setText(Integer.toString(r));
            gField.setText(Integer.toString(g));
            bField.setText(Integer.toString(b));
            aField.setText(Integer.toString(a));
        } else {
            rField.setText("");
            gField.setText("");
            bField.setText("");
            aField.setText("");
        }
    }
}
