package edu.project2.solver;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SolverManagerTest {

    @Test
    void getInstanceOf() {
        // given
        SolverType type = SolverType.BreadthFirstSearchSolver;
        var expectedClass = BreadthFirstSearchSolver.class;

        // when
        var actualClass = new SolverManager().getInstanceOf(type);

        // then
        assertThat(actualClass)
            .isInstanceOf(expectedClass);
    }
}
