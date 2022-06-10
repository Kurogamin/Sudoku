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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BoardListener implements PropertyChangeListener {

    private SudokuField [][] fields = new SudokuField [9][9];

    public void propertyChange(PropertyChangeEvent evt) {
        this.setBoard((SudokuField[][]) evt.getNewValue());
        SudokuBoard board = new SudokuBoard(fields);
        System.out.println(board.checkBoard());
    }

    private void setBoard(SudokuField [][] newFields) {
        this.fields = newFields;
    }

    public SudokuField[][] getBoard() {
        SudokuField [][] board = new SudokuField [9][9];
        for (int i = 0;i < 9; i++) {
            for (int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(this.fields[i], 0, board[i], 0, fields.length);
        }
        return board;
    }
}
