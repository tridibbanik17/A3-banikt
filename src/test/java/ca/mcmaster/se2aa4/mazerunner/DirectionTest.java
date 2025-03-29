package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void testDirectionTurnRight() {
        Direction direction = Direction.EAST;
        assertEquals(Direction.SOUTH, direction.turnRight());
    }

    @Test
    void testDirectionTurnLeft() {
        Direction direction = Direction.NORTH;
        assertEquals(Direction.WEST, direction.turnLeft());
    }

    @Test
    void testDirectionTurnAround() {
        Direction direction = Direction.NORTH;
        assertEquals(Direction.SOUTH, direction.turnAround());
    }

    @Test
    void testPositionEquality() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(1, 2);
        assertEquals(pos1, pos2);
    }
}
