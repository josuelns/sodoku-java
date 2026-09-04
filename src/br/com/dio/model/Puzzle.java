package br.com.dio.model;

import java.util.Map;

public record Puzzle(
        DifficultyLevel level,
        String name,
        Map<String, String> positions
) {
}
