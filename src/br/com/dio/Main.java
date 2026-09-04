package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.DifficultyLevel;
import br.com.dio.model.Space;
import br.com.dio.util.PuzzleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static br.com.dio.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    private static Board board;

    private static boolean campaignMode = false;

    private static DifficultyLevel currentLevel = DifficultyLevel.EASY;

    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        final var cliPositions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        var option = -1;
        while (true) {
            printMenu();
            option = scanner.nextInt();

            switch (option) {
                case 1 -> startGame(cliPositions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> startCampaign();
                case 9 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Selecione uma das opções a seguir");
        System.out.println("1 - Iniciar um novo Jogo");
        System.out.println("2 - Colocar um novo número");
        System.out.println("3 - Remover um número");
        System.out.println("4 - Visualizar jogo atual");
        System.out.println("5 - Verificar status do jogo");
        System.out.println("6 - Limpar jogo");
        System.out.println("7 - Finalizar jogo");
        System.out.println("8 - Iniciar modo campanha (níveis progressivos)");
        if (campaignMode) {
            System.out.printf("   >> Campanha ativa | Nível atual: %s%n", currentLevel.getLabel());
        }
        System.out.println("9 - Sair");
    }

    private static void startCampaign() {
        if (nonNull(board)) {
            System.out.println("Finalize ou limpe o jogo atual antes de iniciar a campanha");
            return;
        }

        campaignMode = true;
        currentLevel = DifficultyLevel.EASY;
        System.out.println("Modo campanha ativado! Começando pelo nível Fácil.");
        System.out.println("Use a opção 1 para iniciar o primeiro puzzle.");
    }

    private static void startGame(final Map<String, String> cliPositions) {
        if (nonNull(board)) {
            System.out.println("O jogo já foi iniciado");
            return;
        }

        final Map<String, String> positions = resolvePositions(cliPositions);
        if (positions.isEmpty()) {
            System.out.println("Nenhum puzzle disponível. Ative o modo campanha (opção 8) ou passe os argumentos na linha de comando.");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);

        if (campaignMode) {
            var puzzle = PuzzleRepository.getByLevel(currentLevel).orElseThrow();
            System.out.printf("Nível %s - %s%n", currentLevel.getLabel(), puzzle.name());
        }
        System.out.println("O jogo está pronto para começar");
    }

    private static Map<String, String> resolvePositions(final Map<String, String> cliPositions) {
        if (campaignMode) {
            return PuzzleRepository.getByLevel(currentLevel)
                    .map(p -> p.positions())
                    .orElse(Map.of());
        }
        if (cliPositions.size() >= BOARD_LIMIT * BOARD_LIMIT) {
            return cliPositions;
        }
        return Map.of();
    }

    private static void inputNumber() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o número será inserido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o número será inserido");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número que vai entrar na posição [%s,%s]%n", col, row);
        var value = runUntilGetValidNumber(1, 9);
        if (!board.changeValue(col, row, value)) {
            System.out.printf("A posição [%s,%s] tem um valor fixo%n", col, row);
        }
    }

    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o número será removido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o número será removido");
        var row = runUntilGetValidNumber(0, 8);
        if (!board.clearValue(col, row)) {
            System.out.printf("A posição [%s,%s] tem um valor fixo%n", col, row);
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col : board.getSpaces()) {
                args[argPos++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }
        System.out.println("Seu jogo se encontra da seguinte forma");
        System.out.printf((BOARD_TEMPLATE) + "%n", args);
    }

    private static void showGameStatus() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.printf("O jogo atualmente se encontra no status %s%n", board.getStatus().getLabel());
        if (board.hasErrors()) {
            System.out.println("O jogo contém erros");
        } else {
            System.out.println("O jogo não contém erros");
        }
    }

    private static void clearGame() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Tem certeza que deseja limpar seu jogo e perder todo seu progresso?");
        if (confirmYes()) {
            board.reset();
        }
    }

    private static void finishGame() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        if (board.gameIsFinished()) {
            System.out.println("Parabéns, você concluiu o jogo!");
            showCurrentGame();

            if (campaignMode) {
                offerNextLevel();
            } else {
                board = null;
            }
        } else if (board.hasErrors()) {
            System.out.println("Seu jogo contém erros, verifique seu board e ajuste-o");
        } else {
            System.out.println("Você ainda precisa preencher algum espaço");
        }
    }

    private static void offerNextLevel() {
        var nextLevel = currentLevel.next();
        board = null;

        if (isNull(nextLevel)) {
            System.out.println("Incrível! Você completou todos os níveis da campanha!");
            campaignMode = false;
            currentLevel = DifficultyLevel.EASY;
            return;
        }

        System.out.printf("Deseja avançar para o nível %s? (sim/não)%n", nextLevel.getLabel());
        if (confirmYes()) {
            currentLevel = nextLevel;
            startGame(Map.of());
        } else {
            currentLevel = nextLevel;
            System.out.printf("Nível %s desbloqueado! Use a opção 1 quando quiser continuar.%n", nextLevel.getLabel());
        }
    }

    private static boolean confirmYes() {
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")) {
            System.out.println("Informe 'sim' ou 'não'");
            confirm = scanner.next();
        }
        return confirm.equalsIgnoreCase("sim");
    }

    private static int runUntilGetValidNumber(final int min, final int max) {
        var current = scanner.nextInt();
        while (current < min || current > max) {
            System.out.printf("Informe um número entre %s e %s%n", min, max);
            current = scanner.nextInt();
        }
        return current;
    }

}
