package br.com.dio.model;

public enum DifficultyLevel {

    EASY(1, "Fácil"),
    MEDIUM(2, "Médio"),
    HARD(3, "Difícil"),
    EXPERT(4, "Expert");

    private final int order;
    private final String label;

    DifficultyLevel(final int order, final String label) {
        this.order = order;
        this.label = label;
    }

    public int getOrder() {
        return order;
    }

    public String getLabel() {
        return label;
    }

    public DifficultyLevel next() {
        return switch (this) {
            case EASY -> MEDIUM;
            case MEDIUM -> HARD;
            case HARD -> EXPERT;
            case EXPERT -> null;
        };
    }

}
