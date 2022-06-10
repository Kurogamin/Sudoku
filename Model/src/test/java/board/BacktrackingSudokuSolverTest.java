package board; /**
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     You should have received a copy of the GNU General Public License
 *     along with this program.
 */
import board.BacktrackingSudokuSolver;
import board.SudokuField;
import board.SudokuSolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    @Test
    void solve() {
        SudokuField[][] board = new SudokuField[9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        SudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(board);
        int oldNumber;
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                oldNumber = board[i][j].getFieldValue();
                board[i][j].setFieldValue(0);
                assertTrue(solver.checkRow(i, oldNumber, board));
                assertTrue(solver.checkCol(j, oldNumber, board));
                assertTrue(solver.checkBlock(i, j,  oldNumber, board));
                board[i][j].setFieldValue(oldNumber);
            }
        }
    }

    @Test
    void checkNumber() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        assertTrue(true);
        System.out.println(System.getProperty("user.dir"));
    }
}