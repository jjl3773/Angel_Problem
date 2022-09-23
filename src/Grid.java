package src;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
// import javax.swing.event.MouseWheelListener;
import java.util.HashSet;
import java.util.Set;
import java.awt.Point;
import java.lang.Math;

class GridsCanvas extends JPanel {
  // width and height in pixels (?)
  int width, height;

  // mouse coords on click
  int orgMouseX, orgMouseY;

  // current mouse coords
  int currMouseX, currMouseY;

  // previous mouse coords - stores how the view on the plane
  // is currently shifted
  int prevMouseX, prevMouseY;

  // the dimension of each grid
  int dim;

  // the coords of the top left pixel on the screen
  int screenX, screenY;
  int prevScreenX, prevScreenY;

  MouseInputListener listener;
  // keep track of points in the form (x, y) where each
  // point corresponds to the corner of some grid square
  Set<Point> points;

  // The player of the game
  Player player;

  GridsCanvas(int w, int h, int d) {
    setSize(width = w, height = h);
    dim = d;
    points = new HashSet<>();
    listener = new CustomMouseListener();
    player = new King(1);

    // enable frame to pick up mouse input
    addMouseListener(listener);
    addMouseMotionListener(listener);
    // addMouseWheelListener();
  }

  @Override
  public void paintComponent(Graphics g) {
    width = getSize().width;
    height = getSize().height;

    // draw grid lines
    int i = 0;
    while (dim * i + currMouseY < height) {
      g.drawLine(0, i * dim + currMouseY, width, i * dim + currMouseY);
      i++;
    }


    i = 0;
    while (dim * i + currMouseX < width) {
      g.drawLine(i * dim + currMouseX, 0, i * dim + currMouseX, height);
      i++;
    }

    // draw player valid positions
    g.setColor(Color.GRAY);
    Point playerPos = player.playerPos();
    for (Point point: player.getSquaresToMove()) {
        g.fillRect((playerPos.x + point.x) * dim - screenX + 1, (playerPos.y + point.y) * dim - screenY + 1, dim - 1, dim - 1);
    }
    g.drawImage(player.getImage(), playerPos.x * dim - screenX + 1 + dim/10, playerPos.y * dim - screenY + 1 + dim/10, (dim * 8/10) - 1, (dim * 8/10) - 1, getBackground(), null);

    // loop through points and draw the ones that are visible in the current frame
    g.setColor(Color.RED);
    for (Point point: points) {
        int pointInCurrViewX = point.x * dim - screenX;
        int pointInCurrViewY = point.y * dim - screenY;
        if (0 - dim <= pointInCurrViewX && pointInCurrViewX < width && 0 - dim <= pointInCurrViewY && pointInCurrViewY < height) {
            g.fillRect(pointInCurrViewX + 1, pointInCurrViewY + 1, dim - 1, dim - 1);
        }
    }
    g.setColor(Color.BLACK);
  }

  class CustomMouseListener implements MouseInputListener {
    public CustomMouseListener() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();

        if (SwingUtilities.isRightMouseButton(e)) {
            points.add(new Point(Math.floorDiv(point.x + screenX, dim), Math.floorDiv(point.y + screenY, dim)));
        }        
        // use floordiv to account for rounding when negative, we always
        // want to round to the lower integer
        player.makeMove(points, new Point(Math.floorDiv(point.x + screenX, dim), Math.floorDiv(point.y + screenY, dim)));
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        prevMouseX  = currMouseX;
        prevMouseY = currMouseY;
        prevScreenX = screenX;
        prevScreenY = screenY;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int diffX = e.getX() - orgMouseX;
        int diffY = e.getY() - orgMouseY;

        // update absolute screen position
        screenX = prevScreenX - diffX;
        screenY = prevScreenY - diffY;

        // update current coords accounting the distortion existing
        currMouseX = (diffX + prevMouseX) % dim;
        currMouseY = (diffY + prevMouseY) % dim;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        orgMouseX = e.getX();
        orgMouseY = e.getY();
    }
  }
}