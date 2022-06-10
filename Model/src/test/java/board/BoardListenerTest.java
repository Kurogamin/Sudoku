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
import board.BoardListener;
import board.SudokuBoard;
import board.SudokuField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardListenerTest {

    @Test
    void propertyChange() {
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        SudokuField[][] newBoard = new SudokuField [9][9];
        BoardListener observer = new BoardListener();
        board.addPropertyChangeListener(observer);
        int oldNumber00 = board.getNumber(0,0);
        board.setNumber(0,0,0);
        assertSame(observer.getBoard()[0][0], board.getBoard()[0][0]);
    }
}