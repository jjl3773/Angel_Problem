package src;

import java.util.Set;
import java.awt.Point;

// The base class for the Devil in this game. Every time
// the player makes a move, the Devil can eat a square, rendering
// it restricted from the player to move to at any point in the
// future. The Devil can remove any square on the infinite grid.
public interface Devil {
    /**
     * This method should be called after the player makes a move. Should
     * add a new entry into the "removed" set.
     * @param move the current move number of the game
     * @param removed the current squares that have already been eaten
     * @param playerPos the current position of the player
     */
    public void eatSquare(int move, Set<Point> removed, Point playerPos);
}
