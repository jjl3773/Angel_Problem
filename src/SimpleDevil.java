package src;

import java.awt.Point;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

// The simple devil is a self-made concoction inspired by
// the analysis done in Martin Kutz's Phd Thesis. This devil
// is neither fast nor efficient, but should get the job done
// against a 1-King.
public class SimpleDevil implements Devil {
    private static final int STRATEGY_MOVE_COUNT = 44;
    private static final int WALL_LENGTH = 6;
    private static final int[][] transformations = new int[][]{new int[]{1, 1}, new int[]{1, -1}, new int[]{-1, 1}, new int[]{-1, -1}};

    List<Point> strategy;

    public SimpleDevil() {
        strategy = new ArrayList<>();
        for (int[] transform: transformations) {
            strategy.add(new Point(transform[0] * (STRATEGY_MOVE_COUNT + 1), transform[1] * (STRATEGY_MOVE_COUNT + 1)));
            for (int i = 1; i < WALL_LENGTH; i++) {
                strategy.add(new Point(transform[0] * (STRATEGY_MOVE_COUNT + 1 + i), transform[1] * (STRATEGY_MOVE_COUNT + 1)));
                strategy.add(new Point(transform[0] * (STRATEGY_MOVE_COUNT + 1), transform[1] * (STRATEGY_MOVE_COUNT + i + 1)));
            }
        }
    }

    @Override
    public void eatSquare(int move, Set<Point> removed, Point playerPos) {
        if (move < STRATEGY_MOVE_COUNT) {
            eatSquareStrategic(removed, move);
        } else {
            eatSquarePositional(removed, playerPos);
        }
    }

    private void eatSquareStrategic(Set<Point> removed, int move) {
        removed.add(strategy.get(move));
    }

    private void eatSquarePositional(Set<Point> removed, Point playerPos) {
        Point toAdd;
        if (Math.abs(playerPos.getX()) > Math.abs(playerPos.getY())) {
            if (playerPos.getX() >= 0) {
                toAdd = (new Point(STRATEGY_MOVE_COUNT + WALL_LENGTH, (int) playerPos.getY()));
            } else {
                toAdd = new Point(-1 * (STRATEGY_MOVE_COUNT + WALL_LENGTH), (int) playerPos.getY());
            }
            if (!playerPos.equals(toAdd) && !removed.contains(toAdd)) {
                removed.add(toAdd);
            } else {
                if (!removed.contains(new Point((int) toAdd.getX(), (int) toAdd.getY() + 1))) {
                    removed.add(new Point((int) toAdd.getX(), (int) toAdd.getY() + 1));
                } else {
                    removed.add(new Point((int) toAdd.getX(), (int) toAdd.getY() - 1));
                }
            }

        } else {
            if (playerPos.getY() >= 0) {
                toAdd = new Point((int) playerPos.getX(), STRATEGY_MOVE_COUNT + WALL_LENGTH);
            } else {
                toAdd = new Point((int) playerPos.getX(), -1 * (STRATEGY_MOVE_COUNT + WALL_LENGTH));
            }
            if (!playerPos.equals(toAdd) && !removed.contains(toAdd)) {
                removed.add(toAdd);
            } else {
                if (!removed.contains(new Point((int) toAdd.getX() + 1, (int) toAdd.getY()))) {
                    removed.add(new Point((int) toAdd.getX() + 1, (int) toAdd.getY()));
                } else {
                    removed.add(new Point((int) toAdd.getX() - 1, (int) toAdd.getY()));
                }
            }
        }
    }
}
