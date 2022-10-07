package src;
import javax.swing.JFrame;

/*
 * The Driver code for the game which contains the main method
 */
public class Driver extends JFrame {
  public Driver() {
    GridsCanvas xyz = new GridsCanvas(1000, 1000, 15);
    add(xyz);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // setExtendedState (java.awt.Frame.MAXIMIZED_BOTH);
  }

  public static void main(String[] a) {
    new Driver().setVisible(true);
  }
}