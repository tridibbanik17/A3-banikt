package ca.mcmaster.se2aa4.mazerunner;

/**
 * Default implementation of the MazeNavigation strategy for RightHandNavigation.
 */
public class DefaultMazeNavigationStrategy implements MazeNavigation {
    private final RightHandNavigation mazeSolver;

    public DefaultMazeNavigationStrategy(RightHandNavigation mazeSolver) {
        this.mazeSolver = mazeSolver;
    }

    @Override
    public boolean canMoveForward() {
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Maze maze = mazeSolver.getMaze();
        int currentRow = mazeSolver.getCurrentPosition().getRow();
        int currentCol = mazeSolver.getCurrentPosition().getCol();
        if (currentDirection != null) // Checks if the solver can move forward based on current direction
        switch (currentDirection) {
            case NORTH -> {
                if ((maze.returnCellValue(currentRow, currentCol + 1) == '#') && ((maze.returnCellValue(currentRow - 1, currentCol)) == ' ')) {
                    return true;
                }
            }
            case SOUTH -> {
                if ((maze.returnCellValue(currentRow, currentCol - 1) == '#') && ((maze.returnCellValue(currentRow + 1, currentCol)) == ' ')){
                    return true;
                }
            }
            case EAST -> {
                if ((maze.returnCellValue(currentRow + 1, currentCol) == '#') && ((maze.returnCellValue(currentRow, currentCol + 1)) == ' ')){
                    return true;
                }
            }
            case WEST -> {
                if ((maze.returnCellValue(currentRow - 1, currentCol) == '#') && ((maze.returnCellValue(currentRow, currentCol - 1)) == ' ')){
                    return true;
                }
            }
            default -> {
            }
        }
            return false;
    }

    @Override
    public boolean canTurnAround() {
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Maze maze = mazeSolver.getMaze();
        int currentRow = mazeSolver.getCurrentPosition().getRow();
        int currentCol = mazeSolver.getCurrentPosition().getCol();
        if (currentDirection != null) // Checks if the solver can turn around based on current direction
        switch (currentDirection) {
            case NORTH -> {
                if ((maze.returnCellValue(currentRow - 1, currentCol) == '#') && (maze.returnCellValue(currentRow, currentCol - 1) == '#') && (maze.returnCellValue(currentRow, currentCol + 1) == '#')) {
                    return true;
                }
            }
            case SOUTH -> {
                if ((maze.returnCellValue(currentRow + 1, currentCol) == '#') && (maze.returnCellValue(currentRow, currentCol - 1) == '#') && (maze.returnCellValue(currentRow, currentCol + 1) == '#')) {
                    return true;
                }
            }
            case EAST -> {
                if ((maze.returnCellValue(currentRow, currentCol + 1) == '#') && (maze.returnCellValue(currentRow - 1, currentCol) == '#') && (maze.returnCellValue(currentRow + 1, currentCol) == '#')) {
                    return true;
                }
            }
            case WEST -> {
                if ((maze.returnCellValue(currentRow, currentCol - 1) == '#') && (maze.returnCellValue(currentRow - 1, currentCol) == '#') && (maze.returnCellValue(currentRow + 1, currentCol) == '#')) {
                    return true;
                }
            }
            default -> {
            }
        }
            return false;
    }

    @Override
    public boolean canTurnRight() {
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Maze maze = mazeSolver.getMaze();
        int currentRow = mazeSolver.getCurrentPosition().getRow();
        int currentCol = mazeSolver.getCurrentPosition().getCol();
        // Checks if the solver can turn EAST based on current direction
        if (!canMoveForward()) {
            if (currentDirection != null) switch (currentDirection) {
                case NORTH -> {
                    if ((maze.returnCellValue(currentRow, currentCol + 1) == ' ')){
                        return true;
                    }
                }
                case SOUTH -> {
                    if ((maze.returnCellValue(currentRow, currentCol - 1) == ' ')){
                        return true;
                    }
                }
                case EAST -> {
                    if ((maze.returnCellValue(currentRow + 1, currentCol) == ' ')){
                        return true;
                    }
                }
                case WEST -> {
                    if ((maze.returnCellValue(currentRow - 1, currentCol) == ' ')){
                        return true;
                    }
                }
                default -> {
                }
            }
        }
        return false;
    }

    @Override
    public boolean canTurnLeft() {
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Maze maze = mazeSolver.getMaze();
        int currentRow = mazeSolver.getCurrentPosition().getRow();
        int currentCol = mazeSolver.getCurrentPosition().getCol();
        if (currentDirection != null) // Checks if the solver can turn WEST based on current direction
        switch (currentDirection) {
            case NORTH -> {
                if ((maze.returnCellValue(currentRow, currentCol + 1) == '#') && ((maze.returnCellValue(currentRow - 1, currentCol)) == '#')){
                    return true;
                }
            }
            case SOUTH -> {
                if ((maze.returnCellValue(currentRow, currentCol - 1) == '#') && ((maze.returnCellValue(currentRow + 1, currentCol)) == '#')){
                    return true;
                }
            }
            case EAST -> {
                if ((maze.returnCellValue(currentRow + 1, currentCol) == '#') && ((maze.returnCellValue(currentRow, currentCol + 1)) == '#')){
                    return true;
                }
            }
            case WEST -> {
                if ((maze.returnCellValue(currentRow - 1, currentCol) == '#') && ((maze.returnCellValue(currentRow, currentCol - 1)) == '#')){
                    return true;
                }
            }
            default -> {
            }
        }
            return false;
    }

    @Override
    public void moveForward() {
        // Position nextPosition = getNextPosition(mazeSolver.getCurrentDirection()); // Move the solver to the next position
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Position newPosition = getNextPosition(currentDirection);
        mazeSolver.setCurrentPosition(newPosition);
        mazeSolver.logStep("F"); // Log the forward move
    }

    @Override
    public void turnAround() {
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Direction newDirection = switch (currentDirection) {
            case NORTH -> Direction.SOUTH;
            case SOUTH -> Direction.NORTH;
            case EAST -> Direction.WEST;
            case WEST -> Direction.EAST;
        }; // Turn the solver 180 degrees
        mazeSolver.setCurrentDirection(newDirection);
        mazeSolver.logStep("R"); // Log the right turn
        mazeSolver.logStep("R"); // Log another right turn for 180-degree turn
    }

    @Override
    public void turnRight() {
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Direction newDirection = switch (currentDirection) {
            case NORTH -> Direction.EAST;
            case SOUTH -> Direction.WEST;
            case EAST -> Direction.SOUTH;
            case WEST -> Direction.NORTH;
        }; // Turn the solver 180 degrees
        mazeSolver.setCurrentDirection(newDirection);
        mazeSolver.logStep("R"); // Log the right turn
    }

    @Override
    public void turnLeft() {
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Direction newDirection = switch (currentDirection) {
            case NORTH -> Direction.WEST;
            case SOUTH -> Direction.EAST;
            case EAST -> Direction.NORTH;
            case WEST -> Direction.SOUTH;
        };
        mazeSolver.setCurrentDirection(newDirection);
        mazeSolver.logStep("L"); // Log the left turn
    }

    private Position getNextPosition(Direction direction) {
        int row = mazeSolver.getCurrentPosition().getRow();
        int col = mazeSolver.getCurrentPosition().getCol();
        return switch (direction) {
            case NORTH -> new Position(row - 1, col);
            case SOUTH -> new Position(row + 1, col);
            case WEST -> new Position(row, col - 1);
            case EAST -> new Position(row, col + 1);
        }; // Return the next position based on current direction
    }
}
