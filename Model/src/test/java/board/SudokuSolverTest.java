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

class SudokuSolverTest {

    @Test
    void checkRow() {
        SudokuField[][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        for(int i = 0;i < board.length;i++) {
            board[0][i].setFieldValue(i + 1);
        }
        SudokuSolver solve = new BacktrackingSudokuSolver();
        assertFalse(solve.checkRow(0,7, board));
        board[0][6].setFieldValue(0);
        assertTrue(solve.checkRow(0,7, board));
    }

    @Test
    void checkCol() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        SudokuSolver solve = new BacktrackingSudokuSolver();
        for(int i = 0;i < board.length;i++) {
            board[i][0].setFieldValue(i + 1);
        }
        assertFalse(solve.checkCol(0, 7, board));
        board[6][0].setFieldValue(0);
        assertTrue(solve.checkCol(0, 7, board));
    }

    @Test
    void checkBlock() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        SudokuSolver solve = new BacktrackingSudokuSolver();
        int k = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j].setFieldValue(k);
                k++;
            }
        }
        assertFalse(solve.checkBlock(0,0,1, board));
        board[0][0].setFieldValue(0);
        assertTrue(solve.checkBlock(0,0,1, board));
    }
}