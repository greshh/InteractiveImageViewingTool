/* 
   The main class file for your program

   This class, together with the ImagePanel class provides a minimal
   demo on how to read and display an image in a JPanel.
   
   You can use this as a starting point for Assignment 1. You can
   change package, class, and variable names as you see fit.

*/

//package nz.ac.massey.a1;

import javax.swing.*;
import java.awt.*;
import java.io.File;

// Recommended to make your program class a JFrame
public class ImageViewer extends JFrame {

    public ImageViewer() {
        super("Simple Image Display");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Instantiate our image panel class and add this panel to the
        // content pane of the JFrame
        ImagePanel imagePanel = new ImagePanel();
        Container content = this.getContentPane();
        content.add(imagePanel);

        // Read the image into the panel. The image file must be at
        // the top level of your project directory. Here the image file
        // is hard-coded, but we will want to be able to get the file
        // name from a file chooser activated through the File menu
        imagePanel.getImage(new File("dachshund.jpg"));

        this.setSize(400, 400);

        // By making our JFrame visible, the JVM will call the
        // paintComponent() method of any JPanels registered to the
        // JFrame
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ImageViewer();
    }
}