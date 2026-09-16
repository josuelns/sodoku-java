# sodoku-java

Jogo de **Sudoku** em Java puro com interface de terminal: geração de puzzles, validação de regras (linha/coluna/bloco) e resolução por **backtracking**.

Projeto de **OOP e algoritmos** — parte da minha jornada em Java.

## Stack

- Java · Orientação a Objetos · Algoritmos (backtracking)

## Destaques

- Modo campanha com níveis de dificuldade (`EASY`, `MEDIUM`, `HARD`)
- Validação incremental a cada jogada
- Modelagem com `Board`, `Space`, `Puzzle` e repositório de templates

## Como rodar

```bash
# IntelliJ / IDE: executar br.com.dio.Main
# Ou via javac + java a partir de src/
```

## Estrutura

```
src/br/com/dio/
├── Main.java              → loop do jogo (terminal)
├── model/                 → Board, Space, Puzzle, DifficultyLevel
└── util/                  → BoardTemplate, PuzzleRepository
```

## Projetos relacionados

- [desafio-poo-dio](https://github.com/josuelns/desafio-poo-dio) — modelagem OO com bootcamp
- [tasks-api-spring](https://github.com/josuelns/tasks-api-spring) — Spring Boot com JPA

---

[Portfólio](https://josuelns.github.io/) · [GitHub](https://github.com/josuelns)
