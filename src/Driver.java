package src;
import javax.swing.JFrame;

public class Driver extends JFrame {
  public Driver() {
    GridsCanvas xyz = new GridsCanvas(1000, 1000, 50);
    add(xyz);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // setExtendedState (java.awt.Frame.MAXIMIZED_BOTH);
  }

  public static void main(String[] a) {
    new Driver().setVisible(true);
  }
}