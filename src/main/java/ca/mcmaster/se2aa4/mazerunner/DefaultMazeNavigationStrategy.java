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
        int mazeHeight = maze.getGrid().length;
        int mazeWidth = maze.getGrid()[0].length;

        if (currentDirection == null) return false;

        boolean canMove = false;
        switch (currentDirection) {
            case NORTH -> canMove = currentRow > 0 && maze.returnCellValue(currentRow - 1, currentCol) == ' ' &&
                    maze.returnCellValue(currentRow, currentCol + 1) == '#';
            case SOUTH -> canMove = currentRow < mazeHeight - 1 && maze.returnCellValue(currentRow + 1, currentCol) == ' ' &&
                    maze.returnCellValue(currentRow, currentCol - 1) == '#';
            case EAST -> canMove = currentCol < mazeWidth - 1 && maze.returnCellValue(currentRow, currentCol + 1) == ' ' &&
                    maze.returnCellValue(currentRow + 1, currentCol) == '#';
            case WEST -> canMove = currentCol > 0 && maze.returnCellValue(currentRow, currentCol - 1) == ' ' &&
                    maze.returnCellValue(currentRow - 1, currentCol) == '#';
        }
        return canMove;
    }

    @Override
    public boolean canTurnAround() {
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Maze maze = mazeSolver.getMaze();
        int currentRow = mazeSolver.getCurrentPosition().getRow();
        int currentCol = mazeSolver.getCurrentPosition().getCol();
        int mazeHeight = maze.getGrid().length;
        int mazeWidth = maze.getGrid()[0].length;

        if (currentDirection == null) return false;

        switch (currentDirection) {
            case NORTH -> {
                return currentRow > 0 && maze.returnCellValue(currentRow - 1, currentCol) == '#' &&
                    currentCol > 0 && maze.returnCellValue(currentRow, currentCol - 1) == '#' &&
                    currentCol < mazeWidth - 1 && maze.returnCellValue(currentRow, currentCol + 1) == '#';
            }
            case SOUTH -> {
                return currentRow < mazeHeight - 1 && maze.returnCellValue(currentRow + 1, currentCol) == '#' &&
                    currentCol > 0 && maze.returnCellValue(currentRow, currentCol - 1) == '#' &&
                    currentCol < mazeWidth - 1 && maze.returnCellValue(currentRow, currentCol + 1) == '#';
            }
            case EAST -> {
                return currentCol < mazeWidth - 1 && maze.returnCellValue(currentRow, currentCol + 1) == '#' &&
                    currentRow > 0 && maze.returnCellValue(currentRow - 1, currentCol) == '#' &&
                    currentRow < mazeHeight - 1 && maze.returnCellValue(currentRow + 1, currentCol) == '#';
            }
            case WEST -> {
                return currentCol > 0 && maze.returnCellValue(currentRow, currentCol - 1) == '#' &&
                    currentRow > 0 && maze.returnCellValue(currentRow - 1, currentCol) == '#' &&
                    currentRow < mazeHeight - 1 && maze.returnCellValue(currentRow + 1, currentCol) == '#';
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public boolean canTurnRight() {
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Maze maze = mazeSolver.getMaze();
        int currentRow = mazeSolver.getCurrentPosition().getRow();
        int currentCol = mazeSolver.getCurrentPosition().getCol();

        // Only check the next right turn if it's within bounds
        if (canMoveForward()) return false;

        if (currentDirection != null) {
            return switch (currentDirection) {
                case NORTH -> currentCol < maze.getGrid()[0].length - 1 && maze.returnCellValue(currentRow, currentCol + 1) == ' ';
                case SOUTH -> currentCol > 0 && maze.returnCellValue(currentRow, currentCol - 1) == ' ';
                case EAST -> currentRow < maze.getGrid().length - 1 && maze.returnCellValue(currentRow + 1, currentCol) == ' ';
                case WEST -> currentRow > 0 && maze.returnCellValue(currentRow - 1, currentCol) == ' ';
                default -> false;
            };
        }
        return false;
    }

    @Override
    public boolean canTurnLeft() {
        Direction currentDirection = mazeSolver.getCurrentDirection();
        Maze maze = mazeSolver.getMaze();
        int currentRow = mazeSolver.getCurrentPosition().getRow();
        int currentCol = mazeSolver.getCurrentPosition().getCol();

        if (currentDirection != null) {
            return switch (currentDirection) {
                case NORTH -> currentCol > 0 && maze.returnCellValue(currentRow, currentCol - 1) == '#' &&
                    currentRow > 0 && maze.returnCellValue(currentRow - 1, currentCol) == '#';
                case SOUTH -> currentCol > 0 && maze.returnCellValue(currentRow, currentCol - 1) == '#' &&
                    currentRow < maze.getGrid().length - 1 && maze.returnCellValue(currentRow + 1, currentCol) == '#';
                case EAST -> currentRow < maze.getGrid().length - 1 && maze.returnCellValue(currentRow + 1, currentCol) == '#' &&
                    currentCol < maze.getGrid()[0].length - 1 && maze.returnCellValue(currentRow, currentCol + 1) == '#';
                case WEST -> currentRow > 0 && maze.returnCellValue(currentRow - 1, currentCol) == '#' &&
                    currentCol > 0 && maze.returnCellValue(currentRow, currentCol - 1) == '#';
                default -> false;
            };
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
        mazeSolver.logStep("RR"); // Log the right turn
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
        int maxRow = mazeSolver.getMaze().getGrid().length;
        int maxCol = mazeSolver.getMaze().getGrid()[0].length;

        return switch (direction) {
            case NORTH -> (row > 0) ? new Position(row - 1, col) : null;
            case SOUTH -> (row < maxRow - 1) ? new Position(row + 1, col) : null;
            case WEST -> (col > 0) ? new Position(row, col - 1) : null;
            case EAST -> (col < maxCol - 1) ? new Position(row, col + 1) : null;
            default -> null;
        };
    }
}
