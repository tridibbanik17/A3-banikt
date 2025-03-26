package ca.mcmaster.se2aa4.mazerunner;

import java.util.HashMap;
import java.util.Map;

// Enum representing the four cardinal directions.
public enum Direction {
    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W");

    // Lookup map for symbol-based direction retrieval.
    private static final Map<String, Direction> SYMBOL_MAP = new HashMap<>();

    static {
        for (Direction dir : values()) {
            SYMBOL_MAP.put(dir.symbol, dir);
        }
    }

    private final String symbol;

    // Associates a direction with its symbol.
    Direction(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    // Returns the next direction in clockwise order.
    public Direction turnRight() {
        return values()[(ordinal() + 1) % values().length];
    }

    // Returns the next direction in counterclockwise order.
    public Direction turnLeft() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }

    // Returns the next direction as the oppoite direction (180 deg difference)
    public Direction turnAround() {
        return values()[(ordinal() + 2) % values().length];
    }

    // Factory method to retrieve a Direction enum from its symbol.
    public static Direction fromSymbol(String symbol) {
        Direction dir = SYMBOL_MAP.get(symbol);
        if (dir == null) {
            throw new IllegalArgumentException("Invalid direction symbol: " + symbol);
        }
        return dir;
    }
}
