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
package board;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparableCloneableTest
{
    @Test
    void cloneableTests() throws CloneNotSupportedException {
        SudokuField [][] board = new SudokuField [9][9];
        for(int i = 0;i < 9; i++) {
            for(int j = 0;j < 9; j++) {
                board[i][j] = new SudokuField(1);
            }
        }
        SudokuBoard test = new SudokuBoard(board);
        SudokuBoard clonedBoard = test.clone();
        assertTrue(clonedBoard.equals(test));

        SudokuField field = new SudokuField(1);
        SudokuField clonedField = field.clone();
        assertTrue(clonedField.equals(field));

        SudokuRow row = new SudokuRow();
        SudokuBox box = new SudokuBox();
        SudokuColumn column = new SudokuColumn();
        SudokuSection section = new SudokuSection();
        for(int j = 0;j < 9; j++) {
            row.add(new SudokuField(j));
            box.add(new SudokuField(j));
            column.add(new SudokuField(j));
            section.add(new SudokuField(j));
        }
        SudokuRow clonedRow = row.clone();
        SudokuBox clonedBox = box.clone();
        SudokuColumn clonedColumn = column.clone();
        SudokuSection clonedSection = section.clone();
        assertTrue(clonedRow.equals(row));
        assertTrue(clonedBox.equals(box));
        assertTrue(clonedColumn.equals(column));
        assertTrue(clonedSection.equals(section));
        clonedRow.set(0, 999);
        clonedBox.set(0, 999);
        clonedSection.set(0, 999);
        clonedColumn.set(0, 999);
        assertFalse(clonedBox.equals(box));
        assertFalse(clonedRow.equals(row));
        assertFalse(clonedSection.equals(section));
        assertFalse(clonedColumn.equals(column));
    }

    @Test
    void comparableTest() {
        List<SudokuField> fields = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            fields.add(new SudokuField(9 - i));
        }
        Collections.sort(fields);
        for (int i = 0; i < 9; i++) {
            assertEquals(i + 1, fields.get(i).getFieldValue());
        }

        SudokuField field = new SudokuField();
        assertThrows(NullPointerException.class, () -> field.compareTo(null));
    }
}
