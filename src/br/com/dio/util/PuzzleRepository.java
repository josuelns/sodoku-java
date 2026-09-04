package br.com.dio.util;

import br.com.dio.model.DifficultyLevel;
import br.com.dio.model.Puzzle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class PuzzleRepository {

    private static final int BOARD_SIZE = 9;

    private static final List<Puzzle> PUZZLES = List.of(
            new Puzzle(DifficultyLevel.EASY, "Primeiros Passos", buildPositions(EASY_SOLUTION, EASY_FIXED)),
            new Puzzle(DifficultyLevel.MEDIUM, "Desafio Moderado", buildPositions(MEDIUM_SOLUTION, MEDIUM_FIXED)),
            new Puzzle(DifficultyLevel.HARD, "Mente Afiada", buildPositions(HARD_SOLUTION, HARD_FIXED)),
            new Puzzle(DifficultyLevel.EXPERT, "Mestre Sudoku", buildPositions(EASY_SOLUTION, EXPERT_FIXED))
    );

    private static final int[][] EASY_SOLUTION = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    private static final boolean[][] EASY_FIXED = {
            {true, true, true, false, true, true, false, false, false},
            {true, true, false, true, true, true, false, false, true},
            {false, true, true, true, false, false, true, true, false},
            {true, false, true, false, true, true, false, false, true},
            {true, true, false, true, true, true, false, false, true},
            {true, false, true, false, true, false, true, false, true},
            {false, true, false, true, false, true, true, true, false},
            {false, false, true, true, true, true, false, false, true},
            {false, false, false, false, true, false, true, true, true}
    };

    private static final int[][] MEDIUM_SOLUTION = {
            {1, 5, 2, 4, 8, 9, 3, 6, 7},
            {7, 3, 9, 2, 6, 1, 8, 4, 5},
            {4, 6, 8, 3, 7, 5, 1, 2, 9},
            {5, 1, 3, 6, 9, 4, 7, 8, 2},
            {2, 8, 7, 1, 5, 3, 6, 9, 4},
            {9, 4, 6, 7, 2, 8, 5, 3, 1},
            {3, 2, 1, 5, 4, 6, 9, 7, 8},
            {8, 7, 4, 9, 3, 2, 1, 5, 6},
            {6, 9, 5, 8, 1, 7, 2, 4, 3}
    };

    private static final boolean[][] MEDIUM_FIXED = {
            {true, true, false, false, true, false, false, true, false},
            {true, false, false, true, false, true, false, false, true},
            {false, true, true, false, true, false, true, false, false},
            {true, false, false, true, true, true, false, false, false},
            {false, true, true, false, true, false, true, false, false},
            {false, false, true, true, false, true, false, true, true},
            {true, false, false, false, true, true, true, false, false},
            {false, true, true, true, false, false, false, true, false},
            {false, false, true, true, true, false, false, false, true}
    };

    private static final int[][] HARD_SOLUTION = {
            {9, 8, 7, 6, 5, 4, 3, 2, 1},
            {2, 4, 6, 1, 7, 3, 9, 8, 5},
            {3, 5, 1, 9, 2, 8, 7, 4, 6},
            {1, 2, 8, 5, 3, 7, 6, 9, 4},
            {6, 3, 4, 8, 9, 2, 1, 5, 7},
            {7, 9, 5, 4, 6, 1, 8, 3, 2},
            {5, 1, 9, 2, 8, 6, 4, 7, 3},
            {4, 7, 2, 3, 1, 9, 5, 6, 8},
            {8, 6, 3, 7, 4, 5, 2, 1, 9}
    };

    private static final boolean[][] HARD_FIXED = {
            {true, true, false, false, true, false, false, false, true},
            {false, true, false, true, false, false, true, false, false},
            {false, false, true, false, true, true, false, false, false},
            {true, false, false, true, false, false, true, false, true},
            {false, true, true, false, true, false, false, true, false},
            {false, false, true, true, false, true, false, false, true},
            {true, false, false, false, true, false, true, false, false},
            {false, true, true, false, false, true, false, true, false},
            {false, false, false, true, true, false, false, true, true}
    };

    private static final boolean[][] EXPERT_FIXED = {
            {true, false, false, false, true, false, false, false, false},
            {false, false, false, true, false, false, true, false, false},
            {false, false, true, false, false, false, false, true, false},
            {false, false, false, false, false, true, false, false, true},
            {true, false, false, true, false, true, false, false, false},
            {false, false, true, false, true, false, false, false, false},
            {false, true, false, false, false, false, true, false, false},
            {false, false, false, true, true, true, false, false, false},
            {false, false, false, false, true, false, false, true, true}
    };

    private PuzzleRepository() {
    }

    public static Optional<Puzzle> getByLevel(final DifficultyLevel level) {
        return PUZZLES.stream()
                .filter(p -> p.level().equals(level))
                .findFirst();
    }

    public static Puzzle getFirst() {
        return PUZZLES.get(0);
    }

    public static List<Puzzle> getAll() {
        return PUZZLES;
    }

    private static Map<String, String> buildPositions(final int[][] solution, final boolean[][] fixed) {
        final Map<String, String> positions = new HashMap<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                final var key = "%s,%s".formatted(col, row);
                final var value = "%s,%s".formatted(solution[row][col], fixed[row][col]);
                positions.put(key, value);
            }
        }
        return positions;
    }

}
