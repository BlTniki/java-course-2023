package edu.project2.solver;

import edu.hw9.task3.BfsParallelSolver;
import org.jetbrains.annotations.NotNull;

public class SolverManager {
    @SuppressWarnings("DuplicateBranchesInSwitch")
    public @NotNull Solver getInstanceOf(@NotNull SolverType type) {
        switch (type) {
            case BreadthFirstSearchSolver -> {
                return new BreadthFirstSearchSolver();
            }
            case BfsParallelSolver -> {
                return new BfsParallelSolver();
            }
            default -> {
                return new BreadthFirstSearchSolver();
            }
        }
    }
}
