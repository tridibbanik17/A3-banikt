package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ObserverTest {

    @Test
    void testMazeSolverObserver() {
        Logger logger = Logger.getLogger(ConsoleObserver.class.getName());

        try (LogCaptor logCaptor = new LogCaptor(logger)) {
            logCaptor.attach();

            ConsoleObserver consoleObserver = new ConsoleObserver();
            Maze maze = new Maze(new Character[][]{
                {'#', '#', '#', '#'},
                {'#', ' ', '#', '#'},
                {' ', ' ', ' ', ' '},
                {'#', '#', '#', '#'}
            });

            RightHandNavigation solver = RightHandNavigation.create(maze);
            solver.addObserver(consoleObserver);
            solver.solve();

            assertFalse(logCaptor.getCapturedLogs().isEmpty(), "ConsoleObserver should log messages.");
            assertTrue(logCaptor.getCapturedLogs().stream().anyMatch(msg -> msg.contains("Maze successfully solved!")),
                    "ConsoleObserver log should contain 'Maze successfully solved!'");
        }
    }

    static class LogCaptor extends Handler implements AutoCloseable {
        private final Logger logger;
        private final List<String> capturedLogs = new ArrayList<>();
        private final Handler oldHandler;

        LogCaptor(Logger logger) {
            this.logger = logger;
            this.oldHandler = logger.getHandlers().length > 0 ? logger.getHandlers()[0] : null;
            logger.setUseParentHandlers(false);
        }

        void attach() {
            logger.addHandler(this);
        }

        @Override
        public void publish(LogRecord record) {
            capturedLogs.add(record.getMessage());
        }

        @Override
        public void flush() {}

        @Override
        public void close() {
            logger.removeHandler(this);
            if (oldHandler != null) {
                logger.addHandler(oldHandler);
            }
        }

        List<String> getCapturedLogs() {
            return capturedLogs;
        }
    }
}
