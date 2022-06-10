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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class BacktrackingSudokuSolver extends SudokuSolver implements Serializable {
    Random rand = new Random();
    private final Vector<Integer>[][] numbers = new Vector[9][9];

    @Override
    public void solve(SudokuField [][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                numbers[i][j] = new Vector<>();
            }
        }
        List<Integer> numbers = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            numbers.add(i + 1);
        }
        Collections.shuffle(numbers);
        for (int i = 0; i < 9; i++) {
            board[0][i].setFieldValue(numbers.get(i));
        }
        checkNumber(1,0,rand.nextInt(9) + 1, board);
    }

    void checkNumber(int row, int col, int number, SudokuField [][] board) {

        if (row == 9) {
            return;
        }

        numbers[row][col].add(board[row][col].getFieldValue());
        board[row][col].setFieldValue(0);

        while (true) {
            if (!checkRow(row, number, board)
                    || !checkCol(col, number, board)
                    || !checkBlock(row, col, number, board)) {

                numbers[row][col].add(number);
                if (numbers[row][col].size() < 9) {
                    number = rand.nextInt(9) + 1;
                    while (true) {
                        if (numbers[row][col].contains(number)) {
                            number = rand.nextInt(9) + 1;
                        } else {
                            break;
                        }
                    }
                } else {
                    if (col - 1 < 0) {
                        numbers[row][col].clear();
                        checkNumber(row - 1, 8, rand.nextInt(9) + 1, board);
                        return;
                    } else {
                        numbers[row][col].clear();
                        checkNumber(row, col - 1, rand.nextInt(9) + 1, board);
                        return;
                    }
                }
            } else {
                board[row][col].setFieldValue(number);
                if (col + 1 > 8) {
                    checkNumber(row + 1, 0, rand.nextInt(9) + 1, board);
                } else {
                    checkNumber(row, col + 1, rand.nextInt(9) + 1, board);
                }
                return;
            }
        }
    }
}

