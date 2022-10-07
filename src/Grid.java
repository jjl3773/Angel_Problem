package src;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.MouseWheelEvent;
import javax.swing.event.MouseInputAdapter;
import java.util.HashSet;
import java.util.Set;
import java.awt.Point;
import java.lang.Math;

class GridsCanvas extends JPanel {
  public static final int maxZoom = 100;
  public static final int minZoom = 10;

  // width and height in pixels
  int width, height;

  // mouse coords on click
  int orgMouseX, orgMouseY;

  // current mouse coords
  int currMouseX, currMouseY;

  // previous mouse coords - stores how the view on the plane
  // is currently shifted. Similar to screenX/Y but value is
  // % by dimension.
  int prevMouseX, prevMouseY;

  // the dimension of each grid
  int dim;

  // the coords of the top left pixel on the screen
  int screenX, screenY;
  int prevScreenX, prevScreenY;

  MouseInputAdapter listener;

  // the current move of the game
  int move;

  // keep track of "eaten squares" in the form (x, y) where each
  // point corresponds to the corner of some grid square
  Set<Point> removed;

  // The player of the game
  Player player;

  // The Devil facing off against the player
  Devil devil;

  GridsCanvas(int w, int h, int d) {
    setSize(width = w, height = h);
    dim = d;
    removed = new HashSet<>();
    listener = new CustomMouseListener();
    player = new King(1);
    devil = new SimpleDevil();

    // enable frame to pick up mouse input
    addMouseListener(listener);
    addMouseMotionListener(listener);

    // work on later
    // addMouseWheelListener(listener);
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

    // loop through removed and draw the ones that are visible in the current frame
    g.setColor(Color.RED);
    for (Point point: removed) {
        int pointInCurrViewX = point.x * dim - screenX;
        int pointInCurrViewY = point.y * dim - screenY;
        if (0 - dim <= pointInCurrViewX && pointInCurrViewX < width && 0 - dim <= pointInCurrViewY && pointInCurrViewY < height) {
            g.fillRect(pointInCurrViewX + 1, pointInCurrViewY + 1, dim - 1, dim - 1);
        }
    }
    g.setColor(Color.BLACK);
  }

  class CustomMouseListener extends MouseInputAdapter {
    public CustomMouseListener() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();

        if (SwingUtilities.isRightMouseButton(e)) {
            removed.add(new Point(Math.floorDiv(point.x + screenX, dim), Math.floorDiv(point.y + screenY, dim)));
        }        
        // use floordiv to account for rounding when negative, we always
        // want to round to the lower integer
        if (player.makeMove(removed, new Point(Math.floorDiv(point.x + screenX, dim), Math.floorDiv(point.y + screenY, dim)))) {
            devil.eatSquare(move, removed, player.playerPos());
            move++;
        }
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotations = e.getWheelRotation();
        dim -= rotations;
        dim = Math.max(dim, minZoom);
        dim = Math.min(dim, maxZoom);

        repaint();
    }
  }
}