package edu.project2.solver;

import org.jetbrains.annotations.NotNull;

public class SolverManager {
    public @NotNull Solver getInstanceOf(@NotNull SolverType type) {
        switch (type) {
            case BreadthFirstSearchSolver -> {
                return new BreadthFirstSearchSolver();
            }
            default -> {
                return new BreadthFirstSearchSolver();
            }
        }
    }
}
