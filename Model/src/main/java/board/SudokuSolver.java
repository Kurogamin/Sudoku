package board;

/**
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

public abstract class SudokuSolver {

    public abstract void solve(SudokuField [][] board);

    public boolean checkRow(int row, int number, SudokuField[][] board) {
        for (int i = 0;i < board.length; i++) {
            if (board[row][i].getFieldValue() > 0 && board[row][i].getFieldValue() == number) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCol(int col, int number, SudokuField[][] board) {
        for (SudokuField [] fields : board) {
            if (fields[col].getFieldValue() > 0 && fields[col].getFieldValue() == number) {
                return false;
            }
        }
        return true;
    }

    public boolean checkBlock(int row, int col, int number, SudokuField[][] board) {
        int blockRow = (row / 3) * 3;
        int blockCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[blockRow + i][blockCol + j].getFieldValue() == number) {
                    return false;
                }
            }
        }
        return true;
    }
}
