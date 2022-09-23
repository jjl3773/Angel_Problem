package src;

import java.util.Set;
import java.awt.Point;
import java.util.HashSet;
import java.awt.Image;

// The base class for a player in this game. This class is
// meant to be inherited by another class to provide specific
// movement options for the player.
public class Player {
    // in terms of grid squares
    int currX, currY;
    // Inheriting classes should define the valid squares this player is able
    // to move to
    private Set<Point> squaresToMove;
    private Image image;

    public Player() {
        this.squaresToMove = new HashSet<>();
    }

    /*
     * View frame should call this method when a move is inputted by the player
     * @param 
     * @returns whether or not the player made a legal move. If the move was legal,
     * the player's position is adjusted accordingly.
     */
    public boolean makeMove(Set<Point> deleted, Point pos) {
        if (deleted.contains(pos)) {
            return false;
        }

        if (squaresToMove.contains(new Point(pos.x - currX, pos.y - currY))) {
            currX = pos.x;
            currY = pos.y;
            return true;
        }
        return false;
    }

    public Set<Point> getSquaresToMove() {
        return squaresToMove;
    }

    public Point playerPos() {
        return new Point(currX, currY);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
