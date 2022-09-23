package src;

import java.awt.Point;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

/*
 * A k-King is defined as a player that is able to make
 * "k" king moves as a single move. A small caveat is that the
 * king should not be able to return its original starting position
 * at the start of the move.
 */
public class King extends Player {
    int power;

    public King(int power) {
        super();
        this.power = power;
        // work on logic later
        // for (int i = -1 * power; i <= power; i++) {
        //     for (int j = -1 * power; j <= power; j++) {
        //         this.squaresToMove.add(new Point(i, j));
        //     }
        // }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <=1 ;j++) {
                if (!(i == 0 && j == 0)) {
                    this.getSquaresToMove().add(new Point(i, j));
                }
            }
        }

        try {
            // no slash before folder name(?) tbh path stuff still confuses me
            Image image = ImageIO.read(getClass().getResource("resources/king.png"));
            setImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
