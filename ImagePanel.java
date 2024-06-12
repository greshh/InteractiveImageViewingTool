/*
  JPanel class to read and display an image. When using IntelliJ this
  will be in a separate class file that you create.
*/

//package nz.ac.massey.a1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Class to read and display an image. Images are displayed in a
// JPanel, so we make our class extend one.
public class ImagePanel extends JPanel {

    BufferedImage image = null;

    // A method to read an image from a file handle object. Note here
    // that we MUST handle the IOException
    public void getImage(File imageFile) {
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This gets called by the JVM whenever it needs to do so. For
    // example, when rendering a JFrame, this method will be called
    // for all JPanels that have been added to the JFrame. Here we
    // override the paintComponent() method in the parent class (which
    // actually does nothing)
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // If there is an image to draw, then draw it!
        if (image != null) {
            // Call the appropriate g2 methods;
        }
    }

    // Note: there is another important method in the JPanel called
    // .repaint(). You do not override it here, but you call in your
    // own JPanel instances if you want the get the JVM to call
    // paintComponent() again

}