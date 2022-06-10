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
import board.BoardListener;
import board.SudokuBoard;
import board.SudokuField;
import board.SudokuSolver;
import org.apache.commons.lang3.builder.Diff;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    //@Test
    /*void testFillBoard() {
        SudokuBoard test = new SudokuBoard();
        test.fillBoard();
        int [][] firstBoard = test.getBoard();
        test.clearBoard();
        test.fillBoard();
        int [][] secondBoard = test.getBoard();
        assertFalse(Arrays.deepEquals(firstBoard, secondBoard));
        int oldNumber;
    }*/



    @Test
    void getBoard() {
        SudokuField[][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                board[i][j].setFieldValue(7);
            }
        }
        SudokuBoard test = new SudokuBoard(board);
        assertTrue(Arrays.deepEquals(board, test.getBoard()));
    }

    @Test
    void getNumber() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                board[i][j].setFieldValue(7);
            }
        }
        SudokuBoard test = new SudokuBoard(board);
        assertEquals(7, test.getNumber(0, 0));
        assertNotEquals(6, test.getNumber(5, 5));
        board[5][5].setFieldValue(9);
        assertEquals(9, test.getNumber(5, 5));
    }

    @Test
    void setNumber() {
        int [][] board = new int [9][9];
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                board[i][j] = 7;
            }
        }
        SudokuBoard test = new SudokuBoard(board);
        assertEquals(7, test.getNumber(0, 0));
        test.setNumber(0,0, 1);
        assertEquals(1, test.getNumber(0, 0));
    }

    @Test
    void clearBoard() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                board[i][j].setFieldValue(7);
            }
        }
        SudokuBoard test = new SudokuBoard(board);
        test.clearBoard();
        boolean isZero = true;
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                if (board[i][j].getFieldValue() != 0) {
                    isZero = false;
                    break;
                }
            }
        }
        assertTrue(isZero);
    }

    @Test
    void testPrintBoard() {
        int [][] board = new int [9][9];
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(board, solver);
        test.printBoard();
    }

    @Test
    void testCheckNumbers() {
        /*int [][] board = new int [9][9];
        SudokuBoard test = new SudokuBoard(board);
        test.checkNumbers();
        assertTrue(true);*/
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard test = new SudokuBoard(board, solver);
        test.solveGame();
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
        assertTrue(test.checkBoard());
        board[0][7].setFieldValue(board[0][0].getFieldValue());
        assertFalse(test.checkBoard());
    }

    @Test
    void getRow() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        SudokuBoard test = new SudokuBoard(board);
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                assertEquals(board[i][j].getFieldValue(), test.getRow(i).getValue(j));
            }
        }
    }

    @Test
    void getColumn() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        SudokuBoard test = new SudokuBoard(board);
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                assertEquals(board[i][j].getFieldValue(), test.getColumn(j).getValue(i));
            }
        }
    }

    @Test
    void getBox() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        SudokuBoard test = new SudokuBoard(board);
        for(int i = 0;i < 3; i++) {
            for(int j = 0;j < 3; j++) {
                assertEquals(board[i][j].getFieldValue(), test.getBox(0,0).getValue(j + i * 3));
            }
        }
    }
    @Test
    void setBoard() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(1);
            }
        }
        SudokuField [][] board2 = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board2[i][j] = new SudokuField(2);
            }
        }
        SudokuBoard test = new SudokuBoard(board);
        test.setBoard(board2);
        assertTrue(Arrays.deepEquals(test.getBoard(), board2));
    }

    @Test
    void removePropertyChangeListener() {
        SudokuBoard board = new SudokuBoard();
        BoardListener observer = new BoardListener();
        board.removePropertyChangeListener(observer);
        assertTrue(true);
    }

    @Test
    void checkBoard() {
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        int oldNumber = board.getNumber(1, 1);
        board.setNumber(1,1,0);
        assertFalse(board.checkBoard());
        board.setNumber(1,1,oldNumber);
        assertTrue(board.checkBoard());
        oldNumber = board.getNumber(1, 0);
        board.setNumber(1,0,0);
        assertFalse(board.checkBoard());
        board.setNumber(1,0,oldNumber);
        assertTrue(board.checkBoard());
        oldNumber = board.getNumber(0, 0);
        board.setNumber(0,0,0);
        assertFalse(board.checkBoard());
        board.setNumber(0,0,oldNumber);
        assertTrue(board.checkBoard());
    }

    @Test
    void testToString() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(1);
            }
        }
        SudokuBoard test = new SudokuBoard(board);
        SudokuField [][] board2 = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board2[i][j] = new SudokuField(0);
            }
        }
        SudokuBoard test2 = new SudokuBoard(board2);
        assertNotEquals(test.toString(), test2.toString());
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                test2.setNumber(i, j, 1);
            }
        }
        assertEquals(test.toString(), test2.toString());
    }

    @Test
    void testEquals() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(1);
            }
        }
        SudokuField [][] board2 = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board2[i][j] = new SudokuField(2);
            }
        }
        SudokuBoard test1 = new SudokuBoard(board);
        SudokuBoard test2 = new SudokuBoard(board2);
        assertFalse(test1.equals(test2));

        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                test2.setNumber(i, j, 1);
            }
        }
        SudokuBoard test3 = null;
        SudokuField field = new SudokuField(4);
        assertTrue(test1.equals(test2));
        assertEquals(test1.hashCode(), test2.hashCode());
        assertTrue(test1.equals(test1));
        assertFalse(test1.equals(test3));
        assertFalse(test1.equals(field));
        test2.setNumber(0,2,5);
        assertFalse(test1.equals(test2));
        test2.setNumber(1,0,5);
        assertFalse(test1.equals(test2));
    }

    @Test
    void testHashCode() {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(1);
            }
        }
        SudokuField [][] board2 = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board2[i][j] = new SudokuField(2);
            }
        }
        SudokuBoard test1 = new SudokuBoard(board);
        SudokuBoard test2 = new SudokuBoard(board2);
        assertNotEquals(test1.hashCode(), test2.hashCode());

        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                test2.setNumber(i, j, 1);
            }
        }
        assertEquals(test1.hashCode(), test2.hashCode());
        SudokuBoard t1 = new SudokuBoard();
        SudokuBoard t2 = new SudokuBoard();
        t1.solveGame();
        t2.solveGame();
        assertNotEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void removeFields() {
        SudokuBoard board = new SudokuBoard();
        SudokuBoard board2 = new SudokuBoard();
        SudokuBoard board3 = new SudokuBoard();
        board.solveGame();
        board2.solveGame();
        board3.solveGame();
        board.removeFields(Difficulty.Easy);
        board2.removeFields(Difficulty.Medium);
        board3.removeFields(Difficulty.Hard);
        int b1 = 0;
        int b2 = 0;
        int b3 = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getNumber(i, j) == 0) {
                    b1++;
                }

                if (board2.getNumber(i, j) == 0) {
                    b2++;
                }

                if (board3.getNumber(i, j) == 0) {
                    b3++;
                }
            }
        }
        assertEquals(10, b1);
        assertEquals(25, b2);
        assertEquals(40, b3);

    }
}



